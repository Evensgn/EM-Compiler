package com.evensgn.emcompiler.scope;

import com.evensgn.emcompiler.ast.VarDeclNode;
import com.evensgn.emcompiler.type.Type;

public class VarEntity extends Entity {
    public VarEntity(String name, Type type) {
        super(name, type);
    }

    public VarEntity(VarDeclNode node) {
        super(node.getName(), node.getType().getType());
    }
}
