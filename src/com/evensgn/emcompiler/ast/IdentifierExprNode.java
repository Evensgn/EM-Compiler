package com.evensgn.emcompiler.ast;

public class IdentifierExprNode extends ExprNode {
    private String identifier;
    private boolean needMemOp = false;
    private boolean checked = false;

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

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
