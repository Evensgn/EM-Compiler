package com.evensgn.emcompiler.ir;

public class IRBranch extends IRJumpInstruction {
    private RegValue cond;
    private BasicBlock thenBB, elseBB;

    public IRBranch(BasicBlock parentBB, RegValue cond, BasicBlock thenBB, BasicBlock elseBB) {
        super(parentBB);
        this.cond = cond;
        this.thenBB = thenBB;
        this.elseBB = elseBB;
    }

    public BasicBlock getThenBB() {
        return thenBB;
    }

    public BasicBlock getElseBB() {
        return elseBB;
    }
}
