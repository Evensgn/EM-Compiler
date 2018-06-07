package com.evensgn.emcompiler.frontend;

import com.evensgn.emcompiler.ast.*;
import com.evensgn.emcompiler.ir.IRFunction;
import com.evensgn.emcompiler.scope.*;
import com.evensgn.emcompiler.type.ArrayType;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class StaticUsagePreScanner extends BaseScopeScanner {
    private Set<VarEntity> usedStaticSet = new HashSet<>(), unUsedStaticSet = new HashSet<>();
    private Scope globalScope, currentScope;
    private boolean inDefine = false;

    public StaticUsagePreScanner(Scope globalScope) {
        this.globalScope = globalScope;
    }

    @Override
    public void visit(ProgramNode node) {
        currentScope = globalScope;
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
            } else if (decl instanceof VarDeclNode) {
                decl.accept(this);
            }
        }
        for (VarEntity varEntity : unUsedStaticSet) {
            //System.err.println("unUsedStatic? " + varEntity.getName());
            if (usedStaticSet.contains(varEntity)) continue;
            varEntity.setUnUsed(true);
            //System.err.println("unUsedStatic: " + varEntity.getName());
        }
    }

    @Override
    public void visit(VarDeclListNode node) {
        super.visit(node);
    }

    @Override
    public void visit(FuncDeclNode node) {
        node.getBody().accept(this);
    }

    @Override
    public void visit(ClassDeclNode node) {
        super.visit(node);
    }

    @Override
    public void visit(VarDeclNode node) {
        if (node.getInit() != null) {
            VarEntity varEntity = (VarEntity) currentScope.get(Scope.varKey(node.getName()));
            if (varEntity.getType() instanceof ArrayType || varEntity.isGlobal()) {
                unUsedStaticSet.add(varEntity);
            }
            node.getInit().accept(this);
        }
    }

    @Override
    public void visit(BlockStmtNode node) {
        currentScope = node.getScope();
        for (Node stmtOrDecl : node.getStmtsAndVarDecls()) {
            stmtOrDecl.accept(this);
        }
        currentScope = currentScope.getParent();
    }

    @Override
    public void visit(ExprStmtNode node) {
        node.getExpr().accept(this);
    }

    @Override
    public void visit(CondStmtNode node) {
        node.getCond().accept(this);
        if (node.getElseStmt() != null) node.getElseStmt().accept(this);
        if (node.getThenStmt() != null) node.getThenStmt().accept(this);
    }

    @Override
    public void visit(WhileStmtNode node) {
        node.getCond().accept(this);
        if (node.getStmt() != null) node.getStmt().accept(this);
    }

    @Override
    public void visit(ForStmtNode node) {
        if (node.getInit() != null) node.getInit().accept(this);
        if (node.getCond() != null) node.getCond().accept(this);
        if (node.getStep() != null) node.getStep().accept(this);
        if (node.getStmt() != null) node.getStmt().accept(this);
    }

    @Override
    public void visit(ContinueStmtNode node) {
    }

    @Override
    public void visit(BreakStmtNode node) {
    }

    @Override
    public void visit(ReturnStmtNode node) {
        if (node.getExpr() != null) node.getExpr().accept(this);
    }

    @Override
    public void visit(SuffixExprNode node) {
        node.getExpr().accept(this);
    }

    @Override
    public void visit(FuncCallExprNode node) {
        node.getFunc().accept(this);
        for (ExprNode arg : node.getArgs()) {
            arg.accept(this);
        }
    }

    @Override
    public void visit(SubscriptExprNode node) {
        if (inDefine) {
            node.getArr().accept(this);
            inDefine = false;
            node.getSub().accept(this);
            inDefine = true;
        } else {
            node.getArr().accept(this);
            node.getSub().accept(this);
        }
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
    }
}
