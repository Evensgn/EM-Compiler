package com.evensgn.emcompiler.type;

public class BoolType extends PrimitiveType {
    static private BoolType instance = new BoolType();

    private BoolType() {
        hyperType = HyperTypes.BOOL;
    }

    public static BoolType getInstance() {
        return instance;
    }
}
