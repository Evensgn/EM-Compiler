package com.evensgn.emcompiler.ast;

public class PrefixExprNode extends ExprNode {
    public enum PrefixOps {
        PREFIX_ADD, PREFIX_DEC, POS, NEG, LOGIC_NOT, BITWISE_NOT
    }

    private Location location;
    private PrefixOps op;
    private ExprNode expr;

    public PrefixExprNode(Location location, PrefixOps op, ExprNode expr) {
        this.location = location;
        this.op = op;
        this.expr = expr;
    }

    public PrefixOps getOp() {
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
