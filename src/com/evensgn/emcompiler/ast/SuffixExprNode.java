package com.evensgn.emcompiler.ast;

public class SuffixExprNode extends ExprNode {
    public enum SuffixOps {
        SUFFIX_ADD, SUFFIX_DEC
    }

    private Location location;
    private SuffixOps op;
    private ExprNode expr;

    public SuffixExprNode(Location location, SuffixOps op, ExprNode expr) {
        this.location = location;
        this.op = op;
        this.expr = expr;
    }

    public SuffixOps getOp() {
        return op;
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