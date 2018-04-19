package com.evensgn.emcompiler.ast;

public class MemberAccessExprNode extends ExprNode {
    private ExprNode expr;
    private String member;
    private Location location;

    public MemberAccessExprNode(ExprNode expr, String member, Location location) {
        this.expr = expr;
        this.member = member;
        this.location = location;
    }

    public ExprNode getExpr() {
        return expr;
    }

    public String getMember() {
        return member;
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
