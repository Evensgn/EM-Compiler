package com.evensgn.emcompiler.type;

public class ClassType extends Type {
    private String name;

    public ClassType(String name) {
        this.hyperType = HyperTypes.CLASS;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return String.format("ClassType(%s)", name);
    }
}
