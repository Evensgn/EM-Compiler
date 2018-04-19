package com.evensgn.emcompiler.type;

public class PrimitiveType extends Type {
    private Type.PrimitiveTypes type;

    public PrimitiveType(PrimitiveTypes type) {
        this.hyperType = HyperTypes.PRIMITIVE;
        this.type = type;
    }

    public PrimitiveTypes getType() {
        return type;
    }
}
