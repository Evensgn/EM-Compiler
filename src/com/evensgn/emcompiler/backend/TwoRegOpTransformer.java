package com.evensgn.emcompiler.backend;

import com.evensgn.emcompiler.ir.*;

public class TwoRegOpTransformer {
    private IRRoot ir;

    public TwoRegOpTransformer(IRRoot ir) {
        this.ir = ir;
    }

    public void run() {
        for (IRFunction irFunction : ir.getFuncs().values()) {
            for (BasicBlock bb : irFunction.getReversePostOrder()) {
                for (IRInstruction inst = bb.getFirstInst(), nextInst; inst != null; inst = nextInst) {
                    nextInst = inst.getNextInst();
                    if (!(inst instanceof IRBinaryOperation)) continue;
                    IRBinaryOperation binaryInst = (IRBinaryOperation) inst;
                    if (binaryInst.getDest() == binaryInst.getLhs()) continue;
                    if (binaryInst.getDest() == binaryInst.getRhs()) {
                        if (binaryInst.isCommutativeOp()) {
                            binaryInst.setRhs(binaryInst.getLhs());
                            binaryInst.setLhs(binaryInst.getDest());
                        } else {
                            VirtualRegister vreg = new VirtualRegister("rhsBak");
                            binaryInst.prependInst(new IRMove(binaryInst.getParentBB(), vreg, binaryInst.getRhs()));
                            binaryInst.prependInst(new IRMove(binaryInst.getParentBB(), binaryInst.getDest(), binaryInst.getLhs()));
                            binaryInst.setLhs(binaryInst.getDest());
                            binaryInst.setRhs(vreg);
                        }
                    } else if (binaryInst.getOp() != IRBinaryOperation.IRBinaryOp.DIV &&
                            binaryInst.getOp() != IRBinaryOperation.IRBinaryOp.MOD) {
                        binaryInst.prependInst(new IRMove(binaryInst.getParentBB(), binaryInst.getDest(), binaryInst.getLhs()));
                        binaryInst.setLhs(binaryInst.getDest());
                    }
                }
            }
        }
    }
}
