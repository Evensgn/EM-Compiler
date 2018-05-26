package com.evensgn.emcompiler.frontend;

import com.evensgn.emcompiler.Configuration;
import com.evensgn.emcompiler.ast.*;
import com.evensgn.emcompiler.compiler.Compiler;
import com.evensgn.emcompiler.ir.*;
import com.evensgn.emcompiler.scope.*;
import com.evensgn.emcompiler.type.*;
import com.evensgn.emcompiler.utils.CompilerError;
import com.sun.crypto.provider.DESCipher;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.antlr.v4.codegen.model.decl.Decl;

import java.util.ArrayList;
import java.util.List;

public class IRBuilder extends BaseScopeScanner {
    private final String INIT_FUNC_NAME = "#INIT_FUNC";
    private IRRoot ir = new IRRoot();
    private IRFunction currentFunc = null;
    private BasicBlock currentBB = null;
    private Scope globalScope, currentScope;
    private List<GlobalVarInit> globalInitList = new ArrayList<>();
    private boolean isFuncArgDecl = false, wantAddr = false;
    private BasicBlock currentLoopStepBB, currentLoopAfterBB;

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

    private boolean isMemoryAccess(ExprNode node) {
        return node instanceof SubscriptExprNode || node instanceof MemberAccessExprNode;
    }

