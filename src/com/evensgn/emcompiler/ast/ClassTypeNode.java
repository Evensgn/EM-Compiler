package com.evensgn.emcompiler.ast;

public class ClassTypeNode extends TypeNode {
    private Location location;
    private String name;

    public ClassTypeNode(Location location, String name) {
        this.location = location;
        this.name = name;
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
