package com.evensgn.emcompiler.ast;

import com.evensgn.emcompiler.ir.BasicBlock;
import com.evensgn.emcompiler.ir.RegValue;
import com.evensgn.emcompiler.type.Type;

/**
 * @author Zhou Fan
 * @since 4/1/2018
 */
abstract public class ExprNode extends Node {
    private Type type;
    private boolean isLeftValue;
    private RegValue regValue, addrValue;
    private int addrOffset;
    private BasicBlock trueBB, falseBB;

    public void setType(Type type) {
        this.type = type;
    }

    public void setLeftValue(boolean leftValue) {
        isLeftValue = leftValue;
    }

    public Type getType() {
        return type;
    }

    public boolean isLeftValue() {
        return isLeftValue;
    }

    public RegValue getRegValue() {
        return regValue;
    }

    public BasicBlock getTrueBB() {
        return trueBB;
    }

    public BasicBlock getFalseBB() {
        return falseBB;
    }

    public void setTrueBB(BasicBlock trueBB) {
        this.trueBB = trueBB;
    }

    public void setFalseBB(BasicBlock falseBB) {
        this.falseBB = falseBB;
    }

    public RegValue getAddrValue() {
        return addrValue;
    }

    public int getAddrOffset() {
        return addrOffset;
    }

    public void setAddrValue(RegValue addrValue) {
        this.addrValue = addrValue;
    }

    public void setAddrOffset(int addrOffset) {
        this.addrOffset = addrOffset;
    }

    public void setRegValue(RegValue regValue) {
        this.regValue = regValue;
    }
}
