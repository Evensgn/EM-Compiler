package com.evensgn.emcompiler.scope;

import com.evensgn.emcompiler.ast.FuncDeclNode;
import com.evensgn.emcompiler.type.Type;

public class FuncEntity extends Entity {
    public FuncEntity(FuncDeclNode node) {
        super(node.getName(), Type.HyperTypes.FUNCTION);
    }
}
