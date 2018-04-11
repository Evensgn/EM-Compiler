package com.evensgn.emcompiler.ast;

public class SubscriptExprNode extends ExprNode {
    private ExprNode expr;
    private ExprNode idx;

    public SubscriptExprNode(ExprNode expr, ExprNode idx) {
        this.expr = expr;
        this.idx = idx;
    }

    public ExprNode getExpr() {
        return expr;
    }

    public ExprNode getIdx() {
        return idx;
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
