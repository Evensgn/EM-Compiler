package com.evensgn.emcompiler.ir;

import java.util.Map;

public class IRPush extends IRInstruction {
    private RegValue value;

    public IRPush(BasicBlock parentBB, RegValue value) {
        super(parentBB);
        this.value = value;
    }

    @Override
    public void accept(IRVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public IRInstruction copyRename(Map<Object, Object> renameMap) {
        return null;
    }

    @Override
    public void reloadUsedRegistersRegValues() {
        // no actions
    }

    @Override
    public IRRegister getDefinedRegister() {
        return null;
    }

    @Override
    public void setUsedRegisters(Map<IRRegister, IRRegister> renameMap) {
        // no actions
    }

    @Override
    public void setDefinedRegister(IRRegister vreg) {
        // no actions
    }
}
