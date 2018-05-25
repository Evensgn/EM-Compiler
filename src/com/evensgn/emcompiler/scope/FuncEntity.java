package com.evensgn.emcompiler.scope;

import com.evensgn.emcompiler.ast.FuncDeclNode;
import com.evensgn.emcompiler.ast.VarDeclNode;
import com.evensgn.emcompiler.type.ClassType;
import com.evensgn.emcompiler.type.FunctionType;
import com.evensgn.emcompiler.type.NullType;
import com.evensgn.emcompiler.type.Type;

import java.util.ArrayList;
import java.util.List;

public class FuncEntity extends Entity {
    private List<VarEntity> parameters;
    private Type returnType;
    private String className;
    private boolean isConstruct, isMember, isBuiltIn = false;

    public FuncEntity(String name, Type type) {
        super(name, type);
    }

    public FuncEntity(FuncDeclNode node) {
        super(node.getName(), new FunctionType(node.getName()));
        parameters = new ArrayList<>();
        for (VarDeclNode paraDecl : node.getParameterList()) {
            parameters.add(new VarEntity(paraDecl));
        }
        if (node.getReturnType() == null) returnType = null;
        else returnType = node.getReturnType().getType();
        isConstruct = node.isConstruct();
        isMember = false;
        className = null;
    }

    FuncEntity(FuncDeclNode node, String className) {
        super(node.getName(), new FunctionType(node.getName()));
        parameters = new ArrayList<>();
        parameters.add(new VarEntity(Scope.THIS_PARA_NAME, new ClassType(className)));
        isMember = true;
        this.className = className;
        for (VarDeclNode paraDecl : node.getParameterList()) {
            parameters.add(new VarEntity(paraDecl));
        }
        if (node.getReturnType() == null) returnType = null;
        else returnType = node.getReturnType().getType();
        isConstruct = node.isConstruct();
    }

    public void setParameters(List<VarEntity> parameters) {
        this.parameters = parameters;
    }

    public void setReturnType(Type returnType) {
        this.returnType = returnType;
    }

    public List<VarEntity> getParameters() {
        return parameters;
    }

    public Type getReturnType() {
        return returnType;
    }

    public boolean isConstruct() {
        return isConstruct;
    }

    public boolean isMember() {
        return isMember;
    }

    public boolean isBuiltIn() {
        return isBuiltIn;
    }

    public void setBuiltIn(boolean builtIn) {
        isBuiltIn = builtIn;
    }

    public String getClassName() {
        return className;
    }
}
