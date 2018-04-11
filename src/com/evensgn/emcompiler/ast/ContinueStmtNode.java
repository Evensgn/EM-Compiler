package com.evensgn.emcompiler.ast;

public class ContinueStmtNode extends JumpStmtNode {
    private Location location;

    public ContinueStmtNode(Location location) {
        this.location = location;
    }

    @Override
    public Location location() {
        return location;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
