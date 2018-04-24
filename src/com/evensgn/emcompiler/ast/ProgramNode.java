package com.evensgn.emcompiler.ast;

import com.evensgn.emcompiler.scope.Scope;

import java.util.List;

public class ProgramNode extends Node {
    private List<DeclNode> decls;
    private Scope scope;

    public ProgramNode(List<DeclNode> decls, Location location) {
        this.decls = decls;
        this.location = location;
    }

    public List<DeclNode> getDecls() {
        return decls;
    }

    public Scope getScope() {
        return scope;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
