package com.evensgn.emcompiler.ir;

public class IRLoad extends IRInstruction {
    private VirtualRegister dest;
    private int size;
    private RegValue addr;
    private int addrOffset;

    public IRLoad(BasicBlock parentBB, VirtualRegister dest, int size, RegValue addr, int addrOffset) {
        super(parentBB);
        this.dest = dest;
        this.size = size;
        this.addr = addr;
        this.addrOffset = addrOffset;
    }
}