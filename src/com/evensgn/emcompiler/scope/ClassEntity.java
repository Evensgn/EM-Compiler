package com.evensgn.emcompiler.scope;

import com.evensgn.emcompiler.ast.ClassDeclNode;
import com.evensgn.emcompiler.ast.FuncDeclNode;
import com.evensgn.emcompiler.ast.VarDeclNode;
import com.evensgn.emcompiler.type.ClassType;

import java.util.ArrayList;
import java.util.List;

public class ClassEntity extends Entity {
    private List<VarEntity> varMembers;
    private List<FuncEntity> funcMembers;

    public ClassEntity(ClassDeclNode node) {
        super(node.getName(), new ClassType(node.getName()));
        varMembers = new ArrayList<>();
        for (VarDeclNode varMemDecl : node.getVarMember()) {
            varMembers.add(new VarEntity(varMemDecl));
        }
        funcMembers = new ArrayList<>();
        for (FuncDeclNode funcMemDecl : node.getFuncMember()) {
            funcMembers.add(new FuncEntity(funcMemDecl));
        }
    }

    public List<VarEntity> getVarMembers() {
        return varMembers;
    }

    public List<FuncEntity> getFuncMembers() {
        return funcMembers;
    }
}
