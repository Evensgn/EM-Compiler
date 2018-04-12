package com.evensgn.emcompiler.ast;

public class IdentifierExprNode extends ExprNode {
    private Location location;
    private String identifier;

    public IdentifierExprNode(Location location, String identifier) {
        this.location = location;
        this.identifier = identifier;
    }

    public Location getLocation() {
        return location;
    }

    public String getIdentifier() {
        return identifier;
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
