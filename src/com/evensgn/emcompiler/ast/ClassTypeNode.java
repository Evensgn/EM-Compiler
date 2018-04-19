package com.evensgn.emcompiler.ast;

public class ClassTypeNode extends TypeNode {
    private String name;
    private Location location;

    public ClassTypeNode(String name, Location location) {
        this.name = name;
        this.location = location;
    }

    public String getName() {
        return name;
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
