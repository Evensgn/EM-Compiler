package com.evensgn.emcompiler.ast;

import java.util.List;

public class ProgramNode extends Node {
    private List<DeclNode> decls;

    public ProgramNode(List<DeclNode> decls, Location location) {
        this.decls = decls;
        this.location = location;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
