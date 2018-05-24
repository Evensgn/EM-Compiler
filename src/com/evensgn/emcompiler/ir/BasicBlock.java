package com.evensgn.emcompiler.ir;

public class BasicBlock {
    private IRInstruction firstInst = null, lastInst = null;
    private IRFunction func;

    public BasicBlock(IRFunction func) {
        this.func = func;
    }

    public void appendInst(IRInstruction inst) {
        if (lastInst == null) {
            firstInst = lastInst = inst;
        } else {
            lastInst.setNextInst(inst);
            lastInst = inst;
        }
    }
}
