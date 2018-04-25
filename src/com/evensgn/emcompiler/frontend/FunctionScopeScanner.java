package com.evensgn.emcompiler.frontend;

import com.evensgn.emcompiler.ast.*;
import com.evensgn.emcompiler.scope.*;
import com.evensgn.emcompiler.type.*;
import com.evensgn.emcompiler.utils.CompilerError;
import com.evensgn.emcompiler.utils.SemanticError;

public class FunctionScopeScanner implements ASTVisitor {
    private Scope globalScope, currentScope;
    private int inLoop;
    private Type currentReturnType;

    public FunctionScopeScanner(Scope globalScope) {
        this.globalScope = globalScope;
    }

    @Override
    public void visit(ProgramNode node) {
        currentScope = globalScope;
        inLoop = 0;
        for (DeclNode decl : node.getDecls()) {
            if (decl instanceof VarDeclNode) {
                decl.accept(this);
            }
            else if (decl instanceof ClassDeclNode) {
                decl.accept(this);
            }
            else if (decl instanceof FuncDeclNode) {
                decl.accept(this);
            }
            else throw new CompilerError(decl.location(), "Invalid declaration node type");
        }
    }

    @Override
    public void visit(VarDeclListNode node) {

    }

    @Override
    public void visit(VarDeclNode node) {
        VarEntity entity = new VarEntity(node.getName(), node.getType().getType());
        currentScope.putCheck(node.location(), node.getName(), Scope.varKey(node.getName()), entity);
    }

    @Override
    public void visit(FuncDeclNode node) {
        FuncEntity entity = (FuncEntity) currentScope.getCheck(node.location(), node.getName(), Scope.funcKey(node.getName()));
        currentReturnType = entity.getReturnType();
        node.getBody().initScope(currentScope);
        String paraName;
        for (VarEntity paraEntity : entity.getParameters()) {
            paraName = paraEntity.getName();
            node.getBody().getScope().putCheck(paraName, Scope.varKey(paraName), paraEntity);
        }
        node.getBody().accept(this);
    }

    @Override
    public void visit(ClassDeclNode node) {
        ClassEntity entity = (ClassEntity) currentScope.getCheck(node.location(), node.getName(), Scope.classKey(node.getName()));
        currentScope = entity.getScope();
        for (FuncDeclNode funcDecl : node.getFuncMember()) {
            funcDecl.accept(this);
        }
        currentScope = currentScope.getParent(); // should be globalScope
    }

