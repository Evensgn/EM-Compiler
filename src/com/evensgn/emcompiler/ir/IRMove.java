package com.evensgn.emcompiler.ir;

public class IRMove extends IRInstruction {
    private IRRegister lhs;
    private RegValue rhs;

    public IRMove(BasicBlock parentBB, IRRegister lhs, RegValue rhs) {
        super(parentBB);
        this.lhs = lhs;
        this.rhs = rhs;
    }
}
