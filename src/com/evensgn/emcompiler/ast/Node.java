package com.evensgn.emcompiler.ast;

/**
 * @author Zhou Fan
 * @since 2018/3/29
 */
abstract public class Node {
    protected Location location;
    protected boolean outInfluence = false;

    public Node() {}

    public Location location() {
        return location;
    }

    public boolean isOutInfluence() {
        return outInfluence;
    }

    public void setOutInfluence(boolean outInfluence) {
        this.outInfluence = outInfluence;
    }

    abstract public void accept(ASTVisitor visitor);
}