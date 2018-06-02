package com.evensgn.emcompiler.backend;

import com.evensgn.emcompiler.Configuration;
import com.evensgn.emcompiler.ir.*;
import com.evensgn.emcompiler.utils.CompilerError;

import java.util.*;

public class RegisterAllocator {
    private IRRoot ir;
    private List<PhysicalRegister> physicalRegs;
    private PhysicalRegister preg0, preg1;
    private int numColors;

    public RegisterAllocator(IRRoot ir, Collection<PhysicalRegister> physicalRegs) {
        this.ir = ir;
        this.physicalRegs = new ArrayList<>(physicalRegs);
        preg0 = this.physicalRegs.get(0);
        preg1 = this.physicalRegs.get(1);
        this.physicalRegs.remove(preg0);
        this.physicalRegs.remove(preg1);
        numColors = this.physicalRegs.size();
    }

    private class VirtualRegInfo {
        Set<VirtualRegister> neighbours = new HashSet<>();
        boolean removed = false;
        IRRegister color = null;
        int degree = 0;
        Set<VirtualRegister> suggestSameVRegs = new HashSet<>();
    }

    private VirtualRegInfo getVregInfo(VirtualRegister vreg) {
        VirtualRegInfo vregInfo = vregInfoMap.get(vreg);
        if (vregInfo == null) {
            vregInfo = new VirtualRegInfo();
            vregInfoMap.put(vreg, vregInfo);
        }
        return vregInfo;
    }

    private Map<VirtualRegister, VirtualRegInfo> vregInfoMap = new HashMap<>();
    private List<VirtualRegister> vregOrder = new ArrayList<>();
    private Set<PhysicalRegister> usedColors = new HashSet<>();
    private Set<VirtualRegister> vregNodes = new HashSet<>();
    private Set<VirtualRegister> degreeSmallVregNodes = new HashSet<>();

    private Map<IRRegister, IRRegister> renameMap = new HashMap<>();

    private void addEdge(VirtualRegister x, VirtualRegister y) {
        getVregInfo(x).neighbours.add(y);
        getVregInfo(y).neighbours.add(x);
    }

    private void removeVregNode(VirtualRegister vreg) {
        VirtualRegInfo vregInfo = vregInfoMap.get(vreg), neigbhourInfo;
        vregInfo.removed = true;
        vregNodes.remove(vreg);
        for (VirtualRegister neighbour : vregInfo.neighbours) {
            neigbhourInfo = vregInfoMap.get(neighbour);
            if (neigbhourInfo.removed) continue;;
            --neigbhourInfo.degree;
            if (neigbhourInfo.degree < numColors) {
                degreeSmallVregNodes.add(neighbour);
            }
        }
    }

