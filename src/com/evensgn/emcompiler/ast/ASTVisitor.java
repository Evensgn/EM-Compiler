package com.evensgn.emcompiler.ast;

public interface ASTVisitor {
    void visit(FuncDeclNode node);
    void visit(ClassDeclNode node);
    void visit(VarDeclNode node);
    void visit(BlockStmtNode node);
    void visit(ExprStmtNode node);
    void visit(CondStmtNode node);
    void visit(WhileStmtNode node);
    void visit(ForStmtNode node);
    void visit(ContinueStmtNode node);
    void visit(BreakStmtNode node);
    void visit(ReturnStmtNode node);
    void visit(BlankStmtNode node);
    void visit(SuffixExprNode node);
    void visit(FuncCallExprNode node);
    void visit(SubscriptExprNode node);
    void visit(MemberAccessExprNode node);
    void visit(PrefixExprNode node);
    void visit(NewExprNode node);
    void visit(BinaryExprNode node);
    void visit(AssignExprNode node);
    void visit(IdentifierExprNode node);
    void visit(IntConstExprNode node);
    void visit(StringConstExprNode node);
    void visit(BoolConstExprNode node);
    void visit(NullExprNode node);
    void visit(PrimitiveTypeNode node);
    void visit(ClassTypeNode node);
    void visit(ArrayTypeNode node);
    void visit(FuncTypeNode node);
}
