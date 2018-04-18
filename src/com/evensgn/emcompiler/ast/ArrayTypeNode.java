package com.evensgn.emcompiler.ast;

public class ArrayTypeNode extends TypeNode {
    private TypeNode baseType;
    private Location location;

    public ArrayTypeNode(TypeNode baseType, Location location) {
        this.baseType = baseType;
        this.location = location;
    }

    public TypeNode getBaseType() {
        return baseType;
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
