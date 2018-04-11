package com.evensgn.emcompiler.ast;

public class ReturnStmtNode extends JumpStmtNode {
    private Location location;
    private ExprNode expr;

    public ReturnStmtNode(Location location, ExprNode expr) {
        this.location = location;
        this.expr = expr;
    }

    public ExprNode getExpr() {
        return expr;
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
