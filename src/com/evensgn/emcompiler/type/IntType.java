package com.evensgn.emcompiler.type;

public class IntType extends PrimitiveType {
    static private IntType instance = new IntType();

    private IntType() {
        hyperType = HyperTypes.INT;
    }

    public static IntType getInstance() {
        return instance;
    }
}