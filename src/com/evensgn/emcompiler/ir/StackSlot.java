package com.evensgn.emcompiler.ir;

public class StackSlot extends IRRegister {
    private IRFunction parentFunc;
    private String name;

    public StackSlot(IRFunction parentFunc, String name) {
        this.parentFunc = parentFunc;
        this.name = name;
    }

    public IRFunction getParentFunc() {
        return parentFunc;
    }

    public String getName() {
        return name;
    }

    @Override
    public void accept(IRVisitor visitor) {
    }

    @Override
    public RegValue copy() {
        // TO DO
        return null;
    }
}