    @Override
    public void visit(ProgramNode node) {
        currentScope = globalScope;
        // pre-scanning for functions
        for (DeclNode decl : node.getDecls()) {
            if (decl instanceof FuncDeclNode) {
                FuncEntity funcEntity = (FuncEntity) currentScope.get(Scope.funcKey(decl.getName()));
                IRFunction newIRFunc = new IRFunction(funcEntity);
                ir.addFunc(newIRFunc);
            }
        }
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
        currentFunc = ir.getFunc(node.getName());
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
                if (node.getInit().getType() instanceof BoolType) {
                    node.getInit().setTrueBB(new BasicBlock(currentFunc, null));
                    node.getInit().setFalseBB(new BasicBlock(currentFunc, null));
                }
                node.getInit().accept(this);
                processIRAssign(vreg, node.getInit(), node.getInit().getType().getVarSize());
            }
        }
    }

    @Override
    public void visit(BlockStmtNode node) {
        currentScope = node.getScope();
        for (Node stmtOrVarDecl : node.getStmtsAndVarDecls()) {
            if (stmtOrVarDecl instanceof VarDeclNode) {
                stmtOrVarDecl.accept(this);
            } else if (stmtOrVarDecl instanceof StmtNode) {
                stmtOrVarDecl.accept(this);
            } else {
                throw new CompilerError("invalid type of statement or variable declaration");
            }
        }
        currentScope = currentScope.getParent();
    }

    @Override
    public void visit(ExprStmtNode node) {
        node.getExpr().accept(this);
    }

    @Override
    public void visit(CondStmtNode node) {
        BasicBlock thenBB = new BasicBlock(currentFunc, "if_then");
        BasicBlock afterBB = new BasicBlock(currentFunc, "if_after");

        currentBB = thenBB;
        node.getThenStmt().accept(this);
        currentBB.setJumpInst(new IRJump(currentBB, afterBB));

        if (node.getElseStmt() != null) {
            BasicBlock elseBB = new BasicBlock(currentFunc, "if_else");
            node.getCond().setTrueBB(thenBB);
            node.getCond().setFalseBB(elseBB);
            node.getCond().accept(this);

            currentBB = elseBB;
            node.getElseStmt().accept(this);
            currentBB.setJumpInst(new IRJump(currentBB, afterBB));
        } else {
            node.getCond().setTrueBB(thenBB);
            node.getCond().setFalseBB(afterBB);
            node.getCond().accept(this);
        }

        currentBB = afterBB;
    }

    @Override
    public void visit(WhileStmtNode node) {
        BasicBlock condBB = new BasicBlock(currentFunc, "while_cond");
        BasicBlock bodyBB = new BasicBlock(currentFunc, "while_body");
        BasicBlock afterBB = new BasicBlock(currentFunc, "while_after");

        currentBB.setJumpInst(new IRJump(currentBB, condBB));
        currentBB = condBB;
        node.getCond().setTrueBB(bodyBB);
        node.getCond().setFalseBB(afterBB);
        node.getCond().accept(this);

        currentBB = bodyBB;
        BasicBlock externalLoopCondBB = currentLoopStepBB, externalLoopAfterBB = currentLoopAfterBB;
        currentLoopStepBB = condBB;
        currentLoopAfterBB = afterBB;    node.getStmt().accept(this);
        currentBB.setJumpInst(new IRJump(currentBB, condBB));

        currentLoopStepBB = externalLoopCondBB;
        currentLoopAfterBB = externalLoopAfterBB;

        currentBB = afterBB;
    }

    @Override
    public void visit(ForStmtNode node) {
        BasicBlock condBB, stepBB, bodyBB, afterBB;
        bodyBB = new BasicBlock(currentFunc, "for_body");
        if (node.getCond() != null) condBB = new BasicBlock(currentFunc, "for_cond");
        else condBB = bodyBB;
        if (node.getStep() != null) stepBB = new BasicBlock(currentFunc, "for_step");
        else stepBB = condBB;
        afterBB = new BasicBlock(currentFunc, "for_after");

        BasicBlock externalLoopStepBB = currentLoopStepBB, externalLoopAfterBB = currentLoopAfterBB;
        currentLoopStepBB = stepBB;
        currentLoopAfterBB = afterBB;

        if (node.getInit() != null) node.getInit().accept(this);
        currentBB.setJumpInst(new IRJump(currentBB, condBB));

        if (node.getCond() != null) {
            currentBB = condBB;
            node.getCond().setTrueBB(bodyBB);
            node.getCond().setFalseBB(afterBB);
            node.getCond().accept(this);
        }

        if (node.getStep() != null) {
            currentBB = stepBB;
            node.getStep().accept(this);
            currentBB.setJumpInst(new IRJump(currentBB, condBB));
        }


        currentBB = bodyBB;
        node.getStmt().accept(this);
        currentBB.setJumpInst(new IRJump(currentBB, stepBB));

        currentLoopStepBB = externalLoopStepBB;
        currentLoopAfterBB = externalLoopAfterBB;

        currentBB = afterBB;
    }

    @Override
    public void visit(ContinueStmtNode node) {
        currentBB.setJumpInst(new IRJump(currentBB, currentLoopStepBB));
    }

    @Override
    public void visit(BreakStmtNode node) {
        currentBB.setJumpInst(new IRJump(currentBB, currentLoopAfterBB));
    }

    @Override
    public void visit(ReturnStmtNode node) {
        Type retType = currentFunc.getFuncEntity().getReturnType();
        if (retType == null || retType instanceof VoidType) {
            currentBB.setJumpInst(new IRReturn(currentBB, null));
        } else {
            if (retType instanceof BoolType) {
                node.getExpr().setTrueBB(new BasicBlock(currentFunc, null));
                node.getExpr().setFalseBB(new BasicBlock(currentFunc, null));
            }
            node.getExpr().accept(this);
            currentBB.setJumpInst(new IRReturn(currentBB, node.getExpr().getRegValue()));
        }
    }

    private void processSelfIncDec(ExprNode expr, ExprNode node, boolean isSuffix, boolean isInc) {
        boolean needMemOp = isMemoryAccess(expr);
        boolean bakWantAddr = wantAddr;

        wantAddr = false;
        expr.accept(this);

        if (isSuffix) {
            VirtualRegister vreg = new VirtualRegister(null);
            currentBB.addInst(new IRMove(currentBB, vreg, expr.getRegValue()));
            node.setRegValue(vreg);
        } else {
            node.setRegValue(expr.getRegValue());
        }

        IntImmediate one = new IntImmediate(1);
        IRBinaryOperation.IRBinaryOp op = isInc ? IRBinaryOperation.IRBinaryOp.ADD : IRBinaryOperation.IRBinaryOp.SUB;

        if (needMemOp) {
            // get addr of expr
            wantAddr = true;
            expr.accept(this);

            VirtualRegister vreg = new VirtualRegister(null);
            currentBB.addInst(new IRBinaryOperation(currentBB, vreg, op, expr.getRegValue(), one));
            currentBB.addInst(new IRStore(currentBB, vreg, expr.getType().getVarSize(), expr.getAddrValue(), expr.getAddrOffset()));
            if (!isSuffix) {
                expr.setRegValue(vreg);
            }
        } else {
            currentBB.addInst(new IRBinaryOperation(currentBB, (IRRegister) expr.getRegValue(), op, expr.getAddrValue(), one));
        }
        wantAddr = bakWantAddr;
    }

    @Override
    public void visit(SuffixExprNode node) {
        processSelfIncDec(node.getExpr(), node, true, node.getOp() == SuffixExprNode.SuffixOps.SUFFIX_INC);
    }

    @Override
    public void visit(FuncCallExprNode node) {
        String funcName = ((FunctionType)(node.getFunc().getType())).getName();
        List<RegValue> args = new ArrayList<>();
        if (node.getFunc() instanceof MemberAccessExprNode) {
            ExprNode thisExpr = ((MemberAccessExprNode) (node.getFunc())).getExpr();
            thisExpr.accept(this);
            String className = ((ClassType) (thisExpr.getType())).getName();
            funcName = IRRoot.irMemberFuncName(className, funcName);
            args.add(thisExpr.getRegValue());
        }
        IRFunction irFunction = ir.getFunc(funcName);
        if (irFunction.getFuncEntity().isBuiltIn()) {
            // process built-in functions
            return;
        }
        for (ExprNode arg : node.getArgs()) {
            arg.accept(this);
            args.add(arg.getRegValue());
        }
        VirtualRegister vreg = new VirtualRegister(null);
        currentBB.addInst(new IRFunctionCall(currentBB, irFunction, args, vreg));
        node.setRegValue(vreg);
    }

    @Override
    public void visit(SubscriptExprNode node) {

    }

    @Override
    public void visit(MemberAccessExprNode node) {
        boolean wantAddrBak = wantAddr;
        wantAddr = false;
        node.getExpr().accept(this);
        wantAddr = wantAddrBak;

        RegValue classAddr = node.getExpr().getRegValue();
        String className = ((ClassType) (node.getExpr().getType())).getName();
        ClassEntity classEntity = (ClassEntity) currentScope.getCheck(className, Scope.classKey(className));
        VarEntity memberEntity = (VarEntity) classEntity.getScope().selfGet(Scope.varKey(node.getMember()));

        if (wantAddr) {
            node.setAddrValue(classAddr);
            node.setAddrOffset(memberEntity.getAddrOffset());
        } else {
            VirtualRegister vreg = new VirtualRegister(null);
            node.setRegValue(vreg);
            currentBB.addInst(new IRLoad(currentBB, vreg, memberEntity.getType().getVarSize(), classAddr, memberEntity.getAddrOffset()));
            if (node.getType() instanceof BoolType) {
                currentBB.addInst(new IRBranch(currentBB, node.getRegValue(), node.getTrueBB(), node.getFalseBB()));
            }
        }
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
