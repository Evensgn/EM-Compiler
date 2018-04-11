package com.evensgn.emcompiler.type;

abstract public class Type {
    public enum Types {
        VOID, INT, BOOL, STRING, CLASS, ARRAY, FUNCTION, NULL
    }

    private Types type;
}
