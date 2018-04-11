package com.evensgn.emcompiler.ast;

public class WhileStmtNode extends LoopStmtNode {
    private ExprNode cond;
    private StmtNode stmt;

    public WhileStmtNode(ExprNode cond, StmtNode stmt) {
        this.cond = cond;
        this.stmt = stmt;
    }

    public ExprNode getCond() {
        return cond;
    }

    public StmtNode getStmt() {
        return stmt;
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
