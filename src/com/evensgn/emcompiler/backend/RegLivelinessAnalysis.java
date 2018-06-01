package com.evensgn.emcompiler.backend;

import com.evensgn.emcompiler.ir.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class RegLivelinessAnalysis {
    private IRRoot ir;

    public RegLivelinessAnalysis(IRRoot ir) {
        this.ir = ir;
    }

    public void run() {
        for (IRFunction irFunction : ir.getFuncs().values()) {
            List<BasicBlock> reversePreOrder = irFunction.getReversePreOrder();
            for (BasicBlock bb : reversePreOrder) {
                // init basic block
                for (IRInstruction inst = bb.getFirstInst(); inst != null; inst = inst.getNextInst()) {
                    inst.liveIn = new HashSet<>();
                    inst.liveOut = new HashSet<>();
                }
            }
            Set<VirtualRegister> liveIn = new HashSet<>();
            Set<VirtualRegister> liveOut = new HashSet<>();

            // iterations to solve liveliness equation
            boolean converged = false;
            while (!converged) {
                converged = true;
                for (BasicBlock bb : reversePreOrder) {
                    for (IRInstruction inst = bb.getLastInst(); inst != null; inst = inst.getPrevInst()) {
                        liveIn.clear();
                        liveOut.clear();
                        if (inst instanceof IRJumpInstruction) {
                            if (inst instanceof IRJump) {
                                liveOut.addAll(((IRJump) inst).getTargetBB().getFirstInst().liveIn);
                            } else if (inst instanceof IRBranch) {
                                liveOut.addAll(((IRBranch) inst).getThenBB().getFirstInst().liveIn);
                                liveOut.addAll(((IRBranch) inst).getElseBB().getFirstInst().liveIn);
                            }
                        } else {
                            liveOut.addAll(inst.getNextInst().liveIn);
                        }
                        liveIn.addAll(liveOut);
                        IRRegister definedReg = inst.getDefinedRegister();
                        if (definedReg instanceof VirtualRegister) {
                            liveIn.remove(definedReg);
                        }
                        for (IRRegister usedReg : inst.getUsedRegisters()) {
                            if (usedReg instanceof VirtualRegister) {
                                liveIn.add((VirtualRegister) usedReg);
                            }
                        }
                        if (!inst.liveIn.equals(liveIn)) {
                            converged = false;
                            inst.liveIn.clear();
                            inst.liveIn.addAll(liveIn);
                        }
                        if (!inst.liveOut.equals(liveOut)) {
                            converged = false;
                            inst.liveOut.clear();
                            inst.liveOut.addAll(liveOut);
                        }
                    }
                }
            }
        }
    }
}
