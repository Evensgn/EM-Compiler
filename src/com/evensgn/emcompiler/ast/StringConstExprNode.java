package com.evensgn.emcompiler.ast;

public class StringConstExprNode extends ConstExprNode {
    private Location location;
    private String value;

    public StringConstExprNode(Location location, String value) {
        this.location = location;
        this.value = value;
    }

    public String getValue() {
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