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

    @Override
    public String toString() {
        return String.format("ArrayType(%s)", baseType.toString());
    }
}
