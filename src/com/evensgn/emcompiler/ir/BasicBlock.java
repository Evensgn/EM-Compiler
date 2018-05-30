package com.evensgn.emcompiler.ir;

import com.evensgn.emcompiler.utils.CompilerError;

import java.util.HashSet;
import java.util.Set;

public class BasicBlock {
    private IRInstruction firstInst = null, lastInst = null;
    private IRFunction func;
    private String name;
    private boolean hasJumpInst = false;
    private int postOrderIdx;
    private Set<BasicBlock> prevBBSet = new HashSet<>(), nextBBSet = new HashSet<>();

    public BasicBlock(IRFunction func, String name) {
        this.func = func;
        this.name = name;
    }

    public void addInst(IRInstruction inst) {
        if (hasJumpInst) {
            throw new CompilerError("cannot add instruction into finished basic block");
        }
        if (lastInst == null) {
            firstInst = lastInst = inst;
        } else {
            lastInst.linkNextInst(inst);
            lastInst = inst;
        }
    }

    public String getName() {
        return name;
    }

    public void addPrevBB(BasicBlock bb) {
        prevBBSet.add(bb);
    }

    public void addNextBB(BasicBlock bb) {
        nextBBSet.add(bb);
        if (bb != null) {
            bb.addPrevBB(this);
        }
    }

    public void delPrevBB(BasicBlock bb) {
        prevBBSet.remove(bb);
    }

    public void delNextBB(BasicBlock bb) {
        nextBBSet.remove(bb);
        if (bb != null) {
            bb.delPrevBB(this);
        }
    }

    public void setJumpInst(IRJumpInstruction jumpInst) {
        addInst(jumpInst);
        hasJumpInst = true;
        if (jumpInst instanceof IRBranch) {
            addNextBB(((IRBranch) jumpInst).getThenBB());
            addNextBB(((IRBranch) jumpInst).getElseBB());
        } else if (jumpInst instanceof IRJump) {
            addNextBB(((IRJump) jumpInst).getTargetBB());
        } else if (jumpInst instanceof IRReturn) {
            func.getRetInstList().add((IRReturn) jumpInst);
        } else {
            throw new CompilerError("invalid type of IRJumpInstruction");
        }
    }

    public void removeJumpInst() {
        hasJumpInst = false;
        if (lastInst instanceof IRBranch) {
            delNextBB(((IRBranch) lastInst).getThenBB());
            delNextBB(((IRBranch) lastInst).getElseBB());
        } else if (lastInst instanceof IRJump) {
            delNextBB(((IRJump) lastInst).getTargetBB());
        } else if (lastInst instanceof IRReturn) {
            func.getRetInstList().remove((IRReturn) lastInst);
        } else {
            throw new CompilerError("invalid type of IRJumpInstruction");
        }
    }

    public boolean isHasJumpInst() {
        return hasJumpInst;
    }

    public Set<BasicBlock> getPrevBBSet() {
        return prevBBSet;
    }

    public Set<BasicBlock> getNextBBSet() {
        return nextBBSet;
    }

    public void setPostOrderIdx(int postOrderIdx) {
        this.postOrderIdx = postOrderIdx;
    }

    public int getPostOrderIdx() {
        return postOrderIdx;
    }

    public IRInstruction getFirstInst() {
        return firstInst;
    }

    public IRInstruction getLastInst() {
        return lastInst;
    }

    public void setFirstInst(IRInstruction firstInst) {
        this.firstInst = firstInst;
    }

    public void setLastInst(IRInstruction lastInst) {
        this.lastInst = lastInst;
    }

    public IRFunction getFunc() {
        return func;
    }

    public void accept(IRVisitor visitor) {
        visitor.visit(this);
    }
}
