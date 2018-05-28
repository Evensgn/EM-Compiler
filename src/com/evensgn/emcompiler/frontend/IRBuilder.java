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
import javafx.scene.media.VideoTrack;
import org.antlr.v4.codegen.model.decl.Decl;
import sun.reflect.generics.tree.BaseType;

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

    private void processIRAssign(RegValue dest, int addrOffset,  ExprNode rhs, int size, boolean needMemOp) {
        if (rhs.getType() instanceof BoolType) {
            BasicBlock mergeBB = new BasicBlock(currentFunc, null);
            if (needMemOp) {
                rhs.getTrueBB().addInst(new IRStore(rhs.getTrueBB(), new IntImmediate(1), BoolType.getInstance().getVarSize(), dest, addrOffset));
                rhs.getFalseBB().addInst(new IRStore(rhs.getFalseBB(), new IntImmediate(0), BoolType.getInstance().getVarSize(), dest, addrOffset));
            } else {
                rhs.getTrueBB().addInst(new IRMove(rhs.getTrueBB(), (VirtualRegister) dest, new IntImmediate(1)));
                rhs.getFalseBB().addInst(new IRMove(rhs.getFalseBB(), (VirtualRegister) dest, new IntImmediate(0)));
            }
            rhs.getTrueBB().addInst(new IRJump(rhs.getTrueBB(), mergeBB));
            rhs.getFalseBB().addInst(new IRJump(rhs.getFalseBB(), mergeBB));
        } else {
            if (needMemOp) {
                currentBB.addInst(new IRStore(currentBB, rhs.getRegValue(), rhs.getType().getVarSize(), dest, addrOffset));
            } else {
                currentBB.addInst(new IRMove(currentBB, (VirtualRegister) dest, rhs.getRegValue()));
            }
        }
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
        ClassEntity entity = (ClassEntity) currentScope.get(Scope.classKey(node.getName()));
        currentScope = entity.getScope();
        for (VarDeclNode decl : node.getVarMember()) {
            decl.accept(this);
        }
        for (FuncDeclNode decl : node.getFuncMember()) {
            decl.accept(this);
        }
        currentScope = currentScope.getParent();
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
                processIRAssign(vreg, 0, node.getInit(), node.getInit().getType().getVarSize(), false);
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
        FuncEntity funcEntity = (FuncEntity) currentScope.get(Scope.funcKey(funcName));
        List<RegValue> args = new ArrayList<>();
        if (funcEntity.isMember()) {
            ExprNode thisExpr;
            if (node.getFunc() instanceof MemberAccessExprNode) {
                thisExpr = ((MemberAccessExprNode) (node.getFunc())).getExpr();
            } else {
                thisExpr = new ThisExprNode(null);
            }
            thisExpr.accept(this);
            String className = ((ClassType) (thisExpr.getType())).getName();
            funcName = IRRoot.irMemberFuncName(className, funcName);
            args.add(thisExpr.getRegValue());
        }
        if (funcEntity.isBuiltIn()) {
            // process built-in functions
            // TO DO
            return;
        }
        for (ExprNode arg : node.getArgs()) {
            arg.accept(this);
            args.add(arg.getRegValue());
        }
        IRFunction irFunction = ir.getFunc(funcName);
        VirtualRegister vreg = new VirtualRegister(null);
        currentBB.addInst(new IRFunctionCall(currentBB, irFunction, args, vreg));
        node.setRegValue(vreg);
        if (node.getTrueBB() != null) {
            currentBB.setJumpInst(new IRBranch(currentBB, node.getRegValue(), node.getTrueBB(), node.getFalseBB()));
        }
    }

    @Override
    public void visit(SubscriptExprNode node) {
        boolean wantAddrBak = wantAddr;
        wantAddr = false;
        node.getArr().accept(this);
        node.getSub().accept(this);
        wantAddr = wantAddrBak;

        VirtualRegister vreg = new VirtualRegister(null);
        IntImmediate elementSize = new IntImmediate(node.getType().getVarSize());
        currentBB.addInst(new IRBinaryOperation(currentBB, vreg, IRBinaryOperation.IRBinaryOp.MUL, node.getSub().getRegValue(), elementSize));
        currentBB.addInst(new IRBinaryOperation(currentBB, vreg, IRBinaryOperation.IRBinaryOp.ADD, node.getArr().getRegValue(), vreg));

        if (wantAddr) {
            node.setAddrValue(vreg);
            node.setAddrOffset(Configuration.getRegSize());
        } else {
            currentBB.addInst(new IRLoad(currentBB, vreg, node.getType().getVarSize(), vreg, Configuration.getRegSize()));
            node.setRegValue(vreg);
            if (node.getTrueBB() != null) {
                currentBB.setJumpInst(new IRBranch(currentBB, node.getRegValue(), node.getTrueBB(), node.getFalseBB()));
            }
        }
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
            if (node.getTrueBB() != null) {
                currentBB.setJumpInst(new IRBranch(currentBB, node.getRegValue(), node.getTrueBB(), node.getFalseBB()));
            }
        }
    }

    @Override
    public void visit(PrefixExprNode node) {
        VirtualRegister vreg;
        switch (node.getOp()) {
            case PREFIX_DEC:
            case PREFIX_INC:
                processSelfIncDec(node.getExpr(), node, false, node.getOp() == PrefixExprNode.PrefixOps.PREFIX_INC);
                break;

            case POS:
                node.setRegValue(node.getExpr().getRegValue());
                break;

            case NEG:
                vreg = new VirtualRegister(null);
                node.setRegValue(vreg);
                node.getExpr().accept(this);
                currentBB.addInst(new IRUnaryOperation(currentBB, vreg, IRUnaryOperation.IRUnaryOp.NEG, node.getExpr().getRegValue()));
                break;

            case BITWISE_NOT:
                vreg = new VirtualRegister(null);
                node.setRegValue(vreg);
                node.getExpr().accept(this);
                currentBB.addInst(new IRUnaryOperation(currentBB, vreg, IRUnaryOperation.IRUnaryOp.BITWISE_NOT, node.getExpr().getRegValue()));
                break;

            case LOGIC_NOT:
                node.getExpr().setTrueBB(node.getFalseBB());
                node.getExpr().setFalseBB(node.getTrueBB());
                node.getExpr().accept(this);
                break;
            default:
                throw new CompilerError("invalid prefix operation");
        }
    }

    @Override
    public void visit(NewExprNode node) {
        VirtualRegister vreg = new VirtualRegister(null);
        Type newType = node.getNewType().getType();
        if (newType instanceof ClassType) {
            String className = ((ClassType) newType).getName();
            ClassEntity classEntity = (ClassEntity) globalScope.get(Scope.classKey(className));
            currentBB.addInst(new IRHeapAlloc(currentBB, vreg, new IntImmediate(classEntity.getMemorySize())));
            // TO DO construction function
        } else if (newType instanceof ArrayType) {
            ExprNode dim = node.getDims().get(0);
            boolean wantAddrBak = wantAddr;
            wantAddr = false;
            dim.accept(this);
            wantAddr = wantAddrBak;
            currentBB.addInst(new IRBinaryOperation(currentBB, vreg, IRBinaryOperation.IRBinaryOp.MUL, dim.getRegValue(), new IntImmediate(Configuration.getRegSize())));
            currentBB.addInst(new IRBinaryOperation(currentBB, vreg, IRBinaryOperation.IRBinaryOp.ADD, vreg, new IntImmediate(Configuration.getRegSize())));
            currentBB.addInst(new IRHeapAlloc(currentBB, vreg, vreg));
            currentBB.addInst(new IRStore(currentBB, dim.getRegValue(), Configuration.getRegSize(), vreg, 0));
        } else {
            throw new CompilerError("invalid new type");
        }
        node.setRegValue(vreg);
    }

    // short circuit for boolean operation
    private void processLogicalBinaryOp(BinaryExprNode node) {
        if (node.getOp() == BinaryExprNode.BinaryOps.LOGIC_AND) {
            node.getLhs().setTrueBB(new BasicBlock(currentFunc, "and_lhs_true"));
            node.getLhs().setFalseBB(node.getFalseBB());
            node.getLhs().accept(this);
            currentBB = node.getLhs().getTrueBB();
        } else if (node.getOp() == BinaryExprNode.BinaryOps.LOGIC_OR) {
            node.getLhs().setTrueBB(node.getTrueBB());
            node.getRhs().setFalseBB(new BasicBlock(currentFunc, "or_lhs_false"));
            node.getLhs().accept(this);
            currentBB = node.getLhs().getFalseBB();
        } else {
            throw new CompilerError("invalid boolean binary operation");
        }

        node.getRhs().setTrueBB(node.getTrueBB());
        node.getRhs().setFalseBB(node.getFalseBB());
        node.getRhs().accept(this);
    }

    private void processStringBinaryOp(BinaryExprNode node) {
        if (!(node.getLhs().getType() instanceof StringType)) {
            throw new CompilerError("invalid string binary operation");
        }
        // TO DO
    }

    private void processArithBinaryOp(BinaryExprNode node) {
        if (node.getLhs().getType() instanceof StringType) {
            processStringBinaryOp(node);
            return;
        }

        node.getLhs().accept(this);
        node.getRhs().accept(this);
        IRBinaryOperation.IRBinaryOp op;
        switch (node.getOp()) {
            case MUL:
                op = IRBinaryOperation.IRBinaryOp.MUL; break;
            case DIV:
                op = IRBinaryOperation.IRBinaryOp.DIV; break;
            case MOD:
                op = IRBinaryOperation.IRBinaryOp.MOD; break;
            case ADD:
                op = IRBinaryOperation.IRBinaryOp.ADD; break;
            case SUB:
                op = IRBinaryOperation.IRBinaryOp.SUB; break;
            case SHL:
                op = IRBinaryOperation.IRBinaryOp.SHL; break;
            case SHR:
                op = IRBinaryOperation.IRBinaryOp.SHR; break;
            case BITWISE_AND:
                op = IRBinaryOperation.IRBinaryOp.BITWISE_AND; break;
            case BITWISE_OR:
                op = IRBinaryOperation.IRBinaryOp.BITWISE_OR; break;
            case BITWISE_XOR:
                op = IRBinaryOperation.IRBinaryOp.BITWISE_XOR; break;
            default:
                throw new CompilerError("invalid int arithmetic binary operation");
        }

        VirtualRegister vreg = new VirtualRegister(null);
        node.setRegValue(vreg);
        currentBB.addInst(new IRBinaryOperation(currentBB, vreg, op, node.getLhs().getRegValue(), node.getRhs().getRegValue()));
    }

    private void processCmpBinaryOp(BinaryExprNode node) {
        if (node.getLhs().getType() instanceof StringType) {
            processStringBinaryOp(node);
            return;
        }

        node.getLhs().accept(this);
        node.getRhs().accept(this);
        IRComparison.IRCmpOp op;
        switch (node.getOp()) {
            case GREATER:
                op = IRComparison.IRCmpOp.GREATER; break;
            case LESS:
                op = IRComparison.IRCmpOp.LESS; break;
            case GREATER_EQUAL:
                op = IRComparison.IRCmpOp.GREATER_EQUAL; break;
            case LESS_EQUAL:
                op = IRComparison.IRCmpOp.LESS_EQUAL; break;
            case EQUAL:
                op = IRComparison.IRCmpOp.EQUAL; break;
            case INEQUAL:
                op = IRComparison.IRCmpOp.INEQUAL; break;
            default:
                throw new CompilerError("invalid int comparison binary operation");
        }

        VirtualRegister vreg = new VirtualRegister(null);
        currentBB.addInst(new IRComparison(currentBB, vreg, op, node.getLhs().getRegValue(), node.getRhs().getRegValue()));
        if (node.getTrueBB() != null) {
            currentBB.setJumpInst(new IRBranch(currentBB, vreg, node.getTrueBB(), node.getFalseBB()));
        } else {
            node.setRegValue(vreg);
        }
    }


    @Override
    public void visit(BinaryExprNode node) {
        VirtualRegister vreg;
        switch (node.getOp()) {
            case LOGIC_AND:
            case LOGIC_OR:
                processLogicalBinaryOp(node);
                break;

            case MUL:
            case DIV:
            case MOD:
            case ADD:
            case SUB:
            case SHL:
            case SHR:
            case BITWISE_AND:
            case BITWISE_OR:
            case BITWISE_XOR:
                processArithBinaryOp(node);
                break;

            case GREATER:
            case LESS:
            case GREATER_EQUAL:
            case LESS_EQUAL:
            case EQUAL:
            case INEQUAL:
                processCmpBinaryOp(node);
                break;
        }
    }

    @Override
    public void visit(AssignExprNode node) {
        if (node.getRhs().getType() instanceof BoolType) {
            node.getRhs().setTrueBB(new BasicBlock(currentFunc, null));
            node.getRhs().setFalseBB(new BasicBlock(currentFunc, null));
        }
        node.getRhs().accept(this);

        boolean needMemOp = node.getLhs() instanceof MemberAccessExprNode || node.getLhs() instanceof SubscriptExprNode;
        wantAddr = needMemOp;
        node.getLhs().accept(this);
        wantAddr = false;

        RegValue dest;
        int addrOffset;
        if (needMemOp) {
            dest = node.getLhs().getAddrValue();
            addrOffset = node.getLhs().getAddrOffset();
        } else {
            dest = node.getLhs().getRegValue();
            addrOffset = 0;
        }
        processIRAssign(dest, addrOffset, node.getRhs(), Configuration.getRegSize(), needMemOp);
        node.setRegValue(node.getRegValue());
    }

    @Override
    public void visit(IdentifierExprNode node) {
        // should be a variable instead of a function
        VarEntity varEntity = (VarEntity) currentScope.get(Scope.varKey(node.getIdentifier()));
        node.setRegValue(varEntity.getIrRegister());
        if (node.getTrueBB() != null) {
            currentBB.setJumpInst(new IRBranch(currentBB, node.getRegValue(), node.getTrueBB(), node.getFalseBB()));
        }
    }

    @Override
    public void visit(ThisExprNode node) {
        VarEntity thisEntity = (VarEntity) currentScope.get(Scope.varKey(Scope.THIS_PARA_NAME));
        node.setRegValue(thisEntity.getIrRegister());
        if (node.getTrueBB() != null) {
            currentBB.setJumpInst(new IRBranch(currentBB, node.getRegValue(), node.getTrueBB(), node.getFalseBB()));
        }
    }

    @Override
    public void visit(IntConstExprNode node) {
        node.setRegValue(new IntImmediate(node.getValue()));
    }

    @Override
    public void visit(StringConstExprNode node) {
        StaticString staticStr = ir.getStaticStr(node.getValue());
        if (staticStr == null) {
            staticStr = new StaticString(node.getValue());
            ir.addStaticStr(staticStr);
        }
        node.setRegValue(staticStr);
    }

    @Override
    public void visit(BoolConstExprNode node) {
        node.setRegValue(new IntImmediate(node.getValue() ? 1 : 0));
    }

    @Override
    public void visit(NullExprNode node) {
        node.setRegValue(new IntImmediate(0));
    }

    @Override
    public void visit(TypeNode node) {
        // no actions to take
    }
}
