package com.evensgn.emcompiler.ir;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IRRoot {
    private Map<String, IRFunction> funcs = new HashMap<>();
    private List<StaticData> staticDataList = new ArrayList<>();

    public void insertFunc(IRFunction func) {
        funcs.put(func.getName(), func);
    }

    public void addStaticData(StaticData staticData) {
        staticDataList.add(staticData);
    }
}