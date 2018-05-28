package com.evensgn.emcompiler.ir;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public void addStaticData(StaticData staticData) {
        staticDataList.add(staticData);
    }
}