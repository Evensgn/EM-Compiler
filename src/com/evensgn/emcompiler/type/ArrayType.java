package com.evensgn.emcompiler.type;

public class ArrayType extends Type {
    private Type baseType;

    public ArrayType(Type baseType) {
        this.hyperType = HyperTypes.ARRAY;
        this.baseType = baseType;
    }

    public Type getBaseType() {
        return baseType;
    }
}
