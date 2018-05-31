package com.evensgn.emcompiler.ir;

import java.util.Map;

public class IRLoad extends IRInstruction {
    private IRRegister dest;
    private int size;
    private RegValue addr;
    private int addrOffset;
    private boolean isStaticData, isLoadAddr;

    public IRLoad(BasicBlock parentBB, IRRegister dest, int size, RegValue addr, int addrOffset) {
        super(parentBB);
        this.dest = dest;
        this.size = size;
        this.addr = addr;
        this.addrOffset = addrOffset;
        this.isStaticData = false;
        reloadUsedRegistersRegValues();
    }

    public IRLoad(BasicBlock parentBB, IRRegister dest, int size, StaticData addr, boolean isLoadAddr) {
        this(parentBB, dest, size, addr, 0);
        this.isStaticData = true;
        this.isLoadAddr = isLoadAddr;
    }

    @Override
    public void reloadUsedRegistersRegValues() {
        usedRegisters.clear();
        usedRegValues.clear();
        if (addr instanceof IRRegister && !(addr instanceof StaticSlot)) usedRegisters.add((IRRegister) addr);
        usedRegValues.add(addr);
    }

    @Override
    public void setUsedRegisters(Map<IRRegister, IRRegister> renameMap) {
        if (addr instanceof IRRegister && !(addr instanceof StaticSlot)) addr = renameMap.get(addr);
        reloadUsedRegistersRegValues();
    }

    @Override
    public IRRegister getDefinedRegister() {
        return dest;
    }

    @Override
    public void setDefinedRegister(VirtualRegister vreg) {
        dest = vreg;
    }

    public void accept(IRVisitor visitor) {
        visitor.visit(this);
    }

    public IRRegister getDest() {
        return dest;
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
    public IRLoad copyRename(Map<Object, Object> renameMap) {
        if (isStaticData) {
            return new IRLoad(
                    (BasicBlock) renameMap.getOrDefault(getParentBB(), getParentBB()),
                    (IRRegister) renameMap.getOrDefault(dest, dest),
                    size,
                    (StaticData) renameMap.getOrDefault(addr, addr),
                    isLoadAddr
            );
        } else {
            return new IRLoad(
                    (BasicBlock) renameMap.getOrDefault(getParentBB(), getParentBB()),
                    (IRRegister) renameMap.getOrDefault(dest, dest),
                    size,
                    (RegValue) renameMap.getOrDefault(addr, addr),
                    addrOffset
            );
        }
    }
}