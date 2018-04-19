package com.evensgn.emcompiler.ast;

public class StringConstExprNode extends ConstExprNode {
    private String value;
    private Location location;

    public StringConstExprNode(String value, Location location) {
        this.value = value;
        this.location = location;
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