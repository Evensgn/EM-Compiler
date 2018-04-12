package com.evensgn.emcompiler.ast;

import java.util.List;

public class NewExprNode extends ExprNode {
    private TypeNode newType;
    private List<ExprNode> dims;

    public NewExprNode(TypeNode newType, List<ExprNode> dims) {
        this.newType = newType;
        this.dims = dims;
    }

    public TypeNode getNewType() {
        return newType;
    }

    public List<ExprNode> getDims() {
        return dims;
    }

    @Override
    public Location location() {
        return newType.location();
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
