package com.evensgn.emcompiler.ast;

import java.util.List;

/**
 * @author Zhou Fan
 * @since 4/2/2018
 */
public class BlockStmtNode extends StmtNode {
    private List<StmtNode> stmtList;

    @Override
    public Location location() {
        return stmtList.get(0).location();
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
