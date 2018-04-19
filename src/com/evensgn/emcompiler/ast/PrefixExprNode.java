package com.evensgn.emcompiler.ast;

public class PrefixExprNode extends ExprNode {
    public enum PrefixOps {
        PREFIX_INC, PREFIX_DEC, POS, NEG, LOGIC_NOT, BITWISE_NOT
    }

    private PrefixOps op;
    private ExprNode expr;

    public PrefixExprNode(PrefixOps op, ExprNode expr, Location location) {
        this.op = op;
        this.expr = expr;
        this.location = location;
    }

    public PrefixOps getOp() {
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
