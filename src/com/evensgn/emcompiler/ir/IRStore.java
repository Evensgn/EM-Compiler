package com.evensgn.emcompiler.ir;

public class IRStore extends IRInstruction {
    private RegValue value;
    private int size;
    private RegValue addr;
    private int addrOffset;

    public IRStore(BasicBlock parentBB, RegValue value, int size, RegValue addr, int addrOffset) {
        super(parentBB);
        this.value = value;
        this.size = size;
        this.addr = addr;
        this.addrOffset = addrOffset;
    }

    public void accept(IRVisitor visitor) {
        visitor.visit(this);
    }

    public RegValue getValue() {
        return value;
    }

    public int getSize() {
        return size;
    }

    public RegValue getAddr() {
        return addr;
    }

    public int getAddrOffset() {
        return addrOffset;
    }
}