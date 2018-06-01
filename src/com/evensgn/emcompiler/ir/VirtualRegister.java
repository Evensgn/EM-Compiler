package com.evensgn.emcompiler.ir;

public class VirtualRegister extends IRRegister {
    private String name;
    private PhysicalRegister forcedPhysicalRegister = null;

    public VirtualRegister(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void accept(IRVisitor visitor) {
        visitor.visit(this);
    }

    public void setForcedPhysicalRegister(PhysicalRegister forcedPhysicalRegister) {
        this.forcedPhysicalRegister = forcedPhysicalRegister;
    }

    public PhysicalRegister getForcedPhysicalRegister() {
        return forcedPhysicalRegister;
    }

    @Override
    public VirtualRegister copy() {
        return new VirtualRegister(name);
    }
}
