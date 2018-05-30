package com.evensgn.emcompiler.ir;

import java.util.Map;

public class IRStore extends IRInstruction {
    private RegValue value;
    private int size;
    private RegValue addr;
    private int addrOffset;
    private boolean isStaticData;

    public IRStore(BasicBlock parentBB, RegValue value, int size, RegValue addr, int addrOffset) {
        super(parentBB);
        this.value = value;
        this.size = size;
        this.addr = addr;
        this.addrOffset = addrOffset;
        this.isStaticData = false;
    }

    public IRStore(BasicBlock parentBB, RegValue value, int size, StaticData addr) {
        this(parentBB, value, size, addr, 0);
        this.isStaticData = true;
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

    @Override
    public IRStore copyRename(Map<Object, Object> renameMap) {
        if (isStaticData) {
            return new IRStore(
                    (BasicBlock) renameMap.getOrDefault(getParentBB(), getParentBB()),
                    (RegValue) renameMap.getOrDefault(value, value),
                    size,
                    (StaticData) renameMap.getOrDefault(addr, addr)
            );
        } else {
            return new IRStore(
                    (BasicBlock) renameMap.getOrDefault(getParentBB(), getParentBB()),
                    (RegValue) renameMap.getOrDefault(value, value),
                    size,
                    (RegValue) renameMap.getOrDefault(addr, addr),
                    addrOffset
            );
        }
    }
}