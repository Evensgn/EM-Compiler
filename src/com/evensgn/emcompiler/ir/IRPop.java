package com.evensgn.emcompiler.ir;

import java.util.Map;

public class IRPop extends IRInstruction {
    private PhysicalRegister preg;

    public IRPop(BasicBlock parentBB, PhysicalRegister preg) {
        super(parentBB);
        this.preg = preg;
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
