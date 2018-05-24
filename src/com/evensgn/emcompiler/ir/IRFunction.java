package com.evensgn.emcompiler.ir;

import com.evensgn.emcompiler.scope.FuncEntity;
import com.evensgn.emcompiler.scope.VarEntity;

import java.util.List;

public class IRFunction {
    private FuncEntity funcEntity;
    private BasicBlock headBB = null, tailBB = null;

    public String getName() {
        return funcEntity.getName();
    }

    public List<VarEntity> getParameters() {
        return funcEntity.getParameters();
    }

    public IRFunction(FuncEntity funcEntity) {
        this.funcEntity = funcEntity;
    }
}