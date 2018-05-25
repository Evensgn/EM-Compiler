package com.evensgn.emcompiler.ir;

public class IRReturn extends IRJumpInstruction {
    private RegValue retValue;

    public IRReturn(BasicBlock parentBB, RegValue retValue) {
        super(parentBB);
        this.retValue = retValue;
    }
}
