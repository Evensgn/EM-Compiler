package com.evensgn.emcompiler.nasm;

import com.evensgn.emcompiler.ir.PhysicalRegister;

public class NASMRegister extends PhysicalRegister {
    private final String name;
    private final boolean isGeneral, isCallerSave, isCalleeSave;

    public NASMRegister(String name, boolean isGeneral, boolean isCallerSave, boolean isCalleeSave) {
        this.name = name;
        this.isGeneral = isGeneral;
        this.isCallerSave = isCallerSave;
        this.isCalleeSave = isCalleeSave;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean isGeneral() {
        return isGeneral;
    }

    @Override
    public boolean isCallerSave() {
        return isCallerSave;
    }

    @Override
    public boolean isCalleeSave() {
        return isCalleeSave;
    }
}
