package com.evensgn.emcompiler.ir;

import java.util.Map;

public class IRHeapAlloc extends IRInstruction {
    private IRRegister dest;
    private RegValue allocSize;

    public IRHeapAlloc(BasicBlock parentBB, IRRegister dest, RegValue allocSize) {
        super(parentBB);
        this.dest = dest;
        this.allocSize = allocSize;
        reloadUsedRegistersRegValues();
    }

    @Override
    public void reloadUsedRegistersRegValues() {
        usedRegisters.clear();
        usedRegValues.clear();
        if (allocSize instanceof IRRegister) usedRegisters.add((IRRegister) allocSize);
        usedRegValues.add(allocSize);
    }

    @Override
    public void setUsedRegisters(Map<IRRegister, IRRegister> renameMap) {
        if (allocSize instanceof IRRegister) allocSize = renameMap.get(allocSize);
        reloadUsedRegistersRegValues();
    }

    @Override
    public IRRegister getDefinedRegister() {
        return dest;
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

    @Override
    public IRHeapAlloc copyRename(Map<Object, Object> renameMap) {
        return new IRHeapAlloc(
                (BasicBlock) renameMap.getOrDefault(getParentBB(), getParentBB()),
                (IRRegister) renameMap.getOrDefault(dest, dest),
                (RegValue) renameMap.getOrDefault(allocSize, allocSize)
        );
    }
}
