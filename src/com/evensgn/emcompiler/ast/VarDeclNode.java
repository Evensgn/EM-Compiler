package com.evensgn.emcompiler.ast;

/**
 * @author Zhou Fan
 * @since 2018/4/1
 */
public class VarDeclNode extends DeclNode {
    private TypeNode type;
    private String name;
    private ExprNode init;

    public VarDeclNode(TypeNode type, String name, ExprNode init, Location location) {
        this.type = type;
        this.name = name;
        this.init = init;
        this.location = location;
    }

    public TypeNode getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public ExprNode getInit() {
        return init;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
