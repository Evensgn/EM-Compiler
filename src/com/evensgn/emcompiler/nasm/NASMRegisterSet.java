package com.evensgn.emcompiler.nasm;

import com.evensgn.emcompiler.ir.PhysicalRegister;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class NASMRegisterSet {
    public static final Collection<PhysicalRegister> allRegs, generalRegs, callerSaveRegs, calleeSaveRegs;
    public static final NASMRegister rax, rcx, rdx, rbx, rsi, rdi, rsp, rbp, r8, r9, r10, r11, r12, r13, r14, r15;

    static {
        List<NASMRegister> all = new ArrayList<>();
        List<NASMRegister> general = new ArrayList<>();
        List<NASMRegister> callerSave = new ArrayList<>();
        List<NASMRegister> calleeSave = new ArrayList<>();

        rax = new NASMRegister("rax", false, true, false);
        rcx = new NASMRegister("rcx", false, true, false);
        rdx = new NASMRegister("rdx", false, true, false);
        rbx = new NASMRegister("rbx", false, false, true);
        rsi = new NASMRegister("rsi", false, true, false);
        rdi = new NASMRegister("rdi", false, true, false);
        rsp = new NASMRegister("rsp", false, true, false);
        rbp = new NASMRegister("rbp", false, false, true);
        r8 = new NASMRegister("r8", true, true, false);
        r9 = new NASMRegister("r9", true, true, false);
        r10 = new NASMRegister("r10", true, true, false);
        r11 = new NASMRegister("r11", true, true, false);
        r12 = new NASMRegister("r12", true, false, true);
        r13 = new NASMRegister("r13", true, false, true);
        r14 = new NASMRegister("r14", true, false, true);
        r15 = new NASMRegister("r15", true, false, true);

        all.add(rax);
        all.add(rcx);
        all.add(rdx);
        all.add(rbx);
        all.add(rsi);
        all.add(rdi);
        all.add(rsp);
        all.add(rbp);
        all.add(r8);
        all.add(r9);
        all.add(r10);
        all.add(r11);
        all.add(r12);
        all.add(r13);
        all.add(r14);
        all.add(r15);

        all.stream().filter(NASMRegister::isGeneral).forEach(general::add);
        all.stream().filter(NASMRegister::isCallerSave).forEach(callerSave::add);
        all.stream().filter(NASMRegister::isCalleeSave).forEach(calleeSave::add);

        allRegs = Collections.unmodifiableCollection(all);
        generalRegs = Collections.unmodifiableCollection(general);
        callerSaveRegs = Collections.unmodifiableList(callerSave);
        calleeSaveRegs = Collections.unmodifiableList(calleeSave);
    }
}
