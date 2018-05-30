package com.evensgn.emcompiler.backend;

import com.evensgn.emcompiler.ir.*;

import java.util.*;

public class FunctionInlineProcessor {
    private final int MAX_INLINE_INST = 1 << 10;
    private final int MAX_FUNC_INST = 1 << 16;

    private IRRoot ir;

    private class FuncInfo {
        int numInst = 0, numCalled = 0;
        boolean recursiveCall;
    }

    private Map<IRFunction, FuncInfo> funcInfoMap = new HashMap<>();

    public FunctionInlineProcessor(IRRoot ir) {
        this.ir = ir;
    }

    public void run() {
        for (IRFunction irFunction : ir.getFuncs().values()) {
            irFunction.setRecursiveCall(irFunction.calleeSet.contains(irFunction));
            FuncInfo funcInfo = new FuncInfo();
            funcInfo.recursiveCall = irFunction.isRecursiveCall();
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
                        if (calleeInfo == null) continue;
                        if (calleeInfo.recursiveCall) continue;
                        if (calleeInfo.numInst > MAX_INLINE_INST || calleeInfo.numInst + funcInfo.numInst > MAX_FUNC_INST) continue;

                        inlineFunctionCall((IRFunctionCall) inst);
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
    }


    private void inlineFunctionCall(IRFunctionCall funcCallInst) {
        IRFunction callerFunc = funcCallInst.getParentBB().getFunc(), calleeFunc = funcCallInst.getFunc();
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
            for (IRInstruction inst = oldBB.getFirstInst(); inst != null; inst = inst.getNextInst()) {
                for (RegValue usedRegValue : inst.getUsedRegValues()) {
                    copyRegValue(renameMap, usedRegValue);
                }

            }
        }
    }

    private void copyRegValue(Map<Object, Object> renameMap, RegValue regValue) {
        if (!renameMap.containsKey(regValue)) {
            renameMap.put(regValue, regValue.copy());
        }
    }
}