package com.evensgn.emcompiler.ir;

public class IRBinaryOperation extends IRInstruction {
    public enum IRBinaryOp {
        ADD, SUB, MUL, DIV, MOD,
        SHL, SHR,
        BITWISE_AND, BITWISE_OR, BITWISE_XOR
    }

    private IRRegister dest;
    private IRBinaryOp op;
    private RegValue lhs, rhs;

    public IRBinaryOperation(BasicBlock parentBB, IRRegister dest, IRBinaryOp op, RegValue lhs, RegValue rhs) {
        super(parentBB);
        this.dest = dest;
        this.op = op;
        this.lhs = lhs;
        this.rhs = rhs;
    }

    public void accept(IRVisitor visitor) {
        visitor.visit(this);
    }

    public IRBinaryOp getOp() {
        return op;
    }

    public IRRegister getDest() {
        return dest;
    }

    public RegValue getLhs() {
        return lhs;
    }

    public RegValue getRhs() {
        return rhs;
    }
}
