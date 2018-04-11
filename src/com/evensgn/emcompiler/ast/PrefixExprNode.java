package com.evensgn.emcompiler.ast;

public class PrefixExprNode extends ExprNode {
    public enum PrefixOps {
        PREFIX_ADD, PREFIX_DEC, POS, NEG, LOGIC_NOT, BITWISE_NOT
    }

    private PrefixOps op;
    private ExprNode expr;

    public PrefixExprNode(PrefixOps op, ExprNode expr) {
        this.op = op;
        this.expr = expr;
    }

    public PrefixOps getOp() {
        return op;
    }

    public ExprNode getExpr() {
        return expr;
    }

    @Override
    public Location location() {
        return expr.location();
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
