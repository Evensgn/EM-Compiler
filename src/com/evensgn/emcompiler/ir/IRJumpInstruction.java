package com.evensgn.emcompiler.ir;

import java.util.Map;

public abstract class IRJumpInstruction extends IRInstruction {
    public IRJumpInstruction(BasicBlock parentBB) {
        super(parentBB);
    }

    public abstract void accept(IRVisitor visitor);

    @Override
    public abstract IRJumpInstruction copyRename(Map<Object, Object> renameMap);
}
