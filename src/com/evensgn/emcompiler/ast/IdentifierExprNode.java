package com.evensgn.emcompiler.ast;

public class IdentifierExprNode extends ExprNode {
    private String identifier;

    public IdentifierExprNode(String identifier, Location location) {
        this.identifier = identifier;
        this.location = location;
    }

    public String getIdentifier() {
        return identifier;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
