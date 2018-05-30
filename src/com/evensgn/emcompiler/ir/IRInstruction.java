package com.evensgn.emcompiler.ir;

import com.evensgn.emcompiler.utils.CompilerError;

import java.util.Map;

public abstract class IRInstruction {
    private IRInstruction prevInst = null, nextInst = null;
    private BasicBlock parentBB;
    private boolean removed = false;

    public IRInstruction(BasicBlock parentBB) {
        this.parentBB = parentBB;
    }

    public void setPrevInst(IRInstruction prevInst) {
        this.prevInst = prevInst;
    }

    public void setNextInst(IRInstruction nextInst) {
        this.nextInst = nextInst;
    }

    public void remove() {
        if (removed) {
            throw new CompilerError("cannot remove an instruction already removed");
        }
        removed = true;
        prevInst.setNextInst(nextInst);
        nextInst.setPrevInst(prevInst);
        if (this instanceof IRBranch) {
            parentBB.removeJumpInst();
        }
        if (this == parentBB.getFirstInst()) parentBB.setFirstInst(nextInst);
        if (this == parentBB.getLastInst()) parentBB.setLastInst(prevInst);
    }

    public abstract void accept(IRVisitor visitor);

    public IRInstruction getPrevInst() {
        return prevInst;
    }

    public IRInstruction getNextInst() {
        return nextInst;
    }

    public BasicBlock getParentBB() {
        return parentBB;
    }

    public abstract IRInstruction copyRename(Map<Object, Object> renameMap);
}
