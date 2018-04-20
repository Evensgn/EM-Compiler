package com.evensgn.emcompiler.frontend;

import com.evensgn.emcompiler.ast.*;

import java.io.PrintStream;

public class ASTPrinter implements ASTVisitor {
    private static final String INDENT_UNIT = "    ";
    private StringBuilder indentStrBuilder = new StringBuilder();
    private PrintStream out;

    public ASTPrinter(PrintStream out) {
        this.out = out;
    }

    private void indent() {
        indentStrBuilder.append(INDENT_UNIT);
    }

    private void unindent() {
        indentStrBuilder.delete(indentStrBuilder.length() - INDENT_UNIT.length(), indentStrBuilder.length());
    }

    private String getIndentStr() {
        return indentStrBuilder.toString();
    }

    private void println(String str) {
        out.println(getIndentStr() + str);
    }

    private void print(String str) {
        out.print(getIndentStr() + str);
    }

    private void printf(String str, Object... args) {
        out.printf(getIndentStr() + str, args);
    }


    @Override
    public void visit(ProgramNode node) {
        printf("@ ProgramNode %s:\n", node.location().toString());
        println(">>> decls:");
        for (DeclNode decl : node.getDecls()) {
            decl.accept(this);
        }
    }

    // no use
    @Override
    public void visit(VarDeclListNode node) {
    }

    @Override
    public void visit(FuncDeclNode node) {
        indent();
        printf("@ FuncDeclNode %s:\n", node.location().toString());
        printf(">>> isContruct: %b\n", node.isConstruct());
        if (node.getReturnType() != null) {
            println(">>> returnType:");
            node.getReturnType().accept(this);
        }
        else {
            println(">>> returnType: null");
        }
        printf(">>> name: %s\n", node.getName());
        if (!(node.getParameterList().isEmpty())) {
            println(">>> parameterList:");
            for (VarDeclNode parameter : node.getParameterList()) {
                parameter.accept(this);
            }
        }
        else {
            println(">>> parameterList: null");
        }
        println(">>> body:");
        node.getBody().accept(this);
        unindent();
    }

    @Override
    public void visit(ClassDeclNode node) {
        indent();
        printf("@ ClassDeclNode %s:\n", node.location().toString());
        printf(">>> name: %s\n", node.getName());
        if (!(node.getVarMember().isEmpty())) {
            println(">>> varMember:");
            for (VarDeclNode varMem : node.getVarMember()) {
                varMem.accept(this);
            }
        }
        else {
            println(">>> varMember: null");
        }
        if (!(node.getFuncMember().isEmpty())) {
            println(">>> funcMember:");
            for (FuncDeclNode funcMem : node.getFuncMember()) {
                funcMem.accept(this);
            }
        }
        else {
            println(">>> funcMember: null");
        }
        unindent();
    }

    @Override
    public void visit(VarDeclNode node) {
        indent();
        printf("@ VaeDeclNode %s:\n", node.location().toString());
        println(">>> type:");
        node.getType().accept(this);
        printf(">>> name: %s\n", node.getName());
        if (node.getInit() != null) {
            println(">>> init:");
            node.getInit().accept(this);
        }
        else {
            println(">>> init: null");
        }
        unindent();
    }

    @Override
    public void visit(BlockStmtNode node) {
        indent();
        printf("@ BlockStmtNode %s:\n", node.location().toString());
        if (!(node.getStmtsAndVarDecls().isEmpty())) {
            println(">>> stmtsAndVarDecls:");
            for (Node item : node.getStmtsAndVarDecls()) {
                item.accept(this);
            }
        }
        else {
            println(">>> stmtsAndVarDecls: null");
        }
        unindent();
    }

    @Override
    public void visit(ExprStmtNode node) {
        indent();
        printf("@ ExprStmtNode %s:\n", node.location().toString());
        println(">>> expr:");
        node.getExpr().accept(this);
        unindent();
    }

    @Override
    public void visit(CondStmtNode node) {
        indent();
        printf("@ CondStmtNode %s:\n", node.location().toString());
        println(">>> cond:");
        node.getCond().accept(this);
        println(">>> thenStmt:");
        node.getThenStmt().accept(this);
        if (node.getElseStmt() != null) {
            println(">>> elseStmt:");
            node.getElseStmt().accept(this);
        }
        else {
            println(">>> elseStmt: null");
        }
        unindent();
    }

    @Override
    public void visit(WhileStmtNode node) {
        indent();
        printf("@ WhileStmtNode %s:\n", node.location().toString());
        println(">>> cond:");
        node.getCond().accept(this);
        println(">>> stmt:");
        node.getStmt().accept(this);
        unindent();
    }

