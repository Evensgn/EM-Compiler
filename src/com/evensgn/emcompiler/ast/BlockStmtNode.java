package com.evensgn.emcompiler.ast;

import java.util.List;

/**
 * @author Zhou Fan
 * @since 4/2/2018
 */
public class BlockStmtNode extends StmtNode {
    private List<Node> stmtsAndVarDecls;
    private List<StmtNode> stmts;
    private Location location;

    public BlockStmtNode(List<Node> stmtsAndVarDecls, Location location) {
        this.stmtsAndVarDecls = stmtsAndVarDecls;
        this.location = location;
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
