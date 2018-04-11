package com.evensgn.emcompiler.ast;

/**
 * @author Zhou Fan
 * @since 2018/3/29
 */
abstract public class Node {
    public Node() {}
    abstract public Location location();
    abstract public void accept(ASTVisitor visitor);
}
