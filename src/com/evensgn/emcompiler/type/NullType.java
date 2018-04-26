package com.evensgn.emcompiler.type;

public class NullType extends Type {
    static private NullType instance = new NullType();

    private NullType() {
        hyperType = HyperTypes.NULL;
    }

    public static NullType getInstance() {
        return instance;
    }

    @Override
    public String toString() {
        return "NullType";
    }
}