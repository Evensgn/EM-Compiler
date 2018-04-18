package com.evensgn.emcompiler.frontend;

import com.evensgn.emcompiler.ast.*;
import com.evensgn.emcompiler.parser.*;
import com.evensgn.emcompiler.type.Type;
import com.evensgn.emcompiler.utils.SemanticError;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ParseTree;

import java.util.ArrayList;
import java.util.List;

public class ASTBuilder extends EMxStarBaseVisitor<Node> {
    private TypeNode typeForVarDecl;

    @Override
    public Node visitProgram(EMxStarParser.ProgramContext ctx) {
        List<DeclNode> decls = new ArrayList<>();
        for (ParserRuleContext programSection : ctx.programSection()) {
            Node decl = visit(programSection);
            if (decl instanceof VarDeclListNode) decls.addAll(((VarDeclListNode) decl).getDecls());
            else decls.add((DeclNode) decl);
        }
        return new ProgramNode(decls, Location.fromCtx(ctx));
    }

    @Override
    public Node visitFunctionDeclaration(EMxStarParser.FunctionDeclarationContext ctx) {
        TypeNode returnType;
        if (ctx.typeTypeOrVoid() != null) returnType = (TypeNode) visit(ctx.typeTypeOrVoid());
        else returnType = null;
        String name = ctx.Identifier().getText();
        List<VarDeclNode> parameterList = new ArrayList<>();
        Node paraDecl;
        for (ParserRuleContext parameterDeclaration : ctx.parameterDeclarationList().parameterDeclaration()) {
            paraDecl = visit(parameterDeclaration);
            parameterList.add((VarDeclNode) paraDecl);
        }
        BlockStmtNode body = (BlockStmtNode) visit(ctx.block());
        return new FuncDeclNode(returnType, name, parameterList, body, Location.fromCtx(ctx));
    }

    @Override
    public Node visitClassDeclaration(EMxStarParser.ClassDeclarationContext ctx) {
        String name = ctx.Identifier().getText();
        List<VarDeclNode> varMember = new ArrayList<>();
        List<FuncDeclNode> funcMember = new ArrayList<>();
        Node memberDecl;
        for (ParserRuleContext memberDeclaration : ctx.memberDeclaration()) {
            memberDecl = visit(memberDeclaration);
            if (memberDecl instanceof VarDeclListNode) varMember.addAll(((VarDeclListNode) memberDecl).getDecls());
            else funcMember.add((FuncDeclNode) memberDecl);
        }
        return new ClassDeclNode(name, varMember, funcMember, Location.fromCtx(ctx));
    }

    @Override
    public Node visitVariableDeclaration(EMxStarParser.VariableDeclarationContext ctx) {
        typeForVarDecl = (TypeNode) visit(ctx.typeType());
        return visit(ctx.variableDeclaratorList());
    }

    @Override
    public Node visitVariableDeclaratorList(EMxStarParser.VariableDeclaratorListContext ctx) {
        List<VarDeclNode> decls = new ArrayList<>();
        for (ParserRuleContext variableDeclarator : ctx.variableDeclarator()) {
            decls.add((VarDeclNode) visit(variableDeclarator));
        }
        return new VarDeclListNode(decls);
    }

    @Override
    public Node visitVariableDeclarator(EMxStarParser.VariableDeclaratorContext ctx) {
        String name = ctx.Identifier().getText();
        ExprNode init;
        if (ctx.expression() != null) init = (ExprNode) visit(ctx.expression());
        else init = null;
        return new VarDeclNode(typeForVarDecl, name, init, Location.fromCtx(ctx));
    }

    @Override
    public Node visitMemberDeclaration(EMxStarParser.MemberDeclarationContext ctx) {
        if (ctx.functionDeclaration() != null) return visit(ctx.functionDeclaration());
        else return visit(ctx.variableDeclaration());
    }

    @Override
    public Node visitParameterDeclarationList(EMxStarParser.ParameterDeclarationListContext ctx) {
        return super.visitParameterDeclarationList(ctx);
    }

    @Override
    public Node visitParameterDeclaration(EMxStarParser.ParameterDeclarationContext ctx) {
        TypeNode type = (TypeNode) visit(ctx.typeType());
        String name = ctx.Identifier().getText();
        return new VarDeclNode(type, name, null, Location.fromCtx(ctx));
    }

    @Override
    public Node visitTypeTypeOrVoid(EMxStarParser.TypeTypeOrVoidContext ctx) {
        if (ctx.typeType() != null) return visit(ctx.typeType());
        else return new PrimitiveTypeNode(Type.Types.VOID, Location.fromCtx(ctx));
    }

    @Override
    public Node visitArrayType(EMxStarParser.ArrayTypeContext ctx) {
        TypeNode baseType = (TypeNode) visit(ctx.typeType());
        return new ArrayTypeNode(baseType, Location.fromCtx(ctx));
    }

    @Override
    public Node visitNonArrayType(EMxStarParser.NonArrayTypeContext ctx) {
        return super.visitNonArrayType(ctx);
    }

    @Override
    public Node visitNonArrayTypeType(EMxStarParser.NonArrayTypeTypeContext ctx) {
        return super.visitNonArrayTypeType(ctx);
    }

