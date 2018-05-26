package com.evensgn.emcompiler.ir;

public class IRComparison extends IRInstruction {
    public enum IRCmpOp {
        GREATER, LESS, GREATER_EQUAL, LESS_EQUAL, EQUAL, INEQUAL
    }

    private IRRegister dest;
    private IRCmpOp op;
    private RegValue lhs, rhs;

    public IRComparison(BasicBlock parentBB, IRRegister dest, IRCmpOp op, RegValue lhs, RegValue rhs) {
        super(parentBB);
        this.dest = dest;
        this.op = op;
        this.lhs = lhs;
        this.rhs = rhs;
    }
}