    public void run() {
        for (IRFunction irFunction : ir.getFuncs().values()) {
            vregInfoMap.clear();
            vregNodes.clear();
            degreeSmallVregNodes.clear();

            for (VirtualRegister argVreg : irFunction.getArgVRegList()) {
                getVregInfo(argVreg);
            }
            for (BasicBlock bb : irFunction.getReversePreOrder()) {
                for (IRInstruction inst = bb.getFirstInst(); inst != null; inst = inst.getNextInst()) {
                    IRRegister definedReg = inst.getDefinedRegister();
                    if (!(definedReg instanceof VirtualRegister)) continue;
                    VirtualRegInfo vregInfo = getVregInfo((VirtualRegister) definedReg);
                    if (inst instanceof IRMove) {
                        RegValue rhs = ((IRMove) inst).getRhs();
                        if (rhs instanceof VirtualRegister) {
                            vregInfo.suggestSameVRegs.add((VirtualRegister) rhs);
                            getVregInfo((VirtualRegister) rhs).suggestSameVRegs.add((VirtualRegister) definedReg);
                        }
                        for (VirtualRegister vreg : inst.liveOut) {
                            if (vreg != rhs && vreg != definedReg) {
                                addEdge(vreg, (VirtualRegister) definedReg);
                            }
                        }
                    } else {
                        for (VirtualRegister vreg : inst.liveOut) {
                            if (vreg != definedReg) {
                                addEdge(vreg, (VirtualRegister) definedReg);
                            }
                        }
                    }
                }
            }
            for (VirtualRegInfo vregInfo : vregInfoMap.values()) {
                vregInfo.degree = vregInfo.neighbours.size();
            }
            vregNodes.addAll(vregInfoMap.keySet());
            for (VirtualRegister vreg : vregNodes) {
                if (vregInfoMap.get(vreg).degree < numColors) {
                    degreeSmallVregNodes.add(vreg);
                }
            }

            vregOrder.clear();
            while (!vregNodes.isEmpty()) {
                while (!degreeSmallVregNodes.isEmpty()) {
                    Iterator<VirtualRegister> iterator = degreeSmallVregNodes.iterator();
                    VirtualRegister vreg = iterator.next();
                    removeVregNode(vreg);
                    iterator.remove();
                    vregOrder.add(vreg);
                }
                if (vregNodes.isEmpty()) break;
                Iterator<VirtualRegister> iterator = vregNodes.iterator();
                VirtualRegister vreg = iterator.next();
                removeVregNode(vreg);
                iterator.remove();
                vregOrder.add(vreg);
            }
            Collections.reverse(vregOrder);
            for (VirtualRegister vreg : vregOrder) {
                VirtualRegInfo vregInfo = vregInfoMap.get(vreg);
                vregInfo.removed = false;
                usedColors.clear();
                for (VirtualRegister neighbour : vregInfo.neighbours) {
                    VirtualRegInfo neighbourInfo = vregInfoMap.get(neighbour);
                    if (!neighbourInfo.removed && neighbourInfo.color instanceof PhysicalRegister) {
                        usedColors.add((PhysicalRegister) neighbourInfo.color);
                    }
                }
                PhysicalRegister forcedPhysicalRegister = vreg.getForcedPhysicalRegister();
                if (forcedPhysicalRegister != null) {
                    if (usedColors.contains(forcedPhysicalRegister)) {
                        throw new CompilerError("forced physical register has been used");
                    }
                    vregInfo.color = forcedPhysicalRegister;
                } else {
                    for (VirtualRegister suggestSameVreg : vregInfo.suggestSameVRegs) {
                        IRRegister color = getVregInfo(suggestSameVreg).color;
                        if (color instanceof PhysicalRegister && !usedColors.contains(color)) {
                            vregInfo.color = color;
                            break;
                        }
                    }
                    if (vregInfo.color == null) {
                        for (PhysicalRegister physicalReg : physicalRegs) {
                            if (!usedColors.contains(physicalReg)) {
                                vregInfo.color = physicalReg;
                                break;
                            }
                        }
                        if (vregInfo.color == null) {
                            vregInfo.color = irFunction.getArgsStackSlotMap().get(vreg);
                            if (vregInfo.color == null) vregInfo.color = new StackSlot(irFunction, vreg.getName());
                        }
                    }
                }
            }

            updateInstruction(irFunction);
        }
    }

    private void updateInstruction(IRFunction func) {
        for (BasicBlock bb : func.getReversePreOrder()) {
            for (IRInstruction inst = bb.getFirstInst(); inst != null; inst = inst.getNextInst()) {
                if (inst instanceof IRFunctionCall) {
                    List<RegValue> args = ((IRFunctionCall) inst).getArgs();
                    for (int i = 0; i < args.size(); ++i) {
                        if (args.get(i) instanceof VirtualRegister) {
                            args.set(i, vregInfoMap.get(args.get(i)).color);
                        }
                    }
                } else {
                    Collection<IRRegister> usedRegisters = inst.getUsedRegisters();
                    if (!usedRegisters.isEmpty()) {
                        boolean usedPreg0 = false;
                        renameMap.clear();
                        for (IRRegister reg : usedRegisters) {
                            if (reg instanceof VirtualRegister) {
                                IRRegister color = vregInfoMap.get(reg).color;
                                if (color instanceof StackSlot) {
                                    PhysicalRegister preg;
                                    if (usedPreg0) {
                                        preg = preg1;
                                    } else {
                                        preg = preg0;
                                        usedPreg0 = true;
                                    }
                                    inst.prependInst(new IRLoad(bb, preg, Configuration.getRegSize(), color, 0));
                                    renameMap.put(reg, preg);
                                    func.getUsedPhysicalGeneralRegs().add(preg);
                                } else {
                                    renameMap.put(reg, color);
                                    func.getUsedPhysicalGeneralRegs().add((PhysicalRegister) color);
                                }
                            } else {
                                renameMap.put(reg, reg);
                            }
                        }
                        inst.setUsedRegisters(renameMap);
                    }
                }
                IRRegister definedReg = inst.getDefinedRegister();
                if (definedReg instanceof VirtualRegister) {
                    IRRegister color = vregInfoMap.get(definedReg).color;
                    if (color instanceof StackSlot) {
                        inst.setDefinedRegister(preg0);
                        inst.appendInst(new IRStore(bb, preg0, Configuration.getRegSize(), color, 0));
                        func.getUsedPhysicalGeneralRegs().add(preg0);
                        inst = inst.getNextInst();
                    } else {
                        inst.setDefinedRegister(color);
                        func.getUsedPhysicalGeneralRegs().add((PhysicalRegister) color);
                    }
                }
            }
        }
    }
}
