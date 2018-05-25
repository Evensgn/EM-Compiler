package com.evensgn.emcompiler.type;

abstract public class Type {
    public enum HyperTypes {
        VOID, INT, BOOL, STRING, CLASS, ARRAY, FUNCTION, NULL
    }

    int varSize;
    HyperTypes hyperType;

    public HyperTypes getHyperType() {
        return hyperType;
    }

    public int getVarSize() {
        return varSize;
    }
}