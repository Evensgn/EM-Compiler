package com.evensgn.emcompiler.ir;

import java.util.HashMap;
import java.util.Map;

public class IRRoot {
    private Map<String, IRFunction> funcs = new HashMap<>();

    public void insertFunc(IRFunction func) {
        funcs.put(func.getName(), func);
    }
}
