package com.evensgn.emcompiler.ast;

public class IntConstExprNode extends ConstExprNode {
    private int value;
    private Location location;

    public IntConstExprNode(int value, Location location) {
        this.value = value;
        this.location = location;
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
