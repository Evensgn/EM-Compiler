package com.evensgn.emcompiler.ast;

public class SubscriptExprNode extends ExprNode {
    private ExprNode expr;
    private ExprNode subscript;

    public SubscriptExprNode(ExprNode expr, ExprNode subscript) {
        this.expr = expr;
        this.subscript = subscript;
    }

    public ExprNode getExpr() {
        return expr;
    }

    public ExprNode getSubscript() {
        return subscript;
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
