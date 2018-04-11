package com.evensgn.emcompiler.ast;

/**
 * @author Zhou Fan
 * @since 4/6/2018
 */
public class CondStmtNode extends StmtNode {
    @Override
    public Location location() {
        return
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
