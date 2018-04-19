package com.evensgn.emcompiler.ast;

public class ContinueStmtNode extends JumpStmtNode {
    public ContinueStmtNode(Location location) {
        this.location = location;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
