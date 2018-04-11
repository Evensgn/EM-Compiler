package com.evensgn.emcompiler.ast;

public class BreakStmtNode extends JumpStmtNode {
    private Location location;

    public BreakStmtNode(Location location) {
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
