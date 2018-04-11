package com.evensgn.emcompiler.ast;

public class BlankStmtNode extends StmtNode {
    @Override
    public Location location() {
        return null;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
