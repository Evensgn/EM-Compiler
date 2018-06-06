package com.evensgn.emcompiler.ir;

import com.evensgn.emcompiler.nasm.NASMRegisterSet;
import com.evensgn.emcompiler.scope.Scope;

import java.util.*;

public class IRRoot {
    private Map<String, IRFunction> funcs = new HashMap<>();
    private Map<String, IRFunction> builtInFuncs = new HashMap<>();
    private List<StaticData> staticDataList = new ArrayList<>();
    private Map<String, StaticString> staticStrs = new HashMap<>();
    private boolean hasDivShiftInst = false;
    private int maxNumFuncArgs = 3;
    private PhysicalRegister preg0, preg1;

    static public String irMemberFuncName(String className, String funcName) {
        return String.format("__member_%s_%s", className, funcName);
    }

    public IRRoot() {
        insertBuiltInFuncs();
    }

    private void insertBuiltInFuncs() {
        IRFunction func;

        func = new IRFunction(BUILTIN_STRING_CONCAT_FUNC_NAME, "__builtin_string_concat");
        func.getUsedPhysicalGeneralRegs().addAll(NASMRegisterSet.generalRegs);
        addBuiltInFunc(func);

        func = new IRFunction(BUILTIN_STRING_EQUAL_FUNC_NAME, "__builtin_string_equal");
        func.getUsedPhysicalGeneralRegs().addAll(NASMRegisterSet.generalRegs);
        addBuiltInFunc(func);

        func = new IRFunction(BUILTIN_STRING_INEQUAL_FUNC_NAME, "__builtin_string_inequal");
        func.getUsedPhysicalGeneralRegs().addAll(NASMRegisterSet.generalRegs);
        addBuiltInFunc(func);

        func = new IRFunction(BUILTIN_STRING_LESS_FUNC_NAME, "__builtin_string_less");
        func.getUsedPhysicalGeneralRegs().addAll(NASMRegisterSet.generalRegs);
        addBuiltInFunc(func);

        func = new IRFunction(BUILTIN_STRING_LESS_EQUAL_FUNC_NAME, "__builtin_string_less_equal");
        func.getUsedPhysicalGeneralRegs().addAll(NASMRegisterSet.generalRegs);
        addBuiltInFunc(func);

        func = new IRFunction(BUILTIN_PRINT_FUNC_NAME, "_Z5printPc");
        func.getUsedPhysicalGeneralRegs().addAll(NASMRegisterSet.generalRegs);
        addBuiltInFunc(func);

        func = new IRFunction(BUILTIN_PRINTLN_FUNC_NAME, "_Z7printlnPc");
        func.getUsedPhysicalGeneralRegs().addAll(NASMRegisterSet.generalRegs);
        addBuiltInFunc(func);

        func = new IRFunction(BUILTIN_PRINT_INT_FUNC_NAME, "_Z8printInti");
        func.getUsedPhysicalGeneralRegs().addAll(NASMRegisterSet.generalRegs);
        addBuiltInFunc(func);

        func = new IRFunction(BUILTIN_PRINTLN_INT_FUNC_NAME, "_Z10printlnInti");
        func.getUsedPhysicalGeneralRegs().addAll(NASMRegisterSet.generalRegs);
        addBuiltInFunc(func);

        func = new IRFunction(BUILTIN_GET_STRING_FUNC_NAME, "_Z9getStringv");
        func.getUsedPhysicalGeneralRegs().addAll(NASMRegisterSet.generalRegs);
        addBuiltInFunc(func);

        func = new IRFunction(BUILTIN_GET_INT_FUNC_NAME, "_Z6getIntv");
        func.getUsedPhysicalGeneralRegs().addAll(NASMRegisterSet.generalRegs);
        addBuiltInFunc(func);

        func = new IRFunction(BUILTIN_TO_STRING_FUNC_NAME, "_Z8toStringi");
        func.getUsedPhysicalGeneralRegs().addAll(NASMRegisterSet.generalRegs);
        addBuiltInFunc(func);

        func = new IRFunction(BUILTIN_STRING_SUBSTRING_FUNC_NAME, "_Z27__member___string_substringPcii");
        func.getUsedPhysicalGeneralRegs().addAll(NASMRegisterSet.generalRegs);
        addBuiltInFunc(func);

        func = new IRFunction(BUILTIN_STRING_PARSEINT_FUNC_NAME, "_Z26__member___string_parseIntPc");
        func.getUsedPhysicalGeneralRegs().addAll(NASMRegisterSet.generalRegs);
        addBuiltInFunc(func);

        func = new IRFunction(BUILTIN_STRING_ORD_FUNC_NAME, "_Z21__member___string_ordPci");
        func.getUsedPhysicalGeneralRegs().addAll(NASMRegisterSet.generalRegs);
        addBuiltInFunc(func);
    }

