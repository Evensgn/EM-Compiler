package com.evensgn.emcompiler.ir;

import java.rmi.registry.Registry;
import java.util.Map;

import static com.evensgn.emcompiler.ir.IRBinaryOperation.IRBinaryOp.*;

public class IRBinaryOperation extends IRInstruction {
    public enum IRBinaryOp {
        ADD, SUB, MUL, DIV, MOD,
        SHL, SHR,
        BITWISE_AND, BITWISE_OR, BITWISE_XOR
    }

    private IRRegister dest;
    private IRBinaryOp op;
    private RegValue lhs, rhs;

    public IRBinaryOperation(BasicBlock parentBB, IRRegister dest, IRBinaryOp op, RegValue lhs, RegValue rhs) {
        super(parentBB);
        this.dest = dest;
        this.op = op;
        this.lhs = lhs;
        this.rhs = rhs;
        reloadUsedRegistersRegValues();
    }

    public boolean isCommutativeOp() {
        return op == ADD || op == MUL || op == BITWISE_AND || op == BITWISE_OR || op == BITWISE_XOR;
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

    @Override
    public void setDefinedRegister(VirtualRegister vreg) {
        dest = vreg;
    }

    public void accept(IRVisitor visitor) {
        visitor.visit(this);
    }

    public IRBinaryOp getOp() {
        return op;
    }

    public IRRegister getDest() {
        return dest;
    }

    public RegValue getLhs() {
        return lhs;
    }

    public RegValue getRhs() {
        return rhs;
    }

    public void setLhs(RegValue lhs) {
        this.lhs = lhs;
    }

    public void setRhs(RegValue rhs) {
        this.rhs = rhs;
    }

    @Override
    public IRBinaryOperation copyRename(Map<Object, Object> renameMap) {
        return new IRBinaryOperation(
                (BasicBlock) renameMap.getOrDefault(getParentBB(), getParentBB()),
                (IRRegister) renameMap.getOrDefault(dest, dest),
                op,
                (RegValue) renameMap.getOrDefault(lhs, lhs),
                (RegValue) renameMap.getOrDefault(rhs, rhs)
        );
    }
}
