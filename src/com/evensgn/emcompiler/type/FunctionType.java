package com.evensgn.emcompiler.type;

public class FunctionType extends Type {
    private String name;

    public FunctionType(String name) {
        hyperType = HyperTypes.FUNCTION;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof FunctionType)) return false;
        return name.equals(((FunctionType) obj).name);
    }

    @Override
    public String toString() {
        return String.format("FunctionType(%s)", name);
    }
}