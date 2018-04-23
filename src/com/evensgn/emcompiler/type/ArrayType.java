package com.evensgn.emcompiler.type;

public class ArrayType extends Type {
    private Type baseType;
    private int numDim;

    public ArrayType(Type baseType) {
        this.hyperType = HyperTypes.ARRAY;
        if (baseType.hyperType == HyperTypes.ARRAY) {
            this.baseType = ((ArrayType) baseType).baseType;
            this.numDim = ((ArrayType) baseType).numDim + 1;
        }
        else {
            this.baseType = baseType;
            this.numDim = 1;
        }
    }

    public Type getBaseType() {
        return baseType;
    }

    public int getNumDim() {
        return numDim;
    }

    @Override
    public String toString() {
        return String.format("ArrayType(baseType: %s, numDim: %d)", baseType.toString(), numDim);
    }
}
