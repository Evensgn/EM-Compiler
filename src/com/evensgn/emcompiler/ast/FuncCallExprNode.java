package com.evensgn.emcompiler.ast;

import java.util.List;

public class FuncCallExprNode extends ExprNode {
    private ExprNode func;
    private List<ExprNode> args;
    private Location location;

    public FuncCallExprNode(ExprNode func, List<ExprNode> args, Location location) {
        this.func = func;
        this.args = args;
        this.location = location;
    }

    public ExprNode getFunc() {
        return func;
    }

    public List<ExprNode> getArgs() {
        return args;
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
