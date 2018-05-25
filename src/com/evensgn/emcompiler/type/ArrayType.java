package com.evensgn.emcompiler.type;

import com.evensgn.emcompiler.Configuration;

public class ArrayType extends Type {
    private Type baseType;

    public ArrayType(Type baseType) {
        hyperType = HyperTypes.ARRAY;
        this.baseType = baseType;
        varSize = Configuration.getRegSize();
    }

    public Type getBaseType() {
        return baseType;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof ArrayType)) return false;
        return baseType.equals(((ArrayType) obj).baseType);
    }

    @Override
    public String toString() {
        return String.format("ArrayType(%s)", baseType.toString());
    }
}