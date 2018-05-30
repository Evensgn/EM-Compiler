package com.evensgn.emcompiler.ir;

import java.util.*;

public class IRRoot {
    private Map<String, IRFunction> funcs = new HashMap<>();
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