package com.evensgn.emcompiler.type;

public class StringType extends PrimitiveType {
    static private StringType instance = new StringType();

    private StringType() {
        hyperType = HyperTypes.STRING;
    }

    public static StringType getInstance() {
        return instance;
    }
}
