package com.evensgn.emcompiler.frontend;

import com.evensgn.emcompiler.ast.*;
import com.evensgn.emcompiler.ir.BasicBlock;
import com.evensgn.emcompiler.ir.IRFunction;
import com.evensgn.emcompiler.ir.IRRoot;
import com.evensgn.emcompiler.scope.FuncEntity;
import com.evensgn.emcompiler.scope.Scope;
import com.evensgn.emcompiler.utils.CompilerError;

public class IRBuilder extends BaseScopeScanner {
    private IRRoot ir = new IRRoot();
    private IRFunction currentFunc = null;
    private BasicBlock currentBB = null;
    private Scope globalScope, currentScope;

    public IRRoot getIR() {
        return ir;
    }

    public IRBuilder(Scope globalScope) {
        this.globalScope = globalScope;
    }

    @Override
    public void visit(ProgramNode node) {
        currentScope = globalScope;
        for (DeclNode decl : node.getDecls()) {
            if (decl instanceof VarDeclNode) {
                decl.accept(this);
            } else if (decl instanceof ClassDeclNode) {
                decl.accept(this);
            } else if (decl instanceof FuncDeclNode) {
                decl.accept(this);
            } else {
                throw new CompilerError(decl.location(), "Invalid declaration node type");
            }
        }
    }

    @Override
    public void visit(FuncDeclNode node) {
        FuncEntity funcEntity = (FuncEntity) currentScope.get(Scope.funcKey(node.getName()));
        currentFunc = new IRFunction(funcEntity);
        node.getBody().accept(this);
    }

    @Override
    public void visit(ClassDeclNode node) {
        super.visit(node);
    }

    @Override
    public void visit(VarDeclNode node) {
        super.visit(node);
    }

    @Override
    public void visit(BlockStmtNode node) {
        super.visit(node);
    }

    @Override
    public void visit(ExprStmtNode node) {
        super.visit(node);
    }

    @Override
    public void visit(CondStmtNode node) {
        super.visit(node);
    }

    @Override
    public void visit(WhileStmtNode node) {
        super.visit(node);
    }

    @Override
    public void visit(ForStmtNode node) {
        super.visit(node);
    }

    @Override
    public void visit(ContinueStmtNode node) {
        super.visit(node);
    }

    @Override
    public void visit(BreakStmtNode node) {
        super.visit(node);
    }

    @Override
    public void visit(ReturnStmtNode node) {
        super.visit(node);
    }

    @Override
    public void visit(SuffixExprNode node) {
        super.visit(node);
    }

    @Override
    public void visit(FuncCallExprNode node) {
        super.visit(node);
    }

    @Override
    public void visit(SubscriptExprNode node) {
        super.visit(node);
    }

    @Override
    public void visit(MemberAccessExprNode node) {
        super.visit(node);
    }

    @Override
    public void visit(PrefixExprNode node) {
        super.visit(node);
    }

    @Override
    public void visit(NewExprNode node) {
        super.visit(node);
    }

    @Override
    public void visit(BinaryExprNode node) {
        super.visit(node);
    }

    @Override
    public void visit(AssignExprNode node) {
        super.visit(node);
    }

    @Override
    public void visit(IdentifierExprNode node) {
        super.visit(node);
    }

    @Override
    public void visit(ThisExprNode node) {
        super.visit(node);
    }

    @Override
    public void visit(IntConstExprNode node) {
        super.visit(node);
    }

    @Override
    public void visit(StringConstExprNode node) {
        super.visit(node);
    }

    @Override
    public void visit(BoolConstExprNode node) {
        super.visit(node);
    }

    @Override
    public void visit(NullExprNode node) {
        super.visit(node);
    }

    @Override
    public void visit(TypeNode node) {
        super.visit(node);
    }
}
