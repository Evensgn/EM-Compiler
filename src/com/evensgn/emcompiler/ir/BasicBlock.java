package com.evensgn.emcompiler.ir;

import com.evensgn.emcompiler.utils.CompilerError;

import java.util.HashSet;
import java.util.Set;

public class BasicBlock {
    private IRInstruction firstInst = null, lastInst = null;
    private IRFunction func;
    private String name;
    private boolean hasJumpInst = false;
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
            lastInst.setNextInst(inst);
            lastInst = inst;
        }
    }

    public void addPrevBB(BasicBlock bb) {
        prevBBSet.add(bb);
    }

    public void addNextBB(BasicBlock bb) {
        if (bb != null) {
            nextBBSet.add(bb);
        }
        bb.addPrevBB(this);
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

        } else {
            throw new CompilerError("invalid type of IRJumpInstruction");
        }
    }

    public boolean isHasJumpInst() {
        return hasJumpInst;
    }
}
