package com.evensgn.emcompiler.type;

public class FunctionType extends Type {
    private String name;

    public FunctionType(String name) {
        this.hyperType = HyperTypes.FUNCTION;
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
