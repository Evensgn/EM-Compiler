package com.evensgn.emcompiler.ir;

public class IRJump extends IRJumpInstruction {
    private BasicBlock targetBB;

    public IRJump(BasicBlock parentBB) {
        super(parentBB);
    }

    public BasicBlock getTargetBB() {
        return targetBB;
    }
}
