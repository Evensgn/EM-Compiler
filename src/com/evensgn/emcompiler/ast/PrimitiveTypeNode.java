package com.evensgn.emcompiler.ast;

public class PrimitiveTypeNode extends TypeNode {
    private Location location;

    public PrimitiveTypeNode(Location location) {
        this.location = location;
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
