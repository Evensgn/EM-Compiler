package com.evensgn.emcompiler.ast;

public class BinaryExprNode extends ExprNode {
    public enum BinaryOps {
        MUL, DIV, MOD,
        ADD, SUB, SHL, SHR,
        GREATER, LESS, GREATER_EQUAL, LESS_EQUAL, EQUAL, INEQUAL,
        BITWISE_AND, BITWISE_OR, BITWISE_XOR, LOGIC_AND, LOGIC_OR
    }

    private BinaryOps op;
    private ExprNode lhs, rhs;

    public BinaryExprNode(BinaryOps op, ExprNode lhs, ExprNode rhs, Location location) {
        this.op = op;
        this.lhs = lhs;
        this.rhs = rhs;
        this.location = location;
    }

    public ExprNode getLhs() {
        return lhs;
    }

    public ExprNode getRhs() {
        return rhs;
    }

    public BinaryOps getOp() {
        return op;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
