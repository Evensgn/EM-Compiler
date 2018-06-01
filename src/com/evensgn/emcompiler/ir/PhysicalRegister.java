package com.evensgn.emcompiler.ir;

public abstract class PhysicalRegister extends IRRegister {
    @Override
    public void accept(IRVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public RegValue copy() {
        return null;
    }

    public abstract String getName();
    public abstract boolean isGeneral();
    public abstract boolean isCallerSave();
    public abstract boolean isCalleeSave();
}
