package com.evensgn.emcompiler.type;

import com.evensgn.emcompiler.Configuration;

public class IntType extends PrimitiveType {
    static private IntType instance = new IntType();

    private IntType() {
        hyperType = HyperTypes.INT;
        varSize = Configuration.getRegSize();
    }

    public static IntType getInstance() {
        return instance;
    }
}