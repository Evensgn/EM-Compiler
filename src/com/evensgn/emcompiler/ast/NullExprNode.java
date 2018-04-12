package com.evensgn.emcompiler.ast;

public class NullExprNode extends ExprNode {
    private Location location;

    public NullExprNode(Location location) {
        this.location = location;
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
