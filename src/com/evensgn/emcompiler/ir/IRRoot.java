package com.evensgn.emcompiler.ir;

import com.evensgn.emcompiler.scope.Scope;

import java.util.*;

public class IRRoot {
    private Map<String, IRFunction> funcs = new HashMap<>();
    private Map<String, IRFunction> builtInFuncs = new HashMap<>();
    private List<StaticData> staticDataList = new ArrayList<>();
    private Map<String, StaticString> staticStrs = new HashMap<>();
    static public String irMemberFuncName(String className, String funcName) {
        return String.format("__member_%s_%s", className, funcName);
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
}