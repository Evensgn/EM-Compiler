package com.evensgn.emcompiler.ir;

import java.util.Map;

public class IRMove extends IRInstruction {
    private IRRegister lhs;
    private RegValue rhs;

    public IRMove(BasicBlock parentBB, IRRegister lhs, RegValue rhs) {
        super(parentBB);
        this.lhs = lhs;
        this.rhs = rhs;
        reloadUsedRegistersRegValues();
    }

    @Override
    public void reloadUsedRegistersRegValues() {
        usedRegisters.clear();
        usedRegValues.clear();
        if (rhs instanceof IRRegister) usedRegisters.add((IRRegister) rhs);
        usedRegValues.add(rhs);
    }

    @Override
    public void setUsedRegisters(Map<IRRegister, IRRegister> renameMap) {
        if (rhs instanceof IRRegister) rhs = renameMap.get(rhs);
        reloadUsedRegistersRegValues();
    }

    @Override
    public IRRegister getDefinedRegister() {
        return lhs;
    }

    @Override
    public void setDefinedRegister(IRRegister vreg) {
        lhs = vreg;
    }

    public void accept(IRVisitor visitor) {
        visitor.visit(this);
    }

    public IRRegister getLhs() {
        return lhs;
    }

    public RegValue getRhs() {
        return rhs;
    }

    @Override
    public IRMove copyRename(Map<Object, Object> renameMap) {
        return new IRMove(
                (BasicBlock) renameMap.getOrDefault(getParentBB(), getParentBB()),
                (IRRegister) renameMap.getOrDefault(lhs, lhs),
                (RegValue) renameMap.getOrDefault(rhs, rhs)
        );
    }
}
