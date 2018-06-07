package com.evensgn.emcompiler.backend;

import com.evensgn.emcompiler.ir.*;
import com.sun.corba.se.impl.ior.OldJIDLObjectKeyTemplate;

import java.util.*;

public class FunctionInlineProcessor {
    private final int MAX_INLINE_INST = 20;
    private final int MAX_LOW_INLINE_INST = 20;
    private final int MAX_FUNC_INST = 1 << 12;
    private final int MAX_INLINE_DEPTH = 5;

    private IRRoot ir;

    private class FuncInfo {
        int numInst = 0, numCalled = 0;
        boolean recursiveCall, memFunc = false;
    }

    private Map<IRFunction, FuncInfo> funcInfoMap = new HashMap<>();
    private Map<IRFunction, IRFunction> funcBakUpMap = new HashMap<>();

    public FunctionInlineProcessor(IRRoot ir) {
        this.ir = ir;
    }

    public void run() {
        for (IRFunction irFunction : ir.getFuncs().values()) {
            irFunction.setRecursiveCall(irFunction.recursiveCalleeSet.contains(irFunction));
            FuncInfo funcInfo = new FuncInfo();
            funcInfo.recursiveCall = irFunction.isRecursiveCall();
            funcInfo.memFunc = irFunction.isMemFunc();
            funcInfoMap.put(irFunction, funcInfo);
        }
        for (IRFunction irFunction : ir.getFuncs().values()) {
            FuncInfo funcInfo = funcInfoMap.get(irFunction);
            for (BasicBlock bb : irFunction.getReversePostOrder()) {
                for (IRInstruction inst = bb.getFirstInst(); inst != null; inst = inst.getNextInst()) {
                    ++funcInfo.numInst;
                    if (inst instanceof IRFunctionCall) {
                        FuncInfo calleeInfo = funcInfoMap.get(((IRFunctionCall) inst).getFunc());
                        if (calleeInfo != null) {
                            ++calleeInfo.numCalled;
                        }
                    }
                }
            }
        }

        List<BasicBlock> reversePostOrder = new ArrayList<>();
        List<String> unCalledFuncs = new ArrayList<>();
        boolean changed = true, thisFuncChanged;
        while (changed) {
            changed = false;
            unCalledFuncs.clear();
            for (IRFunction irFunction : ir.getFuncs().values()) {
                FuncInfo funcInfo = funcInfoMap.get(irFunction);
                reversePostOrder.clear();
                reversePostOrder.addAll(irFunction.getReversePostOrder());
                thisFuncChanged = false;
                for (BasicBlock bb : reversePostOrder) {
                    for (IRInstruction inst = bb.getFirstInst(), nextInst; inst != null; inst = nextInst) {
                        // inst.getNextInst() may be changed later
                        nextInst = inst.getNextInst();
                        if (!(inst instanceof IRFunctionCall)) continue;
                        FuncInfo calleeInfo = funcInfoMap.get(((IRFunctionCall) inst).getFunc());
                        if (calleeInfo == null) continue; // skip built-in functions
                        if (calleeInfo.recursiveCall) continue; // skip self recursive function
                        if (calleeInfo.memFunc) continue;
                        if (calleeInfo.numInst > MAX_LOW_INLINE_INST || calleeInfo.numInst + funcInfo.numInst > MAX_FUNC_INST) continue;

                        nextInst = inlineFunctionCall((IRFunctionCall) inst);
                        funcInfo.numInst += calleeInfo.numInst;
                        changed = true;
                        thisFuncChanged = true;
                        --calleeInfo.numCalled;
                        if (calleeInfo.numCalled == 0) {
                            unCalledFuncs.add(((IRFunctionCall) inst).getFunc().getName());
                        }
                    }
                }
                if (thisFuncChanged) {
                    irFunction.calcReversePostOrder();
                }
            }
            for (String funcName : unCalledFuncs) {
                ir.removeFunc(funcName);
            }
        }
        for (IRFunction irFunction : ir.getFuncs().values()) {
            irFunction.updateCalleeSet();
        }
        ir.updateCalleeSet();

        // inline recursive functions
        reversePostOrder = new ArrayList<>();
        changed = true;
        for (int i = 0; changed && i < MAX_INLINE_DEPTH; ++i) {
            changed = false;

            // bak up self recursive functions
            funcBakUpMap.clear();
            for (IRFunction irFunction : ir.getFuncs().values()) {
                FuncInfo funcInfo = funcInfoMap.get(irFunction);
                if (!funcInfo.recursiveCall) continue;
                funcBakUpMap.put(irFunction, genBakUpFunc(irFunction));
            }

            for (IRFunction irFunction : ir.getFuncs().values()) {
                FuncInfo funcInfo = funcInfoMap.get(irFunction);
                reversePostOrder.clear();
                reversePostOrder.addAll(irFunction.getReversePostOrder());
                thisFuncChanged = false;
                for (BasicBlock bb : reversePostOrder) {
                    for (IRInstruction inst = bb.getFirstInst(), nextInst; inst != null; inst = nextInst) {
                        // inst.getNextInst() may be changed later
                        nextInst = inst.getNextInst();
                        if (!(inst instanceof IRFunctionCall)) continue;
                        FuncInfo calleeInfo = funcInfoMap.get(((IRFunctionCall) inst).getFunc());
                        if (calleeInfo == null) continue; // skip built-in functions
                        if (calleeInfo.memFunc) continue;
                        if (calleeInfo.numInst > MAX_INLINE_INST || calleeInfo.numInst + funcInfo.numInst > MAX_FUNC_INST) continue;

                        nextInst = inlineFunctionCall((IRFunctionCall) inst);
                        int numAddInst = calleeInfo.numInst;
                        funcInfo.numInst += numAddInst;
                        changed = true;
                        thisFuncChanged = true;
                    }
                }
                if (thisFuncChanged) {
                    irFunction.calcReversePostOrder();
                }
            }
        }
        for (IRFunction irFunction : ir.getFuncs().values()) {
            irFunction.updateCalleeSet();
        }
        ir.updateCalleeSet();
    }

