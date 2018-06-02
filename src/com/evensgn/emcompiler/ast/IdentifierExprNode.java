package com.evensgn.emcompiler.ast;

import com.evensgn.emcompiler.scope.VarEntity;

public class IdentifierExprNode extends ExprNode {
    private String identifier;
    private boolean needMemOp = false;
    private boolean checked = false;
    private VarEntity varEntity = null;

    public IdentifierExprNode(String identifier, Location location) {
        this.identifier = identifier;
        this.location = location;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setNeedMemOp(boolean needMemOp) {
        this.needMemOp = needMemOp;
    }

    public boolean isNeedMemOp() {
        return needMemOp;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public void setVarEntity(VarEntity varEntity) {
        this.varEntity = varEntity;
    }

    public VarEntity getVarEntity() {
        return varEntity;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
