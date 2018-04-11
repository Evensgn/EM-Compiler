package com.evensgn.emcompiler.ast;

public class MemberAccessExprNode extends ExprNode {
    private ExprNode expr;
    private String member;

    public MemberAccessExprNode(ExprNode expr, String member) {
        this.expr = expr;
        this.member = member;
    }

    public ExprNode getExpr() {
        return expr;
    }

    public String getMember() {
        return member;
    }

    @Override
    public Location location() {
        return expr.location();
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
