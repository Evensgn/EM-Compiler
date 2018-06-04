package com.evensgn.emcompiler.backend;

import com.evensgn.emcompiler.Configuration;
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
                    IRRegister definedRegister = inst.getDefinedRegister();
                    if (definedRegister != null && definedRegister instanceof StaticData) {
                        VirtualRegister vreg = getStaticDataVreg(funcInfo.staticDataVregMap, (StaticData) definedRegister);
                        inst.setDefinedRegister(vreg);
                        funcInfo.definedStaticData.add((StaticData) definedRegister);
                    }
                }
            }

            // load static data at the beginning of function
            BasicBlock startBB = irFunction.getStartBB();
            IRInstruction firtInst = startBB.getFirstInst();
            funcInfo.staticDataVregMap.forEach((staticData, virtualRegister) ->
                    firtInst.prependInst(new IRLoad(startBB, virtualRegister, Configuration.getRegSize(), staticData, staticData instanceof StaticString)));
        }

        for (IRFunction builtFunc : ir.getBuiltInFuncs().values()) {
            funcInfoMap.put(builtFunc, new FuncInfo());
        }
        for (IRFunction irFunction : ir.getFuncs().values()) {
            FuncInfo funcInfo = funcInfoMap.get(irFunction);
            funcInfo.recursiveUsedStaticData.addAll(funcInfo.staticDataVregMap.keySet());
            for (IRFunction calleeFunc : irFunction.recursiveCalleeSet) {
                FuncInfo calleeFuncInfo = funcInfoMap.get(calleeFunc);
                funcInfo.recursiveUsedStaticData.addAll(calleeFuncInfo.staticDataVregMap.keySet());
            }
        }

        for (IRFunction irFunction : ir.getFuncs().values()) {
            FuncInfo funcInfo = funcInfoMap.get(irFunction);
            Set<StaticData> usedStaticData = funcInfo.staticDataVregMap.keySet();
            if (usedStaticData.isEmpty()) continue;
            for (BasicBlock bb : irFunction.getReversePostOrder()) {
                for (IRInstruction inst = bb.getFirstInst(); inst != null; inst = inst.getNextInst()) {
                    if (!(inst instanceof IRFunctionCall)) continue;
                    IRFunction calleeFunc = ((IRFunctionCall) inst).getFunc();
                    FuncInfo calleeFuncInfo = funcInfoMap.get(calleeFunc);
                    // store defined static data before function call
                    for (StaticData staticData : funcInfo.definedStaticData) {
                        if (calleeFuncInfo.recursiveUsedStaticData.contains(staticData)) {
                            inst.prependInst(new IRStore(bb, funcInfo.staticDataVregMap.get(staticData), Configuration.getRegSize(), staticData));
                        }
                    }
                    // load used static data after function call
                    if (calleeFuncInfo.definedStaticData.isEmpty()) continue;
                    Set<StaticData> loadStaticDataSet = new HashSet<>();
                    loadStaticDataSet.addAll(calleeFuncInfo.definedStaticData);
                    loadStaticDataSet.retainAll(usedStaticData);
                    for (StaticData staticData : loadStaticDataSet) {
                        inst.appendInst(new IRLoad(bb, funcInfo.staticDataVregMap.get(staticData), Configuration.getRegSize(), staticData, staticData instanceof StaticString));
                    }
                }
            }
        }

        for (IRFunction irFunction : ir.getFuncs().values()) {
            FuncInfo funcInfo = funcInfoMap.get(irFunction);
            IRReturn retInst = irFunction.getRetInstList().get(0);
            // store defined data at the end of function
            for (StaticData staticData : funcInfo.definedStaticData) {
                retInst.prependInst(new IRStore(retInst.getParentBB(), funcInfo.staticDataVregMap.get(staticData), Configuration.getRegSize(), staticData));
            }
        }
    }
}