    @Override
    public void visit(ForStmtNode node) {
        indent();
        printf("@ ForStmtNode %s:\n", node.location().toString());
        if (node.getInit() != null) {
            println(">>> init:");
            node.getInit().accept(this);
        }
        else {
            println(">>> init: null");
        }
        if (node.getCond() != null) {
            println(">>> cond:");
            node.getCond().accept(this);
        }
        else {
            println(">>> cond: null");
        }
        if (node.getStep() != null) {
            println(">>> step:");
            node.getStep().accept(this);
        }
        else {
            println(">>> step: null");
        }
        println(">>> stmt:");
        node.getStmt().accept(this);
        unindent();
    }

    @Override
    public void visit(ContinueStmtNode node) {
        indent();
        printf("@ ContinueStmtNode %s:\n", node.location().toString());
        unindent();
    }

    @Override
    public void visit(BreakStmtNode node) {
        indent();
        printf("@ BreakStmtNode %s:\n", node.location().toString());
        unindent();
    }

    @Override
    public void visit(ReturnStmtNode node) {
        indent();
        printf("@ ReturnStmtNode %s:\n", node.location().toString());
        if (node.getExpr() != null) {
            println(">>> expr:");
            node.getExpr().accept(this);
        }
        else {
            println(">>> expr: null");
        }
        unindent();
    }

    @Override
    public void visit(SuffixExprNode node) {
        indent();
        printf("@ SuffixExprNode %s:\n", node.location().toString());
        printf(">>> op: %s\n", node.getOp().toString());
        println(">>> expr:");
        node.getExpr().accept(this);
        unindent();
    }

    @Override
    public void visit(FuncCallExprNode node) {
        indent();
        printf("@ FuncCallExprNode %s:\n", node.location().toString());
        println(">>> func:");
        node.getFunc().accept(this);
        if (!(node.getArgs().isEmpty())) {
            println(">>> args:");
            for (ExprNode arg : node.getArgs()) {
                arg.accept(this);
            }
        }
        else {
            println(">>> args: null");
        }
        unindent();
    }

    @Override
    public void visit(SubscriptExprNode node) {
        indent();
        printf("@ SubscriptExprNode %s:\n", node.location().toString());
        println(">>> arr:");
        node.getArr().accept(this);
        println(">>> sub:");
        node.getSub().accept(this);
        unindent();
    }

    @Override
    public void visit(MemberAccessExprNode node) {
        indent();
        printf("@ MemberAccessExprNode %s:\n", node.location().toString());
        println(">>> expr:");
        node.getExpr().accept(this);
        printf(">>> member: %s\n", node.getMember());
        unindent();
    }

    @Override
    public void visit(PrefixExprNode node) {
        indent();
        printf("@ PrefixExprNode %s:\n", node.location().toString());
        printf(">>> op: %s\n", node.getOp().toString());
        println(">>> expr:");
        node.getExpr().accept(this);
        unindent();
    }

    @Override
    public void visit(NewExprNode node) {
        indent();
        printf("@ NewExprNode %s:\n", node.location().toString());
        println(">>> newType:");
        node.getNewType().accept(this);
        if (node.getNumDim() != 0) {
            println(">>> dims:");
            for (ExprNode dim : node.getDims()) {
                dim.accept(this);
            }
            printf(">>> numDim: %d\n", node.getNumDim());
        }
        else {
            println(">>> numDim: 0");
        }
        unindent();
    }

    @Override
    public void visit(BinaryExprNode node) {
        indent();
        printf("@ BinaryExprNode %s:\n", node.location().toString());
        printf(">>> op: %s\n", node.getOp().toString());
        println(">>> lhs:");
        node.getLhs().accept(this);
        println(">>> rhs:");
        node.getRhs().accept(this);
        unindent();
    }

    @Override
    public void visit(AssignExprNode node) {
        indent();
        printf("@ AssignExprNode %s:\n", node.location().toString());
        println(">>> lhs:");
        node.getLhs().accept(this);
        println(">>> rhs:");
        node.getRhs().accept(this);
        unindent();
    }

    @Override
    public void visit(IdentifierExprNode node) {
        indent();
        printf("@ IdentifierExprNode %s:\n", node.location().toString());
        printf(">>> identifier: %s\n", node.getIdentifier());
        unindent();
    }

    @Override
    public void visit(IntConstExprNode node) {
        indent();
        printf("@ IntConstExprNode %s:\n", node.location().toString());
        printf(">>> value: %d\n", node.getValue());
        unindent();
    }

    @Override
    public void visit(StringConstExprNode node) {
        indent();
        printf("@ StringConstExprNode %s:\n", node.location().toString());
        printf(">>> value: %s\n", node.getValue());
        unindent();
    }

    @Override
    public void visit(BoolConstExprNode node) {
        indent();
        printf("@ BoolConstExprNode %s:\n", node.location().toString());
        printf(">>> value: %b\n", node.getValue());
        unindent();
    }

    @Override
    public void visit(NullExprNode node) {
        indent();
        printf("@ NullExprNode %s:\n", node.location().toString());
        unindent();
    }

    @Override
    public void visit(TypeNode node) {
        indent();
        printf("@ TypeNode %s:\n", node.location().toString());
        printf(">>> type: %s\n", node.getType().toString());
        unindent();
    }
}
