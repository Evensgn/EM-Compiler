package com.evensgn.emcompiler.ir;

import java.util.Map;

public class IRReturn extends IRJumpInstruction {
    private RegValue retValue;

    public IRReturn(BasicBlock parentBB, RegValue retValue) {
        super(parentBB);
        this.retValue = retValue;
        reloadUsedRegistersRegValues();
    }

    @Override
    public void reloadUsedRegistersRegValues() {
        usedRegisters.clear();
        usedRegValues.clear();
        if (retValue != null && retValue instanceof IRRegister) usedRegisters.add((IRRegister) retValue);
        if (retValue != null) usedRegValues.add(retValue);
    }

    public void accept(IRVisitor visitor) {
        visitor.visit(this);
    }

    public RegValue getRetValue() {
        return retValue;
    }

    @Override
    public IRReturn copyRename(Map<Object, Object> renameMap) {
        return new IRReturn(
                (BasicBlock) renameMap.getOrDefault(getParentBB(), getParentBB()),
                (RegValue) renameMap.getOrDefault(retValue, retValue)
        );
    }
}
