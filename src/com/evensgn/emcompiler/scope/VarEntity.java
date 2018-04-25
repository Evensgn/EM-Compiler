package com.evensgn.emcompiler.scope;

import com.evensgn.emcompiler.ast.VarDeclNode;

public class VarEntity extends Entity {
    public VarEntity(VarDeclNode node) {
        super(node.getName(), node.getType().getType());
    }
}
