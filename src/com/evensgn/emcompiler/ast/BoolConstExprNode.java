package com.evensgn.emcompiler.ast;

public class BoolConstExprNode extends ConstExprNode {
    private Location location;
    private boolean value;

    public BoolConstExprNode(Location location, boolean value) {
        this.location = location;
        this.value = value;
    }

    public boolean getValue() {
        return value;
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
