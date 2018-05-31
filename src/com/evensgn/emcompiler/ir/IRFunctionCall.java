package com.evensgn.emcompiler.ir;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class IRFunctionCall extends IRInstruction {
    private IRFunction func;
    private List<RegValue> args;
    private VirtualRegister dest;

    public IRFunctionCall(BasicBlock parentBB, IRFunction func, List<RegValue> args, VirtualRegister dest) {
        super(parentBB);
        this.func = func;
        this.args = args;
        this.dest = dest;
        reloadUsedRegistersRegValues();
    }

    @Override
    public void reloadUsedRegistersRegValues() {
        usedRegisters.clear();
        usedRegValues.clear();
        for (RegValue arg : args) {
            if (arg instanceof IRRegister) usedRegisters.add((IRRegister) arg);
            usedRegValues.add(arg);
        }
    }

    @Override
    public void setUsedRegisters(Map<IRRegister, IRRegister> renameMap) {
        for (int i = 0; i < args.size(); ++i) {
            if (args.get(i) instanceof IRRegister) {
                args.set(i, renameMap.get((IRRegister) args.get(i)));
            }
        }
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

    public IRFunction getFunc() {
        return func;
    }

    public List<RegValue> getArgs() {
        return args;
    }

    public VirtualRegister getDest() {
        return dest;
    }

    @Override
    public IRFunctionCall copyRename(Map<Object, Object> renameMap) {
        List<RegValue> copyArgs = new ArrayList<>();
        for (RegValue arg : args) {
            copyArgs.add((RegValue) renameMap.getOrDefault(arg, arg));
        }
        return new IRFunctionCall(
                (BasicBlock) renameMap.getOrDefault(getParentBB(), getParentBB()),
                func,
                copyArgs,
                (VirtualRegister) renameMap.getOrDefault(dest, dest)
        );
    }
}