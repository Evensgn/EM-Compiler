package com.evensgn.emcompiler.ir;

public abstract class RegValue {
    public abstract void accept(IRVisitor visitor);
    public abstract RegValue copy();
}
