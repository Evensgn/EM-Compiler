package com.evensgn.emcompiler.ast;

public class ForStmtNode extends LoopStmtNode {
    private ExprNode init, cond, step;
    private StmtNode stmt;

    public ForStmtNode(ExprNode init, ExprNode cond, ExprNode step, StmtNode stmt) {
        this.init = init;
        this.cond = cond;
        this.step = step;
        this.stmt = stmt;
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
    public Location location() {
        return init.location();
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
