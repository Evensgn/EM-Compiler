package com.evensgn.emcompiler.backend;

import com.evensgn.emcompiler.Configuration;
import com.evensgn.emcompiler.ir.*;

import java.util.HashMap;
import java.util.Map;

public class RegisterPreprocessor {
    private IRRoot ir;

    public RegisterPreprocessor(IRRoot ir) {
        this.ir = ir;
    }

    private void processFuncArgs(IRFunction func) {
        Map<IRRegister, IRRegister> argsMap = new HashMap<>();

        IRInstruction firtInst = func.getStartBB().getFirstInst();
        for (int i = 0; i < func.getArgVRegList().size(); ++i) {
            VirtualRegister argVreg = func.getArgVRegList().get(i);
            StackSlot argSlot = new StackSlot(func, "arg" + i);
            func.getArgsStackSlotMap().put(argVreg, argSlot);
            if (i > 5) firtInst.prependInst(new IRLoad(firtInst.getParentBB(), argVreg, Configuration.getRegSize(), argSlot, 0));
        }
        if (func.getArgVRegList().size() > 0) func.getArgVRegList().get(0).setForcedPhysicalRegister();
    }

    public void run() {
        for (IRFunction irFunction : ir.getFuncs().values()) {
            processFuncArgs(irFunction);
        }
    }
}
