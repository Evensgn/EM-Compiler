package com.evensgn.emcompiler.ast;

import java.util.List;

/**
 * @author Zhou Fan
 * @since 2018/4/1
 */
public class FuncDeclNode extends DeclNode {
    private boolean isConstruct;
    private TypeNode returnType;
    private String name;
    private List<VarDeclNode> parameterList;
    private BlockStmtNode body;

    public FuncDeclNode(TypeNode returnType, String name, List<VarDeclNode> parameterList, BlockStmtNode body, Location location) {
        if (returnType == null) {
            this.isConstruct = true;
            this.returnType = null;
        }
        else {
            this.isConstruct = false;
            this.returnType = returnType;
        }
        this.name = name;
        this.parameterList = parameterList;
        this.body = body;
        this.location = location;
    }

    public boolean isConstruct() {
        return isConstruct;
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
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
