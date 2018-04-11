package com.evensgn.emcompiler.ast;

/**
 * @author Zhou Fan
 * @since 4/6/2018
 */
public class CondStmtNode extends StmtNode {
    private ExprNode cond;
    private StmtNode thenStmt, elseStmt;

    public CondStmtNode(ExprNode cond, StmtNode thenStmt, StmtNode elseStmt) {
        this.cond = cond;
        this.thenStmt = thenStmt;
        this.elseStmt = elseStmt;
    }

    public ExprNode getcond() {
        return cond;
    }

    public StmtNode getThenStmt() {
        return thenStmt;
    }

    public StmtNode getElseStmt() {
        return elseStmt;
    }

    @Override
    public Location location() {
        return cond.location();
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
