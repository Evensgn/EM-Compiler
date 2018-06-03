package com.evensgn.emcompiler.backend;

import com.evensgn.emcompiler.Configuration;
import com.evensgn.emcompiler.ir.*;

import java.util.*;

import static com.evensgn.emcompiler.nasm.NASMRegisterSet.*;

public class NASMTransformer {
    private IRRoot ir;

    public NASMTransformer(IRRoot ir) {
        this.ir = ir;
    }

    private class FuncInfo {
        List<PhysicalRegister> usedCallerSaveRegs = new ArrayList<>();
        List<PhysicalRegister> usedCalleeSaveRegs = new ArrayList<>();
        Set<PhysicalRegister> recursiveUsedRegs = new HashSet<>();
        Map<StackSlot, Integer> stackSlotOffsetMap = new HashMap<>();
        int numExtraArgs, numStackSlot = 0;
    }

    private Map<IRFunction, FuncInfo> funcInfoMap = new HashMap<>();

    public void run() {
        for (IRFunction irFunction : ir.getFuncs().values()) {
            FuncInfo funcInfo = new FuncInfo();
            for (PhysicalRegister preg : irFunction.getUsedPhysicalGeneralRegs()) {
                if (preg.isCalleeSave()) funcInfo.usedCalleeSaveRegs.add(preg);
                if (preg.isCallerSave()) funcInfo.usedCallerSaveRegs.add(preg);
            }

            funcInfo.numStackSlot = irFunction.getStackSlots().size();
            for (int i = 0; i < funcInfo.numStackSlot; ++i) {
                funcInfo.stackSlotOffsetMap.put(irFunction.getStackSlots().get(i), -i * Configuration.getRegSize());
            }
            // for rsp alignment
            if ((funcInfo.usedCalleeSaveRegs.size() + funcInfo.numStackSlot) % 2 == 0) {
                ++funcInfo.numStackSlot;
            }

            funcInfo.numExtraArgs = irFunction.getArgVRegList().size() - 6;
            if (funcInfo.numExtraArgs < 0) funcInfo.numExtraArgs = 0;

            int extraArgOffset = -(funcInfo.usedCalleeSaveRegs.size() + funcInfo.numStackSlot + 1) * Configuration.getRegSize(); // return address
            for (int i = 6; i < irFunction.getArgVRegList().size(); ++i) {
                funcInfo.stackSlotOffsetMap.put(irFunction.getArgsStackSlotMap().get(irFunction.getArgVRegList().get(i)), extraArgOffset);
                extraArgOffset -= Configuration.getRegSize();
            }
            funcInfoMap.put(irFunction, funcInfo);
        }

        // TO DO add funcInfo for built-in functions
        for (IRFunction irFunction : funcInfoMap.keySet()) {
            FuncInfo funcInfo = funcInfoMap.get(irFunction);
            funcInfo.recursiveUsedRegs.addAll(irFunction.getUsedPhysicalGeneralRegs());
            for (IRFunction calleeFunc : irFunction.recursiveCalleeSet) {
                funcInfo.recursiveUsedRegs.addAll(calleeFunc.getUsedPhysicalGeneralRegs());
            }
        }

        for (IRFunction irFunction : ir.getFuncs().values()) {
            FuncInfo funcInfo = funcInfoMap.get(irFunction);

            // transform function entry
            BasicBlock entryBB = irFunction.getStartBB();
            IRInstruction firstInst = entryBB.getFirstInst();
            for (PhysicalRegister preg : funcInfo.usedCalleeSaveRegs) {
                firstInst.prependInst(new IRPush(entryBB, preg));
            }
            if (funcInfo.numStackSlot > 0)
                firstInst.prependInst(new IRBinaryOperation(entryBB, rsp, IRBinaryOperation.IRBinaryOp.SUB, rsp, new IntImmediate(funcInfo.numStackSlot * Configuration.getRegSize())));

            for (BasicBlock bb : irFunction.getReversePostOrder()) {
                for (IRInstruction inst = bb.getFirstInst(); inst != null; inst = inst.getNextInst()) {
                    if (inst instanceof IRFunctionCall) {
                        IRFunction calleeFunc = ((IRFunctionCall) inst).getFunc();
                        FuncInfo calleeInfo = funcInfoMap.get(calleeFunc);
                        // push caller save registers which would be changed by callee
                        for (PhysicalRegister preg : funcInfo.usedCallerSaveRegs) {
                            if (calleeInfo.recursiveUsedRegs.contains(preg)) {
                                inst.prependInst(new IRPush(inst.getParentBB(), preg));
                            }
                        }

                        // set arguments
                        List<RegValue> args = ((IRFunctionCall) inst).getArgs();
                        if (calleeFunc.isBuiltIn()) {
                            // TO DO process built-in functions
                        } else {
                            if (args.size() > 0) inst.prependInst(new IRMove(inst.getParentBB(), rdi, args.get(0)));
                            if (args.size() > 1) inst.prependInst(new IRMove(inst.getParentBB(), rsi, args.get(1)));
                            if (args.size() > 2) inst.prependInst(new IRMove(inst.getParentBB(), rdx, args.get(2)));
                            if (args.size() > 3) inst.prependInst(new IRMove(inst.getParentBB(), rcx, args.get(3)));
                            if (args.size() > 4) inst.prependInst(new IRMove(inst.getParentBB(), r8, args.get(4)));
                            if (args.size() > 5) inst.prependInst(new IRMove(inst.getParentBB(), r9, args.get(5)));
                            for (int i = args.size() - 1; i > 5; --i) {
                                inst.prependInst(new IRPush(inst.getParentBB(), args.get(i)));
                            }
                        }

                        // remove extra arguments
                        if (funcInfo.numExtraArgs > 0) {
                            inst.appendInst(new IRBinaryOperation(inst.getParentBB(), rsp, IRBinaryOperation.IRBinaryOp.SUB, rsp, new IntImmediate(funcInfo.numExtraArgs * Configuration.getRegSize())));
                        }

                        // get return value
                        if (((IRFunctionCall) inst).getDest() != null) {
                            inst.appendInst(new IRMove(inst.getParentBB(), ((IRFunctionCall) inst).getDest(), rax));
                        }

                        // restore caller save registers
                        for (PhysicalRegister preg : funcInfo.usedCallerSaveRegs) {
                            if (calleeInfo.recursiveUsedRegs.contains(preg)) {
                                inst.appendInst(new IRPush(inst.getParentBB(), preg));
                            }
                        }
                    } else if (inst instanceof IRHeapAlloc) {
                        // push caller save registers which would be changed by callee
                        for (PhysicalRegister preg : funcInfo.usedCallerSaveRegs) {
                            // could be optimized known which reg would not be changed by malloc
                            inst.prependInst(new IRPush(inst.getParentBB(), preg));
                        }
                        // set arg
                        inst.prependInst(new IRMove(inst.getParentBB(), rdi, ((IRHeapAlloc) inst).getAllocSize()));
                        // get return value
                        inst.appendInst(new IRMove(inst.getParentBB(), ((IRHeapAlloc) inst).getDest(), rax));
                        // restore caller save registers
                        for (PhysicalRegister preg : funcInfo.usedCallerSaveRegs) {
                            // could be optimized known which reg would not be changed by malloc
                            inst.appendInst(new IRPush(inst.getParentBB(), preg));
                        }
                    } else if (inst instanceof IRLoad) {
                        if (((IRLoad) inst).getAddr() instanceof StackSlot) {
                            ((IRLoad) inst).setAddrOffset(funcInfo.stackSlotOffsetMap.get(((IRLoad) inst).getAddr()));
                            ((IRLoad) inst).setAddr(rsp);
                        }
                    } else if (inst instanceof IRStore) {
                        if (((IRStore) inst).getAddr() instanceof StackSlot) {
                            ((IRStore) inst).setAddrOffset(funcInfo.stackSlotOffsetMap.get(((IRStore) inst).getAddr()));
                            ((IRStore) inst).setAddr(rsp);
                        }
                    } else if (inst instanceof IRMove) {
                        // remove useless move: a <- a
                        if (((IRMove) inst).getLhs() == ((IRMove) inst).getRhs()) {
                            inst.remove();
                        }
                    }
                }

                IRReturn retInst = irFunction.getRetInstList().get(0);
                if (retInst.getRetValue() != null) {
                    retInst.prependInst(new IRMove(retInst.getParentBB(), rax, retInst.getRetValue()));
                }
            }

            // transform function exit
            BasicBlock exitBB = irFunction.getEndBB();
            IRInstruction lastInst = exitBB.getLastInst();
            if (funcInfo.numStackSlot > 0)
                lastInst.prependInst(new IRBinaryOperation(entryBB, rsp, IRBinaryOperation.IRBinaryOp.ADD, rsp, new IntImmediate(funcInfo.numStackSlot * Configuration.getRegSize())));
            for (int i = funcInfo.usedCalleeSaveRegs.size() - 1; i >= 0; --i) {
                lastInst.prependInst(new IRPop(entryBB, funcInfo.usedCalleeSaveRegs.get(i)));
            }
        }
    }
}