package com.evensgn.emcompiler.backend;

import com.evensgn.emcompiler.Configuration;
import com.evensgn.emcompiler.ir.*;
import com.evensgn.emcompiler.nasm.NASMRegisterSet;

public class RegisterPreprocessor {
    private IRRoot ir;

    public RegisterPreprocessor(IRRoot ir) {
        this.ir = ir;
    }

    private void processFuncArgs(IRFunction func) {
        IRInstruction firtInst = func.getStartBB().getFirstInst();
        for (int i = 6; i < func.getArgVRegList().size(); ++i) {
            VirtualRegister argVreg = func.getArgVRegList().get(i);
            StackSlot argSlot = new StackSlot(func, "arg" + i, true);
            func.getArgsStackSlotMap().put(argVreg, argSlot);
            firtInst.prependInst(new IRLoad(firtInst.getParentBB(), argVreg, Configuration.getRegSize(), argSlot, 0));
        }
        if (func.getArgVRegList().size() > 0) func.getArgVRegList().get(0).setForcedPhysicalRegister(NASMRegisterSet.rdi);
        if (func.getArgVRegList().size() > 1) func.getArgVRegList().get(1).setForcedPhysicalRegister(NASMRegisterSet.rsi);
        if (func.getArgVRegList().size() > 2) func.getArgVRegList().get(2).setForcedPhysicalRegister(NASMRegisterSet.rdx);
        if (func.getArgVRegList().size() > 3) func.getArgVRegList().get(3).setForcedPhysicalRegister(NASMRegisterSet.rcx);
        if (func.getArgVRegList().size() > 4) func.getArgVRegList().get(4).setForcedPhysicalRegister(NASMRegisterSet.r8);
        if (func.getArgVRegList().size() > 5) func.getArgVRegList().get(5).setForcedPhysicalRegister(NASMRegisterSet.r9);
    }

    public void run() {
        for (IRFunction irFunction : ir.getFuncs().values()) {
            processFuncArgs(irFunction);
        }
        /*for (IRFunction irFunction : ir.getFuncs().values()) {
            for (BasicBlock bb : irFunction.getReversePreOrder()) {
                for (IRInstruction inst = bb.getFirstInst(); inst != null; inst = inst.getNextInst()) {
                    if (inst instanceof IRHeapAlloc) {
                        if (irFunction.getArgVRegList().size() > 0) {
                            // BLANK
                        }
                    }
                }
            }
        }*/
    }
}
