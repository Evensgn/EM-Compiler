package com.evensgn.emcompiler.ir;

public class VirtualRegister extends IRRegister {
    private String name;

    public VirtualRegister(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void accept(IRVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public VirtualRegister copy() {
        return new VirtualRegister(name);
    }
}
