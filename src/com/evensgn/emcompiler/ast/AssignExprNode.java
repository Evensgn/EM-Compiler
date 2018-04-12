package com.evensgn.emcompiler.ast;

public class AssignExprNode extends ExprNode {
    private ExprNode lhs, rhs;

    public AssignExprNode(ExprNode lhs, ExprNode rhs) {
        this.lhs = lhs;
        this.rhs = rhs;
    }

    public ExprNode getLhs() {
        return lhs;
    }

    public ExprNode getRhs() {
        return rhs;
    }

    @Override
    public Location location() {
        return lhs.location();
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
