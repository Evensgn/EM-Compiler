package com.evensgn.emcompiler.ast;

public class ForStmtNode extends LoopStmtNode {
    private ExprNode init, cond, step;
    private StmtNode stmt;

    public ForStmtNode(ExprNode init, ExprNode cond, ExprNode step, StmtNode stmt, Location location) {
        this.init = init;
        this.cond = cond;
        this.step = step;
        this.stmt = stmt;
        this.location = location;
    }

    public ExprNode getInit() {
        return init;
    }

    public ExprNode getCond() {
        return cond;
    }

    public ExprNode getStep() {
        return step;
    }

    public StmtNode getStmt() {
        return stmt;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
