package com.evensgn.emcompiler.ir;

public class IRJump extends IRJumpInstruction {
    private BasicBlock targetBB;

    public IRJump(BasicBlock parentBB, BasicBlock targetBB) {
        super(parentBB);
        this.targetBB = targetBB;
    }

    public BasicBlock getTargetBB() {
        return targetBB;
    }
}
