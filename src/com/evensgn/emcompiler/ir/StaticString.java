package com.evensgn.emcompiler.ir;

import com.evensgn.emcompiler.Configuration;

public class StaticString extends StaticData {
    private String value;
    public StaticString(String value) {
        super("static_str", Configuration.getRegSize());
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void accept(IRVisitor visitor) {
        visitor.visit(this);
    }

}
