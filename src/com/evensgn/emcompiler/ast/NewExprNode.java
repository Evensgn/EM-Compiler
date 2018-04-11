package com.evensgn.emcompiler.ast;

public class NewExprNode extends ExprNode {
    private TypeNode type;
    private

    @Override
    public Location location() {
        return null;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
