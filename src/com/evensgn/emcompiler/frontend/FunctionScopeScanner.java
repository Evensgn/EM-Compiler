package com.evensgn.emcompiler.frontend;

import com.evensgn.emcompiler.ast.*;
import com.evensgn.emcompiler.scope.*;
import com.evensgn.emcompiler.type.*;
import com.evensgn.emcompiler.utils.CompilerError;
import com.evensgn.emcompiler.utils.SemanticError;

public class FunctionScopeScanner extends BaseScopeScanner {
    private Scope globalScope, currentScope;
    private int inLoop;
    private Type currentReturnType, currentClassType;
    private FuncEntity currentFuncEntity;

    public FunctionScopeScanner(Scope globalScope) {
        this.globalScope = globalScope;
    }

    public Scope getGlobalScope() {
        return globalScope;
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
    public void visit(VarDeclNode node) {
        if (node.getType().getType() instanceof ClassType) {
            String className = ((ClassType) node.getType().getType()).getName();
            currentScope.assertContainsExactKey(node.location(), className, Scope.classKey(className));
        }
        checkVarDeclInit(node);
        VarEntity entity = new VarEntity(node.getName(), node.getType().getType());
        currentScope.putCheck(node.location(), node.getName(), Scope.varKey(node.getName()), entity);
    }

    @Override
    public void visit(FuncDeclNode node) {
        FuncEntity entity = (FuncEntity) currentScope.getCheck(node.location(), node.getName(), Scope.funcKey(node.getName()));
        if (entity.getReturnType() instanceof ClassType)
            currentScope.assertContainsExactKey(node.getReturnType().location(), ((ClassType) entity.getReturnType()).getName(), Scope.classKey(((ClassType) entity.getReturnType()).getName()));
        currentReturnType = entity.getReturnType();
        node.getBody().initScope(currentScope);
        currentScope = node.getBody().getScope();
        if (currentClassType != null) {
            String key = Scope.varKey(Scope.THIS_PARA_NAME);
            currentScope.putCheck(node.location(), Scope.THIS_PARA_NAME, key, new VarEntity(Scope.THIS_PARA_NAME, currentClassType));
            if (node.isConstruct() && !(node.getName().equals(((ClassType) currentClassType).getName())))
                throw new SemanticError(node.location(), String.format("Function \"%s\" should have a return type", node.getName()));
        }
        for (VarDeclNode paraDecl : node.getParameterList()) {
            paraDecl.accept(this);
        }
        currentScope = currentScope.getParent();
        node.getBody().accept(this);
    }

    @Override
    public void visit(ClassDeclNode node) {
        ClassEntity entity = (ClassEntity) currentScope.getCheck(node.location(), node.getName(), Scope.classKey(node.getName()));
        currentScope = entity.getScope();
        currentClassType = entity.getType();
        for (FuncDeclNode funcDecl : node.getFuncMember()) {
            funcDecl.accept(this);
        }
        currentClassType = null;
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
                stmtOrVarDecl.accept(this);
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
            if (node.getExpr().getType() == null || node.getExpr().getType() instanceof VoidType)
                invalidReturnValueType = true;
            else if (node.getExpr().getType() instanceof NullType)
                invalidReturnValueType = !(currentReturnType instanceof ClassType || currentReturnType instanceof ArrayType);
            else if (!(node.getExpr().getType().equals(currentReturnType)))
                invalidReturnValueType = true;
        }
        if (invalidReturnValueType) {
            if (currentReturnType == null || currentReturnType instanceof VoidType)
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
        FuncEntity funcEntity = currentFuncEntity;
        int paraNum = funcEntity.getParameters().size();
        if (paraNum != node.getArgs().size())
            throw new SemanticError(node.location(), String.format("Function call has inconsistent number of arguments, expected %d but got %d", paraNum, node.getArgs().size()));
        boolean invalidArgType;
        for (int i = 0; i < paraNum; ++i) {
            node.getArgs().get(i).accept(this);
            if (node.getArgs().get(i).getType() instanceof VoidType)
                invalidArgType = true;
            else if (node.getArgs().get(i).getType() instanceof NullType)
                invalidArgType = !(funcEntity.getParameters().get(i).getType() instanceof ClassType || funcEntity.getParameters().get(i).getType() instanceof ArrayType);
            else
                invalidArgType = !(funcEntity.getParameters().get(i).getType().equals(node.getArgs().get(i).getType()));
            if (invalidArgType) {
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
        node.getSub().accept(this);
        if (!(node.getSub().getType() instanceof IntType))
            throw new SemanticError(node.getSub().location(), String.format("Subscript expression in subscription expression should have type \"int\", but got %s", node.getSub().getType().toString()));
        node.setType(((ArrayType) node.getArr().getType()).getBaseType());
        node.setLeftValue(true);
    }

    @Override
    public void visit(MemberAccessExprNode node) {
        node.getExpr().accept(this);
        String className;
        Entity memberEntity;
        if (node.getExpr().getType() instanceof ClassType)
            className = ((ClassType) node.getExpr().getType()).getName();
        else if (node.getExpr().getType() instanceof StringType)
            className = Scope.STRING_CLASS_NAME;
        else if (node.getExpr().getType() instanceof ArrayType)
            className = Scope.ARRAY_CLASS_NAME;
        else throw new SemanticError(node.location(), String.format("Type \"%s\" cannot be used in member access expression", node.getExpr().getType().toString()));
        ClassEntity classEntity = (ClassEntity) currentScope.getCheck(className, Scope.classKey(className));
        if (classEntity.getScope().selfContainsExactKey(Scope.varKey(node.getMember())))
            memberEntity = classEntity.getScope().selfGet(Scope.varKey(node.getMember()));
        else {
            memberEntity = classEntity.getScope().selfGetCheck(node.getMember(), Scope.funcKey(node.getMember()));
            currentFuncEntity = (FuncEntity) memberEntity;
        }
        node.setType(memberEntity.getType());
        node.setLeftValue(true);
    }

    @Override
    public void visit(PrefixExprNode node) {
        node.getExpr().accept(this);
        switch (node.getOp()) {
            case PREFIX_INC:
            case PREFIX_DEC:
                if (!(node.getExpr().getType() instanceof IntType))
                    throw new SemanticError(node.location(), String.format("Operator \"%s\" cannot be applied to type \"%s\"", node.getOp().toString(), node.getExpr().getType().toString()));
                if (!(node.getExpr().isLeftValue()))
                    throw new SemanticError(node.location(), String.format("Operator \"%s\" cannot be applied to right value", node.getOp().toString()));
                node.setType(IntType.getInstance());
                node.setLeftValue(true);
                break;
            case POS:
            case NEG:
            case BITWISE_NOT:
                if (!(node.getExpr().getType() instanceof IntType))
                    throw new SemanticError(node.location(), String.format("Operator \"%s\" cannot be applied to type \"%s\"", node.getOp().toString(), node.getExpr().getType().toString()));
                node.setType(IntType.getInstance());
                node.setLeftValue(false);
                break;
            case LOGIC_NOT:
                if (!(node.getExpr().getType() instanceof BoolType))
                    throw new SemanticError(node.location(), String.format("Operator \"%s\" cannot be applied to type \"%s\"", node.getOp().toString(), node.getExpr().getType().toString()));
                node.setType(BoolType.getInstance());
                node.setLeftValue(false);
                break;
            default:
                throw new CompilerError(node.location(), "Invalid prefix operator");
        }
    }

    @Override
    public void visit(NewExprNode node) {
        node.setType(node.getNewType().getType());
        node.setLeftValue(false);
    }

    @Override
    public void visit(BinaryExprNode node) {
        node.getLhs().accept(this);
        node.getRhs().accept(this);
        switch (node.getOp()) {
            case MUL:
            case DIV:
            case MOD:
            case ADD:
                if (node.getLhs().getType() instanceof StringType && node.getRhs().getType() instanceof StringType) {
                    node.setType(StringType.getInstance());
                    node.setLeftValue(false);
                    break;
                }
            case SUB:
            case SHL:
            case SHR:
            case BITWISE_OR:
            case BITWISE_AND:
            case BITWISE_XOR:
                if (!(node.getLhs().getType() instanceof IntType))
                    throw new SemanticError(node.location(), String.format("Operator \"%s\" cannot be applied to type \"%s\"", node.getOp().toString(), node.getLhs().getType().toString()));
                if (!(node.getRhs().getType() instanceof IntType))
                    throw new SemanticError(node.location(), String.format("Operator \"%s\" cannot be applied to type \"%s\"", node.getOp().toString(), node.getRhs().getType().toString()));
                node.setType(IntType.getInstance());
                node.setLeftValue(false);
                break;
            case GREATER:
            case LESS:
            case GREATER_EQUAL:
            case LESS_EQUAL:
                if (!(node.getLhs().getType() instanceof IntType || node.getLhs().getType() instanceof StringType))
                    throw new SemanticError(node.location(), String.format("Operator \"%s\" cannot be applied to type \"%s\"", node.getOp().toString(), node.getLhs().getType().toString()));
                if (!(node.getRhs().getType() instanceof IntType || node.getRhs().getType() instanceof StringType))
                    throw new SemanticError(node.location(), String.format("Operator \"%s\" cannot be applied to type \"%s\"", node.getOp().toString(), node.getRhs().getType().toString()));
                if (!(node.getLhs().getType().equals(node.getRhs().getType())))
                    throw new SemanticError(node.location(), String.format("Operator \"%s\" cannot be applied to different types \"%s\" and \"%s\"", node.getOp().toString(), node.getLhs().getType().toString(), node.getRhs().getType().toString()));
                node.setType(BoolType.getInstance());
                node.setLeftValue(false);
                break;
            case EQUAL:
            case INEQUAL:
                boolean invalidCompareType;
                if (node.getLhs().getType() instanceof VoidType || node.getRhs().getType() instanceof VoidType)
                    invalidCompareType = true;
                else if (node.getLhs().getType().equals(node.getRhs().getType()))
                    invalidCompareType = false;
                else if (node.getLhs().getType() instanceof NullType)
                    invalidCompareType = !(node.getRhs().getType() instanceof ClassType || node.getRhs().getType() instanceof ArrayType);
                else if (node.getRhs().getType() instanceof NullType)
                    invalidCompareType = !(node.getLhs().getType() instanceof ClassType || node.getLhs().getType() instanceof ArrayType);
                else
                    invalidCompareType = true;
                if (invalidCompareType)
                    throw new SemanticError(node.location(), String.format("Operator \"%s\" cannot be applied to different types \"%s\" and \"%s\"", node.getOp().toString(), node.getLhs().getType().toString(), node.getRhs().getType().toString()));
                node.setType(BoolType.getInstance());
                node.setLeftValue(false);
                break;
            case LOGIC_OR:
            case LOGIC_AND:
                if (!(node.getLhs().getType() instanceof BoolType))
                    throw new SemanticError(node.location(), String.format("Operator \"%s\" cannot be applied to type \"%s\"", node.getOp().toString(), node.getLhs().getType().toString()));
                if (!(node.getRhs().getType() instanceof BoolType))
                    throw new SemanticError(node.location(), String.format("Operator \"%s\" cannot be applied to type \"%s\"", node.getOp().toString(), node.getRhs().getType().toString()));
                node.setType(BoolType.getInstance());
                node.setLeftValue(false);
                break;
            default:
                throw new CompilerError(node.location(), "Invalid binary operator");
        }
    }

    @Override
    public void visit(AssignExprNode node) {
        node.getLhs().accept(this);
        node.getRhs().accept(this);
        if (!(node.getLhs().isLeftValue()))
            throw new SemanticError(node.location(), "Lhs of assignment statement should be left value");
        boolean invalidAssignType;
        if (node.getLhs().getType() instanceof VoidType || node.getRhs().getType() instanceof VoidType)
            invalidAssignType = true;
        else if (node.getLhs().getType().equals(node.getRhs().getType()))
            invalidAssignType = false;
        else if (node.getRhs().getType() instanceof NullType)
            invalidAssignType = !(node.getLhs().getType() instanceof ClassType || node.getLhs().getType() instanceof ArrayType);
        else
            invalidAssignType = true;
        if (invalidAssignType)
            throw new SemanticError(node.location(), String.format("Assignment operator cannot be applied to different types \"%s\" and \"%s\"", node.getLhs().getType().toString(), node.getRhs().getType().toString()));
        // support for a = b = c
        node.setType(node.getLhs().getType());
        node.setLeftValue(false);
    }

    @Override
    public void visit(IdentifierExprNode node) {
        String name = node.getIdentifier();
        Entity entity = currentScope.getVarFuncCheck(node.location(), name);
        if (entity instanceof VarEntity) {
            node.setLeftValue(true);
        }
        else if (entity instanceof FuncEntity) {
            currentFuncEntity = (FuncEntity) entity;
            node.setLeftValue(false);
        }
        else throw new CompilerError(node.location(), "Invalid entity type for identifier");
        node.setType(entity.getType());
    }

    @Override
    public void visit(ThisExprNode node) {
        Entity entity = currentScope.getVarFuncCheck(node.location(), Scope.THIS_PARA_NAME);
        if (!(entity instanceof VarEntity)) throw new CompilerError(node.location(), "Invalid entity type for \"this\"");
        node.setLeftValue(false);
        node.setType(entity.getType());
    }

    @Override
    public void visit(IntConstExprNode node) {
        node.setType(IntType.getInstance());
        node.setLeftValue(false);
    }

    @Override
    public void visit(StringConstExprNode node) {
        node.setType(StringType.getInstance());
        node.setLeftValue(false);
    }

    @Override
    public void visit(BoolConstExprNode node) {
        node.setType(BoolType.getInstance());
        node.setLeftValue(false);
    }

    @Override
    public void visit(NullExprNode node) {
        node.setType(NullType.getInstance());
        node.setLeftValue(false);
    }
}
