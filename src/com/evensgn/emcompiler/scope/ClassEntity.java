package com.evensgn.emcompiler.scope;

import com.evensgn.emcompiler.ast.ClassDeclNode;
import com.evensgn.emcompiler.type.Type;

public class ClassEntity extends Entity {
    public ClassEntity(ClassDeclNode node) {
        super(node.getName(), Type.HyperTypes.CLASS);
    }
}
