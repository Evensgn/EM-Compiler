package com.evensgn.emcompiler.scope;

import com.evensgn.emcompiler.ast.VarDeclNode;
import com.evensgn.emcompiler.ir.IRRegister;
import com.evensgn.emcompiler.type.Type;

public class VarEntity extends Entity {
    private IRRegister irRegister;
    // for member variables
    private int addrOffset;

    public VarEntity(String name, Type type) {
        super(name, type);
    }

    public VarEntity(VarDeclNode node) {
        super(node.getName(), node.getType().getType());
    }

    public IRRegister getIrRegister() {
        return irRegister;
    }

    public void setIrRegister(IRRegister irRegister) {
        this.irRegister = irRegister;
    }

    public int getAddrOffset() {
        return addrOffset;
    }

    public void setAddrOffset(int addrOffset) {
        this.addrOffset = addrOffset;
    }
}
