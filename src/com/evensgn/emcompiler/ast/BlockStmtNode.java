package com.evensgn.emcompiler.ast;

import com.evensgn.emcompiler.scope.Entity;

import java.util.List;

/**
 * @author Zhou Fan
 * @since 4/2/2018
 */
public class BlockStmtNode extends StmtNode {
    private List<Node> stmtsAndVarDecls;
    private List<StmtNode> stmts;
    private Entity scope;

    public BlockStmtNode(List<Node> stmtsAndVarDecls, Location location) {
        this.stmtsAndVarDecls = stmtsAndVarDecls;
        this.location = location;
    }

    public List<Node> getStmtsAndVarDecls() {
        return stmtsAndVarDecls;
    }

    public List<StmtNode> getStmts() {
        return stmts;
    }

    public Entity getScope() {
        return scope;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
