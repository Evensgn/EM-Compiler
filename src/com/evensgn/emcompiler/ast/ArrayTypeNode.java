package com.evensgn.emcompiler.ast;

public class ArrayTypeNode extends TypeNode {
    private TypeNode baseType;

    public ArrayTypeNode(TypeNode baseType) {
        this.baseType = baseType;
    }

    public TypeNode getBaseType() {
        return baseType;
    }

    @Override
    public Location location() {
        return baseType.location();
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
