package com.evensgn.emcompiler.type;

import com.evensgn.emcompiler.Configuration;

public class BoolType extends PrimitiveType {
    static private BoolType instance = new BoolType();

    private BoolType() {
        hyperType = HyperTypes.BOOL;
        varSize = Configuration.getRegSize();
    }

    public static BoolType getInstance() {
        return instance;
    }
}