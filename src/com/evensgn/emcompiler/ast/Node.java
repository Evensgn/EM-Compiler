package com.evensgn.emcompiler.ast;

/**
 * @author Zhou Fan
 * @since 2018/3/29
 */
abstract public class Node {
    protected Location location;

    public Node() {}

    public Location location() {
        return location;
    }

    abstract public void accept(ASTVisitor visitor);
}