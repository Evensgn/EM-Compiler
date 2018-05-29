package com.evensgn.emcompiler.ast;

import com.evensgn.emcompiler.scope.FuncEntity;

import java.util.List;

public class FuncCallExprNode extends ExprNode {
    private ExprNode func;
    private List<ExprNode> args;
    private FuncEntity funcEntity;

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

    public FuncEntity getFuncEntity() {
        return funcEntity;
    }

    public void setFuncEntity(FuncEntity funcEntity) {
        this.funcEntity = funcEntity;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
