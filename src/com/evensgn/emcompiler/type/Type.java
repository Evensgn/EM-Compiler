package com.evensgn.emcompiler.type;

abstract public class Type {
    public enum HyperTypes {
        PRIMITIVE, CLASS, ARRAY, FUNCTION
    }

    public enum PrimitiveTypes {
        VOID, INT, BOOL, STRING
    }

    protected HyperTypes hyperType;

    public HyperTypes getHyperType() {
        return hyperType;
    }
}