    @Override
    public void visit(BlockStmtNode node) {
        currentScope = node.getScope();
        String name, key;
        VarEntity varEntity;
        for (Node stmtOrVarDecl : node.getStmtsAndVarDecls()) {
            if (stmtOrVarDecl instanceof StmtNode) {
                if (stmtOrVarDecl instanceof BlockStmtNode) {
                    ((BlockStmtNode) stmtOrVarDecl).initScope(currentScope);
                }
                stmtOrVarDecl.accept(this);
            }
            else if (stmtOrVarDecl instanceof VarDeclNode) {
                name = ((VarDeclNode) stmtOrVarDecl).getName();
                key = Scope.varKey(name);
                varEntity = new VarEntity((VarDeclNode) stmtOrVarDecl);
                currentScope.putCheck(stmtOrVarDecl.location(), name, key, varEntity);
            }
            else throw new CompilerError(stmtOrVarDecl.location(), "Invalid node type in block statement node");
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
        if (!(node.getCond().getType() instanceof BoolType))
            throw new SemanticError(node.getCond().location(), "Condition expression of condition statement should have type \"bool\"");
        if (node.getThenStmt() != null) {
            if (node.getThenStmt() instanceof BlockStmtNode)
                ((BlockStmtNode) node.getThenStmt()).initScope(currentScope);
            node.getThenStmt().accept(this);
        }
        if (node.getElseStmt() != null) {
            if (node.getElseStmt() instanceof BlockStmtNode)
                ((BlockStmtNode) node.getElseStmt()).initScope(currentScope);
            node.getElseStmt().accept(this);
        }
    }

    @Override
    public void visit(WhileStmtNode node) {
        ++inLoop;
        node.getCond().accept(this);
        if (!(node.getCond().getType() instanceof BoolType))
            throw new SemanticError(node.getCond().location(), "Condition expression of while loop statement should have type \"bool\"");
        if (node.getStmt() != null) {
            if (node.getStmt() instanceof BlockStmtNode)
                ((BlockStmtNode) node.getStmt()).initScope(currentScope);
            node.getStmt().accept(this);
        }
        --inLoop;
    }

    @Override
    public void visit(ForStmtNode node) {
        ++inLoop;
        if (node.getInit() != null) node.getInit().accept(this);
        if (node.getCond() != null) {
            node.getCond().accept(this);
            if (!(node.getCond().getType() instanceof BoolType))
                throw new SemanticError(node.getCond().location(), "Condition expression of for loop statement should have type \"bool\"");
        }
        if (node.getStep() != null) node.getStep().accept(this);
        if (node.getStmt() != null) {
            if (node.getStmt() instanceof BlockStmtNode)
                ((BlockStmtNode) node.getStmt()).initScope(currentScope);
            node.getStmt().accept(this);
        }
        --inLoop;
    }

    @Override
    public void visit(ContinueStmtNode node) {
        if (inLoop <= 0) throw new SemanticError(node.location(), "Continue statement cannot be used outside of loop statement");
    }

    @Override
    public void visit(BreakStmtNode node) {
        if (inLoop <= 0) throw new SemanticError(node.location(), "Break statement cannot be used outside of loop statement");
    }

    @Override
    public void visit(ReturnStmtNode node) {
        boolean invalidReturnValueType = false;
        if (node.getExpr() == null) {
            if (!(currentReturnType == null || currentReturnType instanceof VoidType))
                invalidReturnValueType = true;
        }
        else {
            node.getExpr().accept(this);
            if (!(node.getExpr().getType().equals(currentReturnType)))
                invalidReturnValueType = true;
        }
        if (invalidReturnValueType) {
            if (currentReturnType == null)
                throw new SemanticError(node.location(), "Return statement should have no return value");
            else
                throw new SemanticError(node.location(), String.format("Return statement should have return value of type \"%s\"", currentReturnType.toString()));
        }
    }

    @Override
    public void visit(SuffixExprNode node) {
        node.getExpr().accept(this);
        if (!(node.getExpr().getType() instanceof IntType))
            throw new SemanticError(node.location(), String.format("Operator \"%s\" cannot be applied to type \"%s\"", node.getOp().toString(), node.getExpr().getType().toString()));
        if (!(node.getExpr().isLeftValue()))
            throw new SemanticError(node.location(), String.format("Operator \"%s\" cannot be applied to right value", node.getOp().toString()));
        node.setType(IntType.getInstance());
        node.setLeftValue(false);
    }

    @Override
    public void visit(FuncCallExprNode node) {
        node.getFunc().accept(this);
        if (!(node.getFunc().getType() instanceof FunctionType))
            throw new SemanticError(node.getFunc().location(), String.format("Type \"%s\" is not callable", node.getFunc().getType().toString()));
        String funcName, funcKey;
        funcName = ((FunctionType) node.getFunc().getType()).getName();
        funcKey = Scope.funcKey(funcName);
        FuncEntity funcEntity = (FuncEntity) currentScope.getCheck(node.getFunc().location(), funcName, funcKey);
        int paraNum = funcEntity.getParameters().size();
        if (paraNum != node.getArgs().size())
            throw new SemanticError(node.location(), String.format("Function call has inconsistent number of arguments, expected %d but got %d", paraNum, node.getArgs().size()));
        for (int i = 0; i < paraNum; ++i) {
            node.getArgs().get(i).accept(this);
            if (!funcEntity.getParameters().get(i).getType().equals(node.getArgs().get(i).getType())) {
                throw new SemanticError(
                    node.getArgs().get(i).location(),
                    String.format(
                        "Function call has inconsistent type of arguments, expected %s but got %s",
                        funcEntity.getParameters().get(i).getType().toString(),
                        node.getArgs().get(i).getType().toString()
                    )
                );
            }
        }
        node.setType(funcEntity.getReturnType());
        node.setLeftValue(false);
    }

    @Override
    public void visit(SubscriptExprNode node) {
        node.getArr().accept(this);
        if (!(node.getArr().getType() instanceof ArrayType))
            throw new SemanticError(node.getArr().location(), String.format("Type \"%s\" is not subscriptable", node.getArr().getType().toString()));

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