    @Override
    public Node visitBlockStmt(EMxStarParser.BlockStmtContext ctx) {
        return super.visitBlockStmt(ctx);
    }

    @Override
    public Node visitExprStmt(EMxStarParser.ExprStmtContext ctx) {
        return super.visitExprStmt(ctx);
    }

    @Override
    public Node visitCondStmt(EMxStarParser.CondStmtContext ctx) {
        return super.visitCondStmt(ctx);
    }

    @Override
    public Node visitLoopStmt(EMxStarParser.LoopStmtContext ctx) {
        return super.visitLoopStmt(ctx);
    }

    @Override
    public Node visitJumpStmt(EMxStarParser.JumpStmtContext ctx) {
        return super.visitJumpStmt(ctx);
    }

    @Override
    public Node visitBlankStmt(EMxStarParser.BlankStmtContext ctx) {
        return super.visitBlankStmt(ctx);
    }

    @Override
    public Node visitBlock(EMxStarParser.BlockContext ctx) {
        return super.visitBlock(ctx);
    }

    @Override
    public Node visitStmt(EMxStarParser.StmtContext ctx) {
        return super.visitStmt(ctx);
    }

    @Override
    public Node visitVarDeclStmt(EMxStarParser.VarDeclStmtContext ctx) {
        return super.visitVarDeclStmt(ctx);
    }

    @Override
    public Node visitConditionStatement(EMxStarParser.ConditionStatementContext ctx) {
        return super.visitConditionStatement(ctx);
    }

    @Override
    public Node visitWhileStmt(EMxStarParser.WhileStmtContext ctx) {
        return super.visitWhileStmt(ctx);
    }

    @Override
    public Node visitForStmt(EMxStarParser.ForStmtContext ctx) {
        return super.visitForStmt(ctx);
    }

    @Override
    public Node visitContinueStmt(EMxStarParser.ContinueStmtContext ctx) {
        return super.visitContinueStmt(ctx);
    }

    @Override
    public Node visitBreakStmt(EMxStarParser.BreakStmtContext ctx) {
        return super.visitBreakStmt(ctx);
    }

    @Override
    public Node visitReturnStmt(EMxStarParser.ReturnStmtContext ctx) {
        return super.visitReturnStmt(ctx);
    }

    @Override
    public Node visitNewExpr(EMxStarParser.NewExprContext ctx) {
        return super.visitNewExpr(ctx);
    }

    @Override
    public Node visitPrefixExpr(EMxStarParser.PrefixExprContext ctx) {
        return super.visitPrefixExpr(ctx);
    }

    @Override
    public Node visitPrimaryExpr(EMxStarParser.PrimaryExprContext ctx) {
        return super.visitPrimaryExpr(ctx);
    }

    @Override
    public Node visitSubscriptExpr(EMxStarParser.SubscriptExprContext ctx) {
        return super.visitSubscriptExpr(ctx);
    }

    @Override
    public Node visitSuffixExpr(EMxStarParser.SuffixExprContext ctx) {
        return super.visitSuffixExpr(ctx);
    }

    @Override
    public Node visitBinaryExpr(EMxStarParser.BinaryExprContext ctx) {
        return super.visitBinaryExpr(ctx);
    }

    @Override
    public Node visitMemberAccessExpr(EMxStarParser.MemberAccessExprContext ctx) {
        return super.visitMemberAccessExpr(ctx);
    }

    @Override
    public Node visitFuncCallExpr(EMxStarParser.FuncCallExprContext ctx) {
        return super.visitFuncCallExpr(ctx);
    }

    @Override
    public Node visitAssignExpr(EMxStarParser.AssignExprContext ctx) {
        return super.visitAssignExpr(ctx);
    }

    @Override
    public Node visitIdentifierExpr(EMxStarParser.IdentifierExprContext ctx) {
        return super.visitIdentifierExpr(ctx);
    }

    @Override
    public Node visitConstExpr(EMxStarParser.ConstExprContext ctx) {
        return super.visitConstExpr(ctx);
    }

    @Override
    public Node visitSubExpr(EMxStarParser.SubExprContext ctx) {
        return super.visitSubExpr(ctx);
    }

    @Override
    public Node visitIntConst(EMxStarParser.IntConstContext ctx) {
        return super.visitIntConst(ctx);
    }

    @Override
    public Node visitStringConst(EMxStarParser.StringConstContext ctx) {
        return super.visitStringConst(ctx);
    }

    @Override
    public Node visitNullLiteral(EMxStarParser.NullLiteralContext ctx) {
        return super.visitNullLiteral(ctx);
    }

    @Override
    public Node visitBoolConst(EMxStarParser.BoolConstContext ctx) {
        return super.visitBoolConst(ctx);
    }

    @Override
    public Node visitErrorCreator(EMxStarParser.ErrorCreatorContext ctx) {
        return super.visitErrorCreator(ctx);
    }

    @Override
    public Node visitArrayCreator(EMxStarParser.ArrayCreatorContext ctx) {
        return super.visitArrayCreator(ctx);
    }

    @Override
    public Node visitNonArrayCreator(EMxStarParser.NonArrayCreatorContext ctx) {
        return super.visitNonArrayCreator(ctx);
    }

    @Override
    public Node visitParameterList(EMxStarParser.ParameterListContext ctx) {
        return super.visitParameterList(ctx);
    }
}
