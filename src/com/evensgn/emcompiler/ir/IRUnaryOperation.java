package com.evensgn.emcompiler.ir;

import java.util.Map;

public class IRUnaryOperation extends IRInstruction {
    public enum IRUnaryOp {
        BITWISE_NOT, NEG
    }
    private IRRegister dest;
    private IRUnaryOp op;
    private RegValue rhs;

    public IRUnaryOperation(BasicBlock parentBB, IRRegister dest, IRUnaryOp op, RegValue rhs) {
        super(parentBB);
        this.dest = dest;
        this.op = op;
        this.rhs = rhs;
        reloadUsedRegistersRegValues();
    }

    @Override
    public void reloadUsedRegistersRegValues() {
        usedRegisters.clear();
        usedRegValues.clear();
        if (rhs instanceof IRRegister) usedRegisters.add((IRRegister) rhs);
        usedRegValues.add(rhs);
    }

    public void accept(IRVisitor visitor) {
        visitor.visit(this);
    }

    public IRUnaryOp getOp() {
        return op;
    }

    public IRRegister getDest() {
        return dest;
    }

    public RegValue getRhs() {
        return rhs;
    }

    @Override
    public IRUnaryOperation copyRename(Map<Object, Object> renameMap) {
        return new IRUnaryOperation(
                (BasicBlock) renameMap.getOrDefault(getParentBB(), getParentBB()),
                (IRRegister) renameMap.getOrDefault(dest, dest),
                op,
                (RegValue) renameMap.getOrDefault(rhs, rhs)
        );
    }
}
