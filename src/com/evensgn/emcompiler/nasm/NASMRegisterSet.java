package com.evensgn.emcompiler.nasm;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class NASMRegisterSet {
    public static final Collection<NASMRegister> allRegs, generalRegs, callerSaveRegs, calleeSaveRegs;

    static {
        List<NASMRegister> all = new ArrayList<>();
        List<NASMRegister> general = new ArrayList<>();
        List<NASMRegister> callerSave = new ArrayList<>();
        List<NASMRegister> calleeSave = new ArrayList<>();

        all.add(new NASMRegister("rax", false, true, false));
        all.add(new NASMRegister("rcx", false, true, false));
        all.add(new NASMRegister("rdx", false, true, false));
        all.add(new NASMRegister("rbx", false, false, true));
        all.add(new NASMRegister("rsi", false, true, false));
        all.add(new NASMRegister("rdi", false, true, false));
        all.add(new NASMRegister("rsp", false, true, false));
        all.add(new NASMRegister("rbp", false, false, true));
        all.add(new NASMRegister("r8", true, true, false));
        all.add(new NASMRegister("r9", true, true, false));
        all.add(new NASMRegister("r10", true, true, false));
        all.add(new NASMRegister("r11", true, true, false));
        all.add(new NASMRegister("r12", true, false, true));
        all.add(new NASMRegister("r13", true, false, true));
        all.add(new NASMRegister("r14", true, false, true));
        all.add(new NASMRegister("r15", true, false, true));

        all.stream().filter(NASMRegister::isGeneral).forEach(general::add);
        all.stream().filter(NASMRegister::isCallerSave).forEach(callerSave::add);
        all.stream().filter(NASMRegister::isCalleeSave).forEach(calleeSave::add);

        allRegs = Collections.unmodifiableCollection(all);
        generalRegs = Collections.unmodifiableCollection(general);
        callerSaveRegs = Collections.unmodifiableList(callerSave);
        calleeSaveRegs = Collections.unmodifiableList(calleeSave);
    }
}
