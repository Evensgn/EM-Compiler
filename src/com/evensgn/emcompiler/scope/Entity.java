package com.evensgn.emcompiler.scope;

import com.evensgn.emcompiler.type.Type;

abstract public class Entity {
    private String name;
    private Type.HyperTypes type;

    public Entity(String name, Type.HyperTypes type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public Type.HyperTypes getType() {
        return type;
    }
}
