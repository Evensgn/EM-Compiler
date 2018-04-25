package com.evensgn.emcompiler.ast;

import com.evensgn.emcompiler.type.Type;

/**
 * @author Zhou Fan
 * @since 4/1/2018
 */
abstract public class ExprNode extends Node {
    private Type type;
    private boolean isLeftValue;

    public void setType(Type type) {
        this.type = type;
    }

    public void setLeftValue(boolean leftValue) {
        isLeftValue = leftValue;
    }

    public Type getType() {
        return type;
    }

    public boolean isLeftValue() {
        return isLeftValue;
    }
}
