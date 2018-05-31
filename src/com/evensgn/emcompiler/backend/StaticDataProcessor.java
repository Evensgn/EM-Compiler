package com.evensgn.emcompiler.backend;

import com.evensgn.emcompiler.ir.*;

import java.util.*;

public class StaticDataProcessor {
    private IRRoot ir;

    public StaticDataProcessor(IRRoot ir) {
        this.ir = ir;
    }

    private class FuncInfo {
        Set<StaticData> definedStaticData = new HashSet<>();
        Set<StaticData> recursiveUsedStaticData = new HashSet<>();
        Map<StaticData, VirtualRegister> staticDataVregMap = new HashMap<>();
    }

    private Map<IRFunction, FuncInfo> funcInfoMap = new HashMap<>();

    private boolean isStaticLoadStore(IRInstruction inst) {
        return (inst instanceof IRLoad && ((IRLoad) inst).isStaticData()) ||
                (inst instanceof IRStore && ((IRStore) inst).isStaticData());
    }

    private VirtualRegister getStaticDataVreg(Map<StaticData, VirtualRegister> staticDataVregMap, StaticData staticData) {
        VirtualRegister vreg = staticDataVregMap.get(staticData);
        if (vreg == null) {
            vreg = new VirtualRegister(staticData.getName());
            staticDataVregMap.put(staticData, vreg);
        }
        return vreg;
    }

    public void run() {
        for (IRFunction irFunction : ir.getFuncs().values()) {
            FuncInfo funcInfo = new FuncInfo();
            funcInfoMap.put(irFunction, funcInfo);
            Map<IRRegister, IRRegister> renameMap = new HashMap<>();
            for (BasicBlock bb : irFunction.getReversePostOrder()) {
                for (IRInstruction inst = bb.getFirstInst(); inst != null; inst = inst.getNextInst()) {
                    if (isStaticLoadStore(inst)) continue;
                    List<IRRegister> usedRegisters = inst.getUsedRegisters();
                    if (!usedRegisters.isEmpty()) {
                        renameMap.clear();
                        for (IRRegister reg : usedRegisters) {
                            if (reg instanceof StaticData) {
                                renameMap.put(reg, getStaticDataVreg(funcInfo.staticDataVregMap, (StaticData) reg));
                            } else {
                                renameMap.put(reg, reg);
                            }
                        }
                        inst.setUsedRegisters(renameMap);
                    }
                }
            }
        }
    }
}
