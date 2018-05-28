package com.evensgn.emcompiler.ir;

import java.util.List;

public class IRFunctionCall extends IRInstruction {
    private IRFunction func;
    private List<RegValue> args;
    private VirtualRegister dest;

    public IRFunctionCall(BasicBlock parentBB, IRFunction func, List<RegValue> args, VirtualRegister dest) {
        super(parentBB);
        this.func = func;
        this.args = args;
        this.dest = dest;
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
}