package com.evensgn.emcompiler.ast;

import java.util.List;

/**
 * @author Zhou Fan
 * @since 4/6/2018
 */
public class ClassDeclNode extends DeclNode {
    private String name;
    private List<VarDeclNode> varMember;
    private List<FuncDeclNode> funcMember;
    private Location location;

    public ClassDeclNode(String name, List<VarDeclNode> varMember, List<FuncDeclNode> funcMember, Location location) {
        this.name = name;
        this.varMember = varMember;
        this.funcMember = funcMember;
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public List<VarDeclNode> getVarMember() {
        return varMember;
    }

    public List<FuncDeclNode> getFuncMember() {
        return funcMember;
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