    public PhysicalRegister getPreg0() {
        return preg0;
    }

    public PhysicalRegister getPreg1() {
        return preg1;
    }

    public void setPreg0(PhysicalRegister preg0) {
        this.preg0 = preg0;
    }

    public void setPreg1(PhysicalRegister preg1) {
        this.preg1 = preg1;
    }

    public void addFunc(IRFunction func) {
        funcs.put(func.getName(), func);
    }

    public IRFunction getFunc(String name) {
        return funcs.get(name);
    }

    public void addBuiltInFunc(IRFunction builtInFunc) {
        builtInFuncs.put(builtInFunc.getName(), builtInFunc);
    }

    public IRFunction getBuiltInFunc(String name) {
        return builtInFuncs.get(name);
    }

    public void addStaticStr(StaticString staticStr) {
        staticStrs.put(staticStr.getValue(), staticStr);
    }

    public StaticString getStaticStr(String str) {
        return staticStrs.get(str);
    }

    public Map<String, IRFunction> getFuncs() {
        return funcs;
    }

    public Map<String, IRFunction> getBuiltInFuncs() {
        return builtInFuncs;
    }

    public List<StaticData> getStaticDataList() {
        return staticDataList;
    }

    public Map<String, StaticString> getStaticStrs() {
        return staticStrs;
    }

    public void updateCalleeSet() {
        Set<IRFunction> recursiveCalleeSet = new HashSet<>();
        for (IRFunction irFunction : funcs.values()) {
            irFunction.recursiveCalleeSet.clear();
        }
        boolean changed = true;
        while (changed) {
            changed = false;
            for (IRFunction irFunction : funcs.values()) {
                recursiveCalleeSet.clear();
                recursiveCalleeSet.addAll(irFunction.calleeSet);
                for (IRFunction calleeFunction : irFunction.calleeSet) {
                    recursiveCalleeSet.addAll(calleeFunction.recursiveCalleeSet);
                }
                if (!recursiveCalleeSet.equals(irFunction.recursiveCalleeSet)) {
                    irFunction.recursiveCalleeSet.clear();
                    irFunction.recursiveCalleeSet.addAll(recursiveCalleeSet);
                    changed = true;
                }
            }
        }
    }

    static public final String BUILTIN_STRING_CONCAT_FUNC_NAME = "__builtin_string_concat";
    static public final String BUILTIN_STRING_EQUAL_FUNC_NAME = "__builtin_string_equal";
    static public final String BUILTIN_STRING_INEQUAL_FUNC_NAME = "__builtin_string_inequal";
    static public final String BUILTIN_STRING_LESS_FUNC_NAME = "__builtin_string_less";
    static public final String BUILTIN_STRING_LESS_EQUAL_FUNC_NAME = "__builtin_string_less_equal";

    static public final String BUILTIN_PRINT_FUNC_NAME = "print";
    static public final String BUILTIN_PRINTLN_FUNC_NAME = "println";
    static public final String BUILTIN_PRINT_INT_FUNC_NAME = "printInt";
    static public final String BUILTIN_PRINTLN_INT_FUNC_NAME = "printlnInt";
    static public final String BUILTIN_GET_STRING_FUNC_NAME = "getString";
    static public final String BUILTIN_GET_INT_FUNC_NAME = "getInt";
    static public final String BUILTIN_TO_STRING_FUNC_NAME = "toString";
    static public final String BUILTIN_STRING_LENGTH_FUNC_NAME = "__member_" + Scope.STRING_CLASS_NAME + "_" + "length";
    static public final String BUILTIN_STRING_SUBSTRING_FUNC_NAME = "__member_" + Scope.STRING_CLASS_NAME + "_" + "substring";
    static public final String BUILTIN_STRING_PARSEINT_FUNC_NAME = "__member_" + Scope.STRING_CLASS_NAME + "_" + "parseInt";
    static public final String BUILTIN_STRING_ORD_FUNC_NAME = "__member_" +  Scope.STRING_CLASS_NAME+ "_" + "ord";
    static public final String BUILTIN_ARRAY_SIZE_FUNC_NAME = "__member_" + Scope.ARRAY_CLASS_NAME + "_" + "size";

    public void addStaticData(StaticData staticData) {
        staticDataList.add(staticData);
    }

    public void removeFunc(String funcName) {
        funcs.remove(funcName);
    }

    public void accept(IRVisitor visitor) {
        visitor.visit(this);
    }

    public boolean isHasDivShiftInst() {
        return hasDivShiftInst;
    }

    public int getMaxNumFuncArgs() {
        return maxNumFuncArgs;
    }

    public void setHasDivShiftInst(boolean hasDivShiftInst) {
        this.hasDivShiftInst = hasDivShiftInst;
    }

    public void setMaxNumFuncArgs(int maxNumFuncArgs) {
        this.maxNumFuncArgs = maxNumFuncArgs;
    }
}