package com.evensgn.emcompiler.ast;

public class AssignExprNode extends ExprNode {
    private ExprNode lhs, rhs;
    private Location location;

    public AssignExprNode(ExprNode lhs, ExprNode rhs, Location location) {
        this.lhs = lhs;
        this.rhs = rhs;
        this.location = location;
    }

    public ExprNode getLhs() {
        return lhs;
    }

    public ExprNode getRhs() {
        return rhs;
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
