package com.evensgn.emcompiler.ast;

public class ReturnStmtNode extends JumpStmtNode {
    private ExprNode expr;
    private Location location;

    public ReturnStmtNode(ExprNode expr, Location location) {
        this.expr = expr;
        this.location = location;
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
