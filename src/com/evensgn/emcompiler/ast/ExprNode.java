package com.evensgn.emcompiler.ast;

import com.evensgn.emcompiler.type.Type;

/**
 * @author Zhou Fan
 * @since 4/1/2018
 */
abstract public class ExprNode extends Node {
    private Type type;
    private boolean isLeftValue;

    public Type getType() {
        return type;
    }

    public boolean isLeftValue() {
        return isLeftValue;
    }
}
