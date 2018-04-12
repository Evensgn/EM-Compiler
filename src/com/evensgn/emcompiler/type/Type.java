package com.evensgn.emcompiler.type;

abstract public class Type {
    public enum Types {
        VOID, INT, BOOL, STRING, CLASS, ARRAY, FUNCTION
    }

    public enum PrimitiveTypes {
        VOID, INT, BOOL, STRING
    }

    private Types type;
}
