package com.evensgn.emcompiler.type;

import com.evensgn.emcompiler.Configuration;

public class StringType extends PrimitiveType {
    static private StringType instance = new StringType();

    private StringType() {
        hyperType = HyperTypes.STRING;
        varSize = Configuration.getRegSize();
    }

    public static StringType getInstance() {
        return instance;
    }
}
