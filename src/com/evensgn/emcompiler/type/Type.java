package com.evensgn.emcompiler.type;

abstract public class Type {
    public enum HyperTypes {
        VOID, INT, BOOL, STRING, CLASS, ARRAY, FUNCTION
    }

    HyperTypes hyperType;

    public HyperTypes getHyperType() {
        return hyperType;
    }
}
