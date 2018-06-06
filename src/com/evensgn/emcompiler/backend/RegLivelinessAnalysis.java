package com.evensgn.emcompiler.backend;

import com.evensgn.emcompiler.ast.AssignExprNode;
import com.evensgn.emcompiler.ir.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class RegLivelinessAnalysis {
    private IRRoot ir;
    private boolean eliminationChanged;

    public RegLivelinessAnalysis(IRRoot ir) {
        this.ir = ir;
    }

    private void livelinessAnalysis(IRFunction irFunction) {
        List<BasicBlock> reversePreOrder = irFunction.getReversePreOrder();
        for (BasicBlock bb : reversePreOrder) {
            // init basic block
            for (IRInstruction inst = bb.getFirstInst(); inst != null; inst = inst.getNextInst()) {
                if (inst.liveIn == null) inst.liveIn = new HashSet<>();
                else inst.liveIn.clear();
                if (inst.liveOut == null) inst.liveOut = new HashSet<>();
                else inst.liveOut.clear();
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
                        if (inst.getNextInst() != null)
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

    void tryEliminate(IRFunction func) {
        List<BasicBlock> reversePreOrder = func.getReversePreOrder();
        for (BasicBlock bb : reversePreOrder) {
            for (IRInstruction inst = bb.getLastInst(), prevInst; inst != null; inst = prevInst) {
                prevInst = inst.getPrevInst();
                if (inst instanceof IRBinaryOperation || inst instanceof IRComparison ||
                        inst instanceof IRLoad || inst instanceof IRMove || inst instanceof IRUnaryOperation) {
                    IRRegister dest = inst.getDefinedRegister();
                    if (dest == null || !inst.liveOut.contains(dest)) {
                        eliminationChanged = true;
                        inst.remove();
                    }
                }
            }
        }
    }

    void updateState(IRFunction func) {
        // do nothing
    }

    public void run() {
        for (IRFunction irFunction : ir.getFuncs().values()) {
            livelinessAnalysis(irFunction);
        }
        eliminationChanged = true;
        while (eliminationChanged) {
            eliminationChanged = false;
            for (IRFunction irFunction : ir.getFuncs().values()) {
                if (irFunction.isBuiltIn()) continue;
                tryEliminate(irFunction);
                updateState(irFunction);
                livelinessAnalysis(irFunction);
            }
        }
    }
}
