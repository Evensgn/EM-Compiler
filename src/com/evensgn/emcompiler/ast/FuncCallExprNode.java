package com.evensgn.emcompiler.ast;

import java.util.List;

public class FuncCallExprNode extends ExprNode {
    private ExprNode func;
    private List<ExprNode> args;

    public FuncCallExprNode(ExprNode func, List<ExprNode> args) {
        this.func = func;
        this.args = args;
    }

    public ExprNode getFunc() {
        return func;
    }

    public List<ExprNode> getArgs() {
        return args;
    }

    @Override
    public Location location() {
        return func.location();
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
