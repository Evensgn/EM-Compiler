package com.evensgn.emcompiler.ast;

public class BinaryExprNode extends ExprNode {
    public enum BinaryOps {
        MUL, DIV, MOD,
        ADD, SUB, SHL, SHR,
        GREATER, LESS, GREATER_EQUAL, LESS_EQUAL, EQUAL, INEQUAL,
        LOGIC_AND, LOGIC_XOR, LOGIC_OR, BOOL_AND, BOOL_OR
    }

    private ExprNode lhs, rhs;
    private BinaryOps op;

    public BinaryExprNode(ExprNode lhs, ExprNode rhs, BinaryOps op) {
        this.lhs = lhs;
        this.rhs = rhs;
        this.op = op;
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
    public Location location() {
        return lhs.location();
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
