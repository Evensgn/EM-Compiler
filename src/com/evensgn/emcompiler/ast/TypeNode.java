package com.evensgn.emcompiler.ast;

import com.evensgn.emcompiler.type.Type;

/**
 * @author Zhou Fan
 * @since 2018/4/1
 */
abstract public class TypeNode extends Node {
    private Type.Types type;

    public Type.Types getType() {
        return type;
    }
}
