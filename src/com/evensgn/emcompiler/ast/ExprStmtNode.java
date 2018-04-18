package com.evensgn.emcompiler.ast;

/**
 * @author Zhou Fan
 * @since 4/2/2018
 */
public class ExprStmtNode extends StmtNode {
    private ExprNode expr;

    public ExprStmtNode(ExprNode expr) {
        this.expr = expr;
    }

    public ExprNode getExpr() {
        return expr;
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