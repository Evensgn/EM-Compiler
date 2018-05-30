package com.evensgn.emcompiler.ir;

import java.util.Map;

public class IRJump extends IRJumpInstruction {
    private BasicBlock targetBB;

    public IRJump(BasicBlock parentBB, BasicBlock targetBB) {
        super(parentBB);
        this.targetBB = targetBB;
    }

    public BasicBlock getTargetBB() {
        return targetBB;
    }

    public void accept(IRVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public IRJump copyRename(Map<Object, Object> renameMap) {
        return new IRJump(
                (BasicBlock) renameMap.getOrDefault(getParentBB(), getParentBB()),
                (BasicBlock) renameMap.getOrDefault(targetBB, targetBB)
        );
    }
}
