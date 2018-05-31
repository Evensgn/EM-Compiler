package com.evensgn.emcompiler.ir;

import java.util.Map;

public class IRComparison extends IRInstruction {
    public enum IRCmpOp {
        GREATER, LESS, GREATER_EQUAL, LESS_EQUAL, EQUAL, INEQUAL
    }

    private IRRegister dest;
    private IRCmpOp op;
    private RegValue lhs, rhs;

    public IRComparison(BasicBlock parentBB, IRRegister dest, IRCmpOp op, RegValue lhs, RegValue rhs) {
        super(parentBB);
        this.dest = dest;
        this.op = op;
        this.lhs = lhs;
        this.rhs = rhs;
        reloadUsedRegistersRegValues();
    }

    @Override
    public void reloadUsedRegistersRegValues() {
        usedRegisters.clear();
        usedRegValues.clear();
        if (lhs instanceof IRRegister) usedRegisters.add((IRRegister) lhs);
        if (rhs instanceof IRRegister) usedRegisters.add((IRRegister) rhs);
        usedRegValues.add(lhs);
        usedRegValues.add(rhs);
    }

    @Override
    public void setUsedRegisters(Map<IRRegister, IRRegister> renameMap) {
        if (lhs instanceof IRRegister) lhs = renameMap.get(lhs);
        if (rhs instanceof IRRegister) rhs = renameMap.get(rhs);
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

    public IRCmpOp getOp() {
        return op;
    }

    public RegValue getLhs() {
        return lhs;
    }

    public RegValue getRhs() {
        return rhs;
    }

    @Override
    public IRComparison copyRename(Map<Object, Object> renameMap) {
        return new IRComparison(
                (BasicBlock) renameMap.getOrDefault(getParentBB(), getParentBB()),
                (IRRegister) renameMap.getOrDefault(dest, dest),
                op,
                (RegValue) renameMap.getOrDefault(lhs, lhs),
                (RegValue) renameMap.getOrDefault(rhs, rhs)
        );
    }
}
