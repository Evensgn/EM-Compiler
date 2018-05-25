package com.evensgn.emcompiler.ir;

public class IRBranch extends IRJumpInstruction {
    private BasicBlock thenBB, elseBB;

    public IRBranch(BasicBlock parentBB) {
        super(parentBB);
    }

    public BasicBlock getThenBB() {
        return thenBB;
    }

    public BasicBlock getElseBB() {
        return elseBB;
    }
}
