package com.evensgn.emcompiler.ast;

abstract public class Node {
    public Node() {}
    abstract public Location location();
    abstract public void accpet(EASTVisitor visitor);
}
