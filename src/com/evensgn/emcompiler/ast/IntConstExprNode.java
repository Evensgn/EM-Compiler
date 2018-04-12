package com.evensgn.emcompiler.ast;

public class IntConstExprNode extends ConstExprNode {
    private Location location;
    private int value;

    public IntConstExprNode(Location location, int value) {
        this.location = location;
        this.value = value;
    }

    public int getValue() {
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
