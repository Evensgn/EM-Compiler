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
        reloadUsedRegistersRegValues();
    }

    public IRStore(BasicBlock parentBB, RegValue value, int size, StaticData addr) {
        this(parentBB, value, size, addr, 0);
        this.isStaticData = true;
    }

    @Override
    public IRRegister getDefinedRegister() {
        return null;
    }

    @Override
    public void setDefinedRegister(IRRegister vreg) {
        // no actions
    }

    @Override
    public void reloadUsedRegistersRegValues() {
        usedRegisters.clear();
        usedRegValues.clear();
        if (addr instanceof IRRegister && !(addr instanceof StackSlot)) usedRegisters.add((IRRegister) addr);
        if (value instanceof IRRegister) usedRegisters.add((IRRegister) value);
        usedRegValues.add(addr);
        usedRegValues.add(value);
    }

    @Override
    public void setUsedRegisters(Map<IRRegister, IRRegister> renameMap) {
        if (addr instanceof IRRegister && !(addr instanceof StackSlot)) addr = renameMap.get(addr);
        if (value instanceof IRRegister) value = renameMap.get(value);
        reloadUsedRegistersRegValues();
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

    public boolean isStaticData() {
        return isStaticData;
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

    public void setAddr(RegValue addr) {
        this.addr = addr;
    }

    public void setAddrOffset(int addrOffset) {
        this.addrOffset = addrOffset;
    }
}