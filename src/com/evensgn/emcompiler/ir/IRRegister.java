package com.evensgn.emcompiler.ir;

public abstract class IRRegister extends RegValue {
    public abstract void accept(IRVisitor visitor);
}
