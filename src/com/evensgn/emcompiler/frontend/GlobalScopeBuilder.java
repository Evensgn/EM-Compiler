package com.evensgn.emcompiler.frontend;

import com.evensgn.emcompiler.ast.*;
import com.evensgn.emcompiler.scope.ClassEntity;
import com.evensgn.emcompiler.scope.Entity;
import com.evensgn.emcompiler.scope.FuncEntity;
import com.evensgn.emcompiler.scope.Scope;
import com.evensgn.emcompiler.type.FunctionType;
import com.evensgn.emcompiler.utils.CompilerError;
import com.evensgn.emcompiler.utils.SemanticError;
import com.evensgn.emcompiler.utils.SyntaxError;

public class GlobalScopeBuilder implements ASTVisitor {
    private Scope scope = new Scope();

    public Scope getScope() {
        return scope;
    }

    private Entity newEntity;

    @Override
    public void visit(ProgramNode node) {
        if (node.getDecls().isEmpty()) return;
        String name, key;
        Entity entity;
        for (DeclNode decl : node.getDecls()) {
            name = decl.getName();
            if (decl instanceof VarDeclNode) key = Scope.varKey(name);
            else if (decl instanceof ClassDeclNode) key = Scope.classKey(name);
            else if (decl instanceof FuncDeclNode) key = Scope.funcKey(name);
            else throw new CompilerError(decl.location(), "Unknown DeclNode class");
            decl.accept(this);
            entity = newEntity;
            if (!scope.put(key, entity)) throw new SemanticError(decl.location(), String.format("Symbol name \"%s\" already defined", name));
        }
    }

    // no use
    @Override
    public void visit(VarDeclListNode node) {
    }

    @Override
    public void visit(FuncDeclNode node) {
        newEntity = new FuncEntity(node);
    }

    @Override
    public void visit(ClassDeclNode node) {
        newEntity = new ClassEntity(node);
    }

    @Override
    public void visit(VarDeclNode node) {

    }

    @Override
    public void visit(BlockStmtNode node) {

    }

    @Override
    public void visit(ExprStmtNode node) {

    }

    @Override
    public void visit(CondStmtNode node) {

    }

    @Override
    public void visit(WhileStmtNode node) {

    }

    @Override
    public void visit(ForStmtNode node) {

    }

    @Override
    public void visit(ContinueStmtNode node) {

    }

    @Override
    public void visit(BreakStmtNode node) {

    }

    @Override
    public void visit(ReturnStmtNode node) {

    }

    @Override
    public void visit(SuffixExprNode node) {

    }

    @Override
    public void visit(FuncCallExprNode node) {

    }

    @Override
    public void visit(SubscriptExprNode node) {

    }

    @Override
    public void visit(MemberAccessExprNode node) {

    }

    @Override
    public void visit(PrefixExprNode node) {

    }

    @Override
    public void visit(NewExprNode node) {

    }

    @Override
    public void visit(BinaryExprNode node) {

    }

    @Override
    public void visit(AssignExprNode node) {

    }

    @Override
    public void visit(IdentifierExprNode node) {

    }

    @Override
    public void visit(IntConstExprNode node) {

    }

    @Override
    public void visit(StringConstExprNode node) {

    }

    @Override
    public void visit(BoolConstExprNode node) {

    }

    @Override
    public void visit(NullExprNode node) {

    }

    @Override
    public void visit(TypeNode node) {

    }
}
