package com.evensgn.emcompiler.ir;

public class IRUnaryOperation extends IRInstruction {
    public enum IRUnaryOp {
        NOT, NEG
    }
    public IRUnaryOperation(BasicBlock parentBB) {
        super(parentBB);
    }
}
