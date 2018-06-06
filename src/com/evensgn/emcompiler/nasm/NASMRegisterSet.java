package com.evensgn.emcompiler.nasm;

import com.evensgn.emcompiler.ir.PhysicalRegister;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class NASMRegisterSet {
    public static final Collection<PhysicalRegister> allRegs, generalRegs, callerSaveRegs, calleeSaveRegs;
    public static final NASMRegister rax, rcx, rdx, rbx, rsi, rdi, rsp, rbp, r8, r9, r10, r11, r12, r13, r14, r15;
    public static final List<PhysicalRegister> arg6;

    static {
        List<NASMRegister> all = new ArrayList<>();
        List<NASMRegister> general = new ArrayList<>();
        List<NASMRegister> callerSave = new ArrayList<>();
        List<NASMRegister> calleeSave = new ArrayList<>();

        rax = new NASMRegister("rax", false, true, false, -1);
        rcx = new NASMRegister("rcx", false, true, false, 3);
        rdx = new NASMRegister("rdx", false, true, false, 2);
        rbx = new NASMRegister("rbx", false, false, true, -1);
        rsi = new NASMRegister("rsi", false, true, false, 1);
        rdi = new NASMRegister("rdi", false, true, false, 0);
        rsp = new NASMRegister("rsp", false, true, false, -1);
        rbp = new NASMRegister("rbp", false, false, true, -1);
        // r8 and r9 are actually general registers
        r8 = new NASMRegister("r8", true, true, false, 4);
        r9 = new NASMRegister("r9", true, true, false, 5);
        r10 = new NASMRegister("r10", true, true, false, -1);
        r11 = new NASMRegister("r11", true, true, false, -1);
        r12 = new NASMRegister("r12", true, false, true, -1);
        r13 = new NASMRegister("r13", true, false, true, -1);
        r14 = new NASMRegister("r14", true, false, true, -1);
        r15 = new NASMRegister("r15", true, false, true, -1);

        arg6 = new ArrayList<>();
        arg6.add(rdi);
        arg6.add(rsi);
        arg6.add(rdx);
        arg6.add(rcx);
        arg6.add(r8);
        arg6.add(r9);

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
