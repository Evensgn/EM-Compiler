package com.evensgn.emcompiler.ir;

public class IRHeapAlloc extends IRInstruction {
    private IRRegister dest;
    private RegValue allocSize;

    public IRHeapAlloc(BasicBlock parentBB, IRRegister dest, RegValue allocSize) {
        super(parentBB);
        this.dest = dest;
        this.allocSize = allocSize;
    }

    public void accept(IRVisitor visitor) {
        visitor.visit(this);
    }

    public IRRegister getDest() {
        return dest;
    }

    public RegValue getAllocSize() {
        return allocSize;
    }
}
