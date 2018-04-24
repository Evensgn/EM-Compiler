package com.evensgn.emcompiler.ast;

/**
 * @author Zhou Fan
 * @since 2018/4/1
 */
abstract public class DeclNode extends Node {
    protected String name;

    public String getName() {
        return name;
    }
}
