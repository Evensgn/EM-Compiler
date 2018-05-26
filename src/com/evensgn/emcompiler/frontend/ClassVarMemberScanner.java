package com.evensgn.emcompiler.frontend;

import com.evensgn.emcompiler.ast.*;
import com.evensgn.emcompiler.scope.ClassEntity;
import com.evensgn.emcompiler.scope.Scope;
import com.evensgn.emcompiler.scope.VarEntity;
import com.evensgn.emcompiler.type.ClassType;

public class ClassVarMemberScanner extends BaseScopeScanner {
    private Scope globalScope, currentClassScope;
    private int currentOffset = 0;

    public ClassVarMemberScanner(Scope globalScope) {
        this.globalScope = globalScope;
    }

    public Scope getGlobalScope() {
        return globalScope;
    }

    @Override
    public void visit(ProgramNode node) {
        for (DeclNode decl : node.getDecls()) {
            if (!(decl instanceof ClassDeclNode)) continue;
            decl.accept(this);
        }
    }

    @Override
    public void visit(ClassDeclNode node) {
        ClassEntity entity = (ClassEntity) globalScope.getCheck(node.location(), node.getName(), Scope.classKey(node.getName()));
        currentClassScope = entity.getScope();
        currentOffset = 0;
        for (VarDeclNode varMemDecl : node.getVarMember()) {
            varMemDecl.accept(this);
        }
    }

    @Override
    public void visit(VarDeclNode node) {
        if (node.getType().getType() instanceof ClassType) {
            String className = ((ClassType) node.getType().getType()).getName();
            currentClassScope.assertContainsExactKey(node.location(), className, Scope.classKey(className));
        }
        checkVarDeclInit(node);
        VarEntity entity = new VarEntity(node.getName(), node.getType().getType());
        entity.setAddrOffset(currentOffset);
        currentOffset += node.getType().getType().getVarSize();
        currentClassScope.putCheck(node.location(), node.getName(), Scope.varKey(node.getName()), entity);
    }
}
