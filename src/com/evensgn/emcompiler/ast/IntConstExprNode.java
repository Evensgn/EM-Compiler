package com.evensgn.emcompiler.ast;

public class IntConstExprNode extends ConstExprNode {
    private int value;

    public IntConstExprNode(int value, Location location) {
        this.value = value;
        this.location = location;
    }

    public int getValue() {
        return value;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
