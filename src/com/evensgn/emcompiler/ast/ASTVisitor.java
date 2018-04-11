package com.evensgn.emcompiler.ast;

public interface ASTVisitor {
    public void visit(FuncDeclNode node);
    public void visit(ClassDeclNode node);
    public void visit(VarDeclNode node);
    public void visit(BlockStmtNode node);
    public void visit(ExprStmtNode node);
    public void visit(CondStmtNode node);
    public void visit(WhileStmtNode node);
    public void visit(ForStmtNode node);
    public void visit(ContinueStmtNode node);
    public void visit(BreakStmtNode node);
    public void visit(ReturnStmtNode node);
    public void visit(BlankStmtNode node);
    public void visit(SuffixExprNode node);
    public void visit(FuncCallExprNode node);
    public void visit(SubscriptExprNode node);
    public void visit(MemberAccessExprNode node);
    public void visit(PrefixExprNode node);
    public void visit(NewExprNode node);
    public void visit(BinaryExprNode node);
    public void visit(AssignExprNode node);
    public void visit(PrimaryExprNode node);
    public void visit(arrayExprNode node);
    public void visit(primitiveExprNode node);
}
