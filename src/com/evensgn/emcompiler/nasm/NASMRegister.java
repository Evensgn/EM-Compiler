package com.evensgn.emcompiler.nasm;

import com.evensgn.emcompiler.ir.PhysicalRegister;

public class NASMRegister extends PhysicalRegister {
    private final String name;
    private final boolean isGeneral, isCallerSave, isCalleeSave;
    private final int arg6Idx;

    public NASMRegister(String name, boolean isGeneral, boolean isCallerSave, boolean isCalleeSave, int arg6Idx) {
        this.name = name;
        this.isGeneral = isGeneral;
        this.isCallerSave = isCallerSave;
        this.isCalleeSave = isCalleeSave;
        this.arg6Idx = arg6Idx;
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

    @Override
    public boolean isArg6() {
        return arg6Idx != -1;
    }

    @Override
    public int getArg6Idx() {
        return arg6Idx;
    }
}
