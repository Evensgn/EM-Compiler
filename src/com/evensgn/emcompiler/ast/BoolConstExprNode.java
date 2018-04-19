package com.evensgn.emcompiler.ast;

public class BoolConstExprNode extends ConstExprNode {
    private boolean value;

    public BoolConstExprNode(boolean value, Location location) {
        this.value = value;
        this.location = location;
    }

    public boolean getValue() {
        return value;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
