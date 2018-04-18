package com.evensgn.emcompiler.ast;

import java.util.List;

public class VarDeclListNode extends Node {
    private List<VarDeclNode> decls;

    public VarDeclListNode(List<VarDeclNode> decls) {
        this.decls = decls;
    }

    public List<VarDeclNode> getDecls() {
        return decls;
    }

    @Override
    public Location location() {
        return null;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
