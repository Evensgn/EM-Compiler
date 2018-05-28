package com.evensgn.emcompiler.ast;

/**
 * @author Zhou Fan
 * @since 4/2/2018
 */
public class ExprStmtNode extends StmtNode {
    private ExprNode expr;

    public ExprStmtNode(ExprNode expr, Location location) {
        this.expr = expr;
        this.location = location;
    }

    public ExprStmtNode(ExprNode expr) {
        this.expr = expr;
        this.location = expr.location();
    }

    public ExprNode getExpr() {
        return expr;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}