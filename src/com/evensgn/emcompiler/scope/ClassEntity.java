package com.evensgn.emcompiler.scope;

import com.evensgn.emcompiler.ast.ClassDeclNode;
import com.evensgn.emcompiler.ast.FuncDeclNode;
import com.evensgn.emcompiler.type.ClassType;
import com.evensgn.emcompiler.type.Type;

public class ClassEntity extends Entity {
    private Scope scope;

    public ClassEntity(String name, Type type, Scope parentScope) {
        super(name, type);
        scope = new Scope(parentScope, true);
    }

    public ClassEntity(ClassDeclNode node, Scope parentScope) {
        super(node.getName(), new ClassType(node.getName()));
        String key;
        FuncEntity entity;
        scope = new Scope(parentScope, true);
        for (FuncDeclNode funcMemDecl : node.getFuncMember()) {
            key = Scope.funcKey(funcMemDecl.getName());
            entity = new FuncEntity(funcMemDecl, node.getName());
            scope.putCheck(funcMemDecl.location(), funcMemDecl.getName(), key, entity);
        }
    }

    public Scope getScope() {
        return scope;
    }
}
