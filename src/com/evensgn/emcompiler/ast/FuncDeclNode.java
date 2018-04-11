package com.evensgn.emcompiler.ast;

import java.util.List;

/**
 * @author Zhou Fan
 * @since 2018/4/1
 */
public class FuncDeclNode extends DeclNode {
    private TypeNode returnType;
    private String name;
    private List<VarDeclNode> parameterList;
    private BlockStmtNode body;

    public FuncDeclNode(TypeNode returnType, String name, List<VarDeclNode> parameterList, BlockStmtNode body) {
        this.returnType = returnType;
        this.name = name;
        this.parameterList = parameterList;
        this.body = body;
    }

    public TypeNode getReturnType() {
        return returnType;
    }

    public String getName() {
        return name;
    }

    public List<VarDeclNode> getParameterList() {
        return parameterList;
    }

    public BlockStmtNode getBody() {
        return body;
    }

    @Override
    public Location location() {
        return returnType.location();
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
