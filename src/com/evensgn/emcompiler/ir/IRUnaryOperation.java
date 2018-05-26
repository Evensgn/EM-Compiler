package com.evensgn.emcompiler.ir;

public class IRUnaryOperation extends IRInstruction {
    public enum IRUnaryOp {
        BITWISE_NOT, NEG
    }
    private IRRegister dest;
    private IRUnaryOp op;
    private RegValue rhs;

    public IRUnaryOperation(BasicBlock parentBB, IRRegister dest, IRUnaryOp op, RegValue rhs) {
        super(parentBB);
        this.dest = dest;
        this.op = op;
        this.rhs = rhs;
    }
}
