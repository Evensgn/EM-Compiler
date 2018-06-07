package com.evensgn.emcompiler.frontend;

import com.evensgn.emcompiler.ast.*;
import com.evensgn.emcompiler.scope.ClassEntity;
import com.evensgn.emcompiler.scope.FuncEntity;
import com.evensgn.emcompiler.scope.Scope;
import com.evensgn.emcompiler.scope.VarEntity;
import com.evensgn.emcompiler.type.ArrayType;

public class InfluencePreScanner extends BaseScopeScanner {
    /*private Scope globalScope, currentScope;
    private FuncDeclNode currentFunc;
    private boolean changed;

    public InfluencePreScanner(Scope globalScope) {
        this.globalScope = globalScope;
    }

    @Override
    public void visit(ProgramNode node) {
        currentScope = globalScope;
        changed = true;
        while (changed) {
            changed = false;
            for (DeclNode decl : node.getDecls()) {
                if (decl instanceof FuncDeclNode) {
                    decl.accept(this);
                } else if (decl instanceof ClassDeclNode) {
                    ClassEntity entity = (ClassEntity) currentScope.get(Scope.classKey(decl.getName()));
                    currentScope = entity.getScope();
                    for (FuncDeclNode memberFunc : ((ClassDeclNode) decl).getFuncMember()) {
                        memberFunc.accept(this);
                    }
                    currentScope = currentScope.getParent();
                }
            }
        }
    }

    @Override
    public void visit(VarDeclListNode node) {
        super.visit(node);
    }

    @Override
    public void visit(FuncDeclNode node) {
        boolean wasOutInfluence = node.isOutInfluence();
        currentFunc = node;
        node.getBody().accept(this);
        currentFunc = null;
        if (node.isOutInfluence() && !wasOutInfluence) {
            FuncEntity funcEntity = (FuncEntity) currentScope.get(Scope.funcKey(node.getName()));
            funcEntity.setOutInfluence(true);
            changed = true;
        }
    }

    @Override
    public void visit(ClassDeclNode node) {
        super.visit(node);
    }

    @Override
    public void visit(VarDeclNode node) {
        if (node.getInit() != null) {
            node.getInit().accept(this);
            node.setOutInfluence(node.getInit().isOutInfluence());
        }
    }

    @Override
    public void visit(BlockStmtNode node) {
        currentScope = node.getScope();
        for (Node stmtOrDecl : node.getStmtsAndVarDecls()) {
            stmtOrDecl.accept(this);
            if (stmtOrDecl.isOutInfluence()) node.setOutInfluence(true);
        }
        if (node.isOutInfluence()) currentFunc.setOutInfluence(true);
        currentScope = currentScope.getParent();
    }

    @Override
    public void visit(ExprStmtNode node) {
        node.getExpr().accept(this);
        node.setOutInfluence(node.getExpr().isOutInfluence());
    }

    @Override
    public void visit(CondStmtNode node) {
        node.getCond().accept(this);
        if (node.getCond().isOutInfluence()) node.setOutInfluence(true);
        if (node.getElseStmt() != null) {
            node.getElseStmt().accept(this);
            if (node.getElseStmt().isOutInfluence()) node.setOutInfluence(true);
        }
        if (node.getThenStmt() != null) {
            node.getThenStmt().accept(this);
            if (node.getThenStmt().isOutInfluence()) node.setOutInfluence(true);
        }
    }

    @Override
    public void visit(WhileStmtNode node) {
        node.getCond().accept(this);
        if (node.getCond().isOutInfluence()) node.setOutInfluence(true);
        if (node.getStmt() != null) {
            node.getStmt().accept(this);
            if (node.getStmt().isOutInfluence()) node.setOutInfluence(true);
        }
    }

    @Override
    public void visit(ForStmtNode node) {
        if (node.getInit() != null) {
            node.getInit().accept(this);
            if (node.getInit().isOutInfluence()) node.setOutInfluence(true);
        }
        if (node.getCond() != null) {
            node.getCond().accept(this);
            if (node.getCond().isOutInfluence()) node.setOutInfluence(true);
        }
        if (node.getStep() != null) {
            node.getStep().accept(this);
            if (node.getStep().isOutInfluence()) node.setOutInfluence(true);
        }
        if (node.getStmt() != null) {
            node.getStmt().accept(this);
            if (node.getStmt().isOutInfluence()) node.setOutInfluence(true);
        }
    }

    @Override
    public void visit(ContinueStmtNode node) {
    }

    @Override
    public void visit(BreakStmtNode node) {
    }

    @Override
    public void visit(ReturnStmtNode node) {
        if (node.getExpr() != null) {
            node.getExpr().accept(this);
            if (node.getExpr().isOutInfluence()) node.setOutInfluence(true);
        }
    }

    @Override
    public void visit(SuffixExprNode node) {
        node.setOutInfluence(true);
        node.getExpr().accept(this);
    }

    @Override
    public void visit(FuncCallExprNode node) {
        node.getFunc().accept(this);
        if (node.getFunc().isOutInfluence()) node.setOutInfluence(true);
        for (ExprNode arg : node.getArgs()) {
            arg.accept(this);
            if (arg.isOutInfluence()) node.setOutInfluence(true);
        }
    }

    @Override
    public void visit(SubscriptExprNode node) {
        node.getArr().accept(this);
        node.getSub().accept(this);
    }

    @Override
    public void visit(MemberAccessExprNode node) {
        node.getExpr().accept(this);
    }

    @Override
    public void visit(PrefixExprNode node) {
        node.getExpr().accept(this);
    }

    @Override
    public void visit(NewExprNode node) {
        if (node.getDims() != null) {
            for (ExprNode dim : node.getDims()) {
                dim.accept(this);
            }
        }
    }

    @Override
    public void visit(BinaryExprNode node) {
        node.getLhs().accept(this);
        node.getRhs().accept(this);
    }

    @Override
    public void visit(AssignExprNode node) {
        if (node.getRhs().getType() instanceof ArrayType && !(node.getRhs() instanceof NewExprNode)) {
            node.getLhs().accept(this);
            node.getRhs().accept(this);
            return;
        }
        inDefine = true;
        node.getLhs().accept(this);
        inDefine = false;
        node.getRhs().accept(this);
    }

    @Override
    public void visit(IdentifierExprNode node) {
        VarEntity varEntity = (VarEntity) currentScope.get(Scope.varKey(node.getIdentifier()));
        if (varEntity != null) {
            if (varEntity.getType() instanceof ArrayType || varEntity.isGlobal()) {
                if (inDefine) unUsedStaticSet.add(varEntity);
                else {
                    usedStaticSet.add(varEntity);
                }
            }
        }
    }

    @Override
    public void visit(ThisExprNode node) {
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
    }*/
}