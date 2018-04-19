package com.evensgn.emcompiler.ast;

import java.util.List;

public class NewExprNode extends ExprNode {
    private TypeNode newType;
    private List<ExprNode> dims;
    private int numDim;

    public NewExprNode(TypeNode newType, List<ExprNode> dims, int numDim, Location location) {
        this.newType = newType;
        this.dims = dims;
        this.numDim = numDim;
        this.location = location;
    }

    public TypeNode getNewType() {
        return newType;
    }

    public List<ExprNode> getDims() {
        return dims;
    }

    public int getNumDim() {
        return numDim;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}