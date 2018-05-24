package com.evensgn.emcompiler.ir;

public abstract class IRJumpInstruction extends IRInstruction {
    public IRJumpInstruction(BasicBlock parentBB) {
        super(parentBB);
    }
}
