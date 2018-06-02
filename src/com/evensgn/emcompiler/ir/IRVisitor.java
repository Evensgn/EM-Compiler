package com.evensgn.emcompiler.ir;

public interface IRVisitor {
    void visit(IRRoot node);
    void visit(IRFunction node);
    void visit(BasicBlock node);

    void visit(IRBranch node);
    void visit(IRJump node);
    void visit(IRReturn node);

    void visit(IRUnaryOperation node);
    void visit(IRBinaryOperation node);
    void visit(IRComparison node);
    void visit(IRMove node);
    void visit(IRLoad node);
    void visit(IRStore node);
    void visit(IRFunctionCall node);
    void visit(IRHeapAlloc node);
    void visit(IRPush node);
    void visit(IRPop node);

    void visit(VirtualRegister node);
    void visit(PhysicalRegister node);
    void visit(IntImmediate node);
    void visit(StaticVar node);
    void visit(StaticString node);
}