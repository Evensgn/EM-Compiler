package com.evensgn.emcompiler.ir;

import java.util.Map;

public class IRBranch extends IRJumpInstruction {
    private RegValue cond;
    private BasicBlock thenBB, elseBB;

    public IRBranch(BasicBlock parentBB, RegValue cond, BasicBlock thenBB, BasicBlock elseBB) {
        super(parentBB);
        this.cond = cond;
        this.thenBB = thenBB;
        this.elseBB = elseBB;
        reloadUsedRegistersRegValues();
    }

    @Override
    public void reloadUsedRegistersRegValues() {
        usedRegisters.clear();
        usedRegValues.clear();
        if (cond instanceof IRRegister) usedRegisters.add((IRRegister) cond);
        usedRegValues.add(cond);
    }

    public BasicBlock getThenBB() {
        return thenBB;
    }

    public BasicBlock getElseBB() {
        return elseBB;
    }

    public RegValue getCond() {
        return cond;
    }

    public void accept(IRVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public IRBranch copyRename(Map<Object, Object> renameMap) {
        return new IRBranch(
                (BasicBlock) renameMap.getOrDefault(getParentBB(), getParentBB()),
                (RegValue) renameMap.getOrDefault(cond, cond),
                (BasicBlock) renameMap.getOrDefault(thenBB, thenBB),
                (BasicBlock) renameMap.getOrDefault(elseBB, elseBB)
        );
    }
}