    private IRFunction genBakUpFunc(IRFunction func) {
        IRFunction bakFunc = new IRFunction();
        Map<Object, Object> bbRenameMap = new HashMap<>();
        for (BasicBlock bb : func.getReversePostOrder()) {
            bbRenameMap.put(bb, new BasicBlock(bakFunc, bb.getName()));
        }
        for (BasicBlock bb : func.getReversePostOrder()) {
            BasicBlock bakBB = (BasicBlock) bbRenameMap.get(bb);
            for (IRInstruction inst = bb.getFirstInst(); inst != null; inst = inst.getNextInst()) {
                if (inst instanceof IRJumpInstruction) {
                    bakBB.setJumpInst((IRJumpInstruction) inst.copyRename(bbRenameMap));
                } else {
                    bakBB.addInst(inst.copyRename(bbRenameMap));
                }
            }
        }
        bakFunc.setStartBB((BasicBlock) bbRenameMap.get(func.getStartBB()));
        bakFunc.setEndBB((BasicBlock) bbRenameMap.get(func.getEndBB()));
        bakFunc.setArgVRegList(func.getArgVRegList());
        return bakFunc;
    }

    private IRInstruction inlineFunctionCall(IRFunctionCall funcCallInst) {
        IRFunction callerFunc = funcCallInst.getParentBB().getFunc(), calleeFunc;
        calleeFunc = funcBakUpMap.getOrDefault(funcCallInst.getFunc(), funcCallInst.getFunc());
        List<BasicBlock> reversePostOrder = calleeFunc.getReversePostOrder();

        Map<Object, Object> renameMap = new HashMap<>();
        BasicBlock oldEndBB = calleeFunc.getEndBB();
        BasicBlock newEndBB = new BasicBlock(callerFunc, oldEndBB.getName());
        renameMap.put(oldEndBB, newEndBB);
        renameMap.put(calleeFunc.getStartBB(), funcCallInst.getParentBB());
        if (callerFunc.getEndBB() == funcCallInst.getParentBB()) {
            callerFunc.setEndBB(newEndBB);
        }

        Map<Object, Object> callBBRenameMap = Collections.singletonMap(funcCallInst.getParentBB(), newEndBB);
        for (IRInstruction inst = funcCallInst.getNextInst(); inst != null; inst = inst.getNextInst()) {
            if (inst instanceof IRJumpInstruction) {
                newEndBB.setJumpInst(((IRJumpInstruction) inst).copyRename(callBBRenameMap));
            } else {
                newEndBB.addInst(inst.copyRename(callBBRenameMap));
            }
            inst.remove();
        }
        IRInstruction newEndBBFisrtInst = newEndBB.getFirstInst();
        for (int i = 0; i < funcCallInst.getArgs().size(); ++i) {
            VirtualRegister oldArgVreg = calleeFunc.getArgVRegList().get(i);
            VirtualRegister newArgVreg = oldArgVreg.copy();
            funcCallInst.prependInst(new IRMove(funcCallInst.getParentBB(), newArgVreg, funcCallInst.getArgs().get(i)));
            renameMap.put(oldArgVreg, newArgVreg);
        }
        funcCallInst.remove();
        for (BasicBlock bb : reversePostOrder) {
            if (!renameMap.containsKey(bb)) {
                renameMap.put(bb, new BasicBlock(callerFunc, bb.getName()));
            }
        }
        for (BasicBlock oldBB : reversePostOrder) {
            BasicBlock newBB = (BasicBlock) renameMap.get(oldBB);
            if (oldBB.forNode != null) {
                IRRoot.ForRecord forRec = ir.forRecMap.get(oldBB.forNode);
                if (forRec.condBB == oldBB) forRec.condBB = newBB;
                if (forRec.stepBB == oldBB) forRec.stepBB = newBB;
                if (forRec.bodyBB == oldBB) forRec.bodyBB = newBB;
                if (forRec.afterBB == oldBB) forRec.afterBB = newBB;
            }
            for (IRInstruction inst = oldBB.getFirstInst(); inst != null; inst = inst.getNextInst()) {
                for (RegValue usedRegValue : inst.getUsedRegValues()) {
                    copyRegValue(renameMap, usedRegValue);
                }
                if (inst.getDefinedRegister() != null) {
                    copyRegValue(renameMap, inst.getDefinedRegister());
                }
                if (newBB == newEndBB) {
                    if (!(inst instanceof IRReturn)) {
                        newEndBBFisrtInst.prependInst(inst.copyRename(renameMap));
                    }
                } else {
                    if (inst instanceof IRJumpInstruction) {
                        if (!(inst instanceof IRReturn)) {
                            newBB.setJumpInst(((IRJumpInstruction) inst).copyRename(renameMap));
                        }
                    } else {
                        newBB.addInst(inst.copyRename(renameMap));
                    }
                }
            }
        }
        if (!funcCallInst.getParentBB().isHasJumpInst()) {
            funcCallInst.getParentBB().setJumpInst(new IRJump(funcCallInst.getParentBB(), newEndBB));
        }
        IRReturn returnInst = calleeFunc.getRetInstList().get(0);
        if (returnInst.getRetValue() != null) {
            newEndBBFisrtInst.prependInst(new IRMove(newEndBB, funcCallInst.getDest(), (RegValue) renameMap.get(returnInst.getRetValue())));
        }

        return newEndBB.getFirstInst();
    }

    private void copyRegValue(Map<Object, Object> renameMap, RegValue regValue) {
        if (!renameMap.containsKey(regValue)) {
            renameMap.put(regValue, regValue.copy());
        }
    }
}