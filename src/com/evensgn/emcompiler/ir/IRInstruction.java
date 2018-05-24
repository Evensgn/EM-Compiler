package com.evensgn.emcompiler.ir;

public abstract class IRInstruction {
    private IRInstruction prevInst = null, nextInst = null;
    private BasicBlock parentBB;

    public IRInstruction(BasicBlock parentBB) {
        this.parentBB = parentBB;
    }

    public void setPrevInst(IRInstruction prevInst) {
        this.prevInst = prevInst;
    }

    public void setNextInst(IRInstruction nextInst) {
        this.nextInst = nextInst;
    }
}
