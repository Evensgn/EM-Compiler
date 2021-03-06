package com.evensgn.emcompiler.ast;

public class SubscriptExprNode extends ExprNode {
    private ExprNode arr, sub;

    public SubscriptExprNode(ExprNode arr, ExprNode sub, Location location) {
        this.arr = arr;
        this.sub = sub;
        this.location = location;
    }

    public ExprNode getArr() {
        return arr;
    }

    public ExprNode getSub() {
        return sub;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
