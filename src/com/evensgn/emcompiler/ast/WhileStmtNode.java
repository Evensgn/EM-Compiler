package com.evensgn.emcompiler.ast;

public class WhileStmtNode extends LoopStmtNode {
    private ExprNode cond;
    private StmtNode stmt;
    private Location location;

    public WhileStmtNode(ExprNode cond, StmtNode stmt, Location location) {
        this.cond = cond;
        this.stmt = stmt;
        this.location = location;
    }

    public ExprNode getCond() {
        return cond;
    }

    public StmtNode getStmt() {
        return stmt;
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
