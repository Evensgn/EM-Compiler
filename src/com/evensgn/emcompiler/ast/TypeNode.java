package com.evensgn.emcompiler.ast;

import com.evensgn.emcompiler.type.Type;

/**
 * @author Zhou Fan
 * @since 2018/4/1
 */
public class TypeNode extends Node {
    private Type type;

    public TypeNode(Type type, Location location) {
        this.type = type;
        this.location = location;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Type getType() {
        return type;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
