package com.evensgn.emcompiler.scope;

import com.evensgn.emcompiler.ast.FuncDeclNode;
import com.evensgn.emcompiler.ast.VarDeclNode;
import com.evensgn.emcompiler.type.FunctionType;
import com.evensgn.emcompiler.type.Type;

import java.util.ArrayList;
import java.util.List;

public class FuncEntity extends Entity {
    private List<VarEntity> parameters;
    private Type returnType;

    public FuncEntity(FuncDeclNode node) {
        super(node.getName(), new FunctionType(node.getName()));
        parameters = new ArrayList<>();
        for (VarDeclNode paraDecl : node.getParameterList()) {
            parameters.add(new VarEntity(paraDecl));
        }
        returnType = node.getReturnType().getType();
    }

    public List<VarEntity> getParameters() {
        return parameters;
    }

    public Type getReturnType() {
        return returnType;
    }
}
