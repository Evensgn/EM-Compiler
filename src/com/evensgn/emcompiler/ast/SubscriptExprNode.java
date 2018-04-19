package com.evensgn.emcompiler.ast;

public class SubscriptExprNode extends ExprNode {
    private ExprNode arr, sub;
    private Location location;

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
    public Location location() {
        return location;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
