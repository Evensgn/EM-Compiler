package com.evensgn.emcompiler.ir;

public class IRStore extends IRJumpInstruction {
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
}
