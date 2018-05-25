package com.evensgn.emcompiler.frontend;

import com.evensgn.emcompiler.Configuration;
import com.evensgn.emcompiler.ast.*;
import com.evensgn.emcompiler.ir.*;
import com.evensgn.emcompiler.scope.FuncEntity;
import com.evensgn.emcompiler.scope.Scope;
import com.evensgn.emcompiler.scope.VarEntity;
import com.evensgn.emcompiler.type.BoolType;
import com.evensgn.emcompiler.type.IntType;
import com.evensgn.emcompiler.type.Type;
import com.evensgn.emcompiler.type.VoidType;
import com.evensgn.emcompiler.utils.CompilerError;

import java.util.ArrayList;
import java.util.List;

public class IRBuilder extends BaseScopeScanner {
    private final String INIT_FUNC_NAME = "#INIT_FUNC";
    private IRRoot ir = new IRRoot();
    private IRFunction currentFunc = null;
    private BasicBlock currentBB = null;
    private Scope globalScope, currentScope;
    private List<GlobalVarInit> globalInitList = new ArrayList<>();
    private boolean isFuncArgDecl;

    public IRRoot getIR() {
        return ir;
    }

    public IRBuilder(Scope globalScope) {
        this.globalScope = globalScope;
    }

    private FuncDeclNode makeInitFunc() {
        List<Node> stmts = new ArrayList<>();
        for (GlobalVarInit init : globalInitList) {
            IdentifierExprNode lhs = new IdentifierExprNode(init.getName(), null);
            AssignExprNode assignExpr = new AssignExprNode(lhs, init.getInitExpr(), null);
            stmts.add(new ExprStmtNode(assignExpr, null));
        }
        BlockStmtNode body = new BlockStmtNode(stmts, null);
        TypeNode retType = new TypeNode(VoidType.getInstance(), null);
        FuncDeclNode funcNode = new FuncDeclNode(retType, INIT_FUNC_NAME, new ArrayList<>(), body, null);
        globalScope.put(Scope.funcKey(INIT_FUNC_NAME), new FuncEntity(funcNode));
        return funcNode;
    }

    private void processIRAssign(IRRegister lhs, ExprNode rhs, int size) {

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
        FuncDeclNode initFunc = makeInitFunc();
        initFunc.accept(this);
    }

    @Override
    public void visit(FuncDeclNode node) {
        FuncEntity funcEntity = (FuncEntity) currentScope.get(Scope.funcKey(node.getName()));
        currentFunc = new IRFunction(funcEntity);
        node.getBody().accept(this);
        if (!currentBB.isHasJumpInst()) {
            if (node.getReturnType() == null || node.getReturnType().getType() instanceof VoidType) {
                currentBB.setJumpInst(new IRReturn(currentBB, null));
            } else {
                currentBB.setJumpInst(new IRReturn(currentBB, new IntImmediate(0)));
            }
        }
    }

    @Override
    public void visit(ClassDeclNode node) {
        super.visit(node);
    }

    @Override
    public void visit(VarDeclNode node) {
        VarEntity entity = (VarEntity) currentScope.get(Scope.varKey(node.getName()));
        if (currentScope.isTop()) {
            // global variables should be placed in data section
            Type type = node.getType().getType();
            StaticData data = new StaticVar(node.getName(), Configuration.getRegSize());
            ir.addStaticData(data);
            entity.setIrRegister(data);
            if (node.getInit() != null) {
                GlobalVarInit init = new GlobalVarInit(node.getName(), node.getInit());
                globalInitList.add(init);
            }
        } else {
            VirtualRegister vreg = new VirtualRegister(node.getName());
            entity.setIrRegister(vreg);
            if (isFuncArgDecl) {
                currentFunc.addArgVReg(vreg);
            }
            if (node.getInit() == null) {
                if (!isFuncArgDecl) {
                    // set default value to 0 if variable is not initialized
                    currentBB.addInst(new IRMove(currentBB, vreg, new IntImmediate(0)));
                }
            } else {
                node.getInit().accept(this);
                processIRAssign(vreg, node.getInit(), node.getInit().getType().getVarSize());
            }
        }
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
