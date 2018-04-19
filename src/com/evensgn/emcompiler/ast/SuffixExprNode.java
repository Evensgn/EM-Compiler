package com.evensgn.emcompiler.ast;

public class SuffixExprNode extends ExprNode {
    public enum SuffixOps {
        SUFFIX_INC, SUFFIX_DEC
    }

    private SuffixOps op;
    private ExprNode expr;

    public SuffixExprNode(SuffixOps op, ExprNode expr, Location location) {
        this.op = op;
        this.expr = expr;
        this.location = location;
    }

    public SuffixOps getOp() {
        return op;
    }

    public ExprNode getExpr() {
        return expr;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}