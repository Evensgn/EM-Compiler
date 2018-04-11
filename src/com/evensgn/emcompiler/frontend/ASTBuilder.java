package com.evensgn.emcompiler.frontend;

import com.evensgn.emcompiler.parser.EMxStarBaseListener;
import com.evensgn.emcompiler.parser.EMxStarParser;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.TerminalNode;

public class ASTBuilder extends EMxStarBaseListener {
    @Override 
    public void exitProgram(EMxStarParser.ProgramContext ctx) { }
    
    @Override 
    public void enterFuncDecl(EMxStarParser.FuncDeclContext ctx) {
        System.out.print("enterFuncDecl: ");
        System.out.println(ctx.getText());
    }
    
    @Override 
    public void exitFuncDecl(EMxStarParser.FuncDeclContext ctx) {
        System.out.print("exitFuncDecl: ");
        System.out.println(ctx.getText());
    }
    
    @Override 
    public void enterClassDecl(EMxStarParser.ClassDeclContext ctx) { }
    
    @Override 
    public void exitClassDecl(EMxStarParser.ClassDeclContext ctx) { }
    
    @Override 
    public void enterVarDecl(EMxStarParser.VarDeclContext ctx) { }
    
    @Override 
    public void exitVarDecl(EMxStarParser.VarDeclContext ctx) { }
    
    @Override 
    public void enterFunctionDeclaration(EMxStarParser.FunctionDeclarationContext ctx) {
        System.out.print("enterFunctionDeclaration: ");
        System.out.println(ctx.getText());
    }
    
    @Override 
    public void exitFunctionDeclaration(EMxStarParser.FunctionDeclarationContext ctx) {
        System.out.print("exitFunctionDeclaration: ");
        System.out.println(ctx.getText());
    }
    
    @Override 
    public void enterClassDeclaration(EMxStarParser.ClassDeclarationContext ctx) { }
    
    @Override 
    public void exitClassDeclaration(EMxStarParser.ClassDeclarationContext ctx) { }
    
    @Override 
    public void enterVariableDeclaration(EMxStarParser.VariableDeclarationContext ctx) { }
    
    @Override 
    public void exitVariableDeclaration(EMxStarParser.VariableDeclarationContext ctx) { }
    
    @Override 
    public void enterVariableDeclaratorList(EMxStarParser.VariableDeclaratorListContext ctx) { }
    
    @Override 
    public void exitVariableDeclaratorList(EMxStarParser.VariableDeclaratorListContext ctx) { }
    
    @Override 
    public void enterVariableDeclarator(EMxStarParser.VariableDeclaratorContext ctx) { }
    
    @Override 
    public void exitVariableDeclarator(EMxStarParser.VariableDeclaratorContext ctx) { }
    
    @Override 
    public void enterMemberDeclaration(EMxStarParser.MemberDeclarationContext ctx) { }
    
    @Override 
    public void exitMemberDeclaration(EMxStarParser.MemberDeclarationContext ctx) { }
    
    @Override 
    public void enterParameterDeclarationList(EMxStarParser.ParameterDeclarationListContext ctx) { }
    
    @Override 
    public void exitParameterDeclarationList(EMxStarParser.ParameterDeclarationListContext ctx) { }
    
    @Override 
    public void enterParameterDeclaration(EMxStarParser.ParameterDeclarationContext ctx) { }
    
    @Override 
    public void exitParameterDeclaration(EMxStarParser.ParameterDeclarationContext ctx) { }
    
    @Override 
    public void enterTypeTypeOrVoid(EMxStarParser.TypeTypeOrVoidContext ctx) { }
    
    @Override 
    public void exitTypeTypeOrVoid(EMxStarParser.TypeTypeOrVoidContext ctx) { }
    
    @Override 
    public void enterArrayType(EMxStarParser.ArrayTypeContext ctx) { }
    
    @Override 
    public void exitArrayType(EMxStarParser.ArrayTypeContext ctx) { }
    
    @Override 
    public void enterNonArrayType(EMxStarParser.NonArrayTypeContext ctx) { }
    
    @Override 
    public void exitNonArrayType(EMxStarParser.NonArrayTypeContext ctx) { }
    
    @Override 
    public void enterNonArrayTypeType(EMxStarParser.NonArrayTypeTypeContext ctx) { }
    
    @Override 
    public void exitNonArrayTypeType(EMxStarParser.NonArrayTypeTypeContext ctx) { }
    
    @Override 
    public void enterBlockStmt(EMxStarParser.BlockStmtContext ctx) { }
    
    @Override 
    public void exitBlockStmt(EMxStarParser.BlockStmtContext ctx) { }
    
    @Override 
    public void enterExprStmt(EMxStarParser.ExprStmtContext ctx) { }
    
    @Override 
    public void exitExprStmt(EMxStarParser.ExprStmtContext ctx) { }
    
    @Override 
    public void enterCondStmt(EMxStarParser.CondStmtContext ctx) { }
    
    @Override 
    public void exitCondStmt(EMxStarParser.CondStmtContext ctx) { }
    
    @Override 
    public void enterLoopStmt(EMxStarParser.LoopStmtContext ctx) { }
    
    @Override 
    public void exitLoopStmt(EMxStarParser.LoopStmtContext ctx) { }
    
    @Override 
    public void enterJumpStmt(EMxStarParser.JumpStmtContext ctx) { }
    
    @Override 
    public void exitJumpStmt(EMxStarParser.JumpStmtContext ctx) { }
    
    @Override 
    public void enterBlankStmt(EMxStarParser.BlankStmtContext ctx) { }
    
    @Override 
    public void exitBlankStmt(EMxStarParser.BlankStmtContext ctx) { }
    
    @Override 
    public void enterBlock(EMxStarParser.BlockContext ctx) { }
    
    @Override 
    public void exitBlock(EMxStarParser.BlockContext ctx) { }
    
    @Override 
    public void enterStmt(EMxStarParser.StmtContext ctx) { }
    
    @Override 
    public void exitStmt(EMxStarParser.StmtContext ctx) { }
    
    @Override 
    public void enterVarDeclStmt(EMxStarParser.VarDeclStmtContext ctx) { }
    
    @Override 
    public void exitVarDeclStmt(EMxStarParser.VarDeclStmtContext ctx) { }
    
    @Override 
    public void enterConditionStatement(EMxStarParser.ConditionStatementContext ctx) { }
    
    @Override 
    public void exitConditionStatement(EMxStarParser.ConditionStatementContext ctx) { }
    
    @Override 
    public void enterWhileStmt(EMxStarParser.WhileStmtContext ctx) { }
    
    @Override 
    public void exitWhileStmt(EMxStarParser.WhileStmtContext ctx) { }
    
    @Override 
    public void enterForStmt(EMxStarParser.ForStmtContext ctx) { }
    
    @Override 
    public void exitForStmt(EMxStarParser.ForStmtContext ctx) { }
    
    @Override 
    public void enterContinueStmt(EMxStarParser.ContinueStmtContext ctx) { }
    
    @Override 
    public void exitContinueStmt(EMxStarParser.ContinueStmtContext ctx) { }
    
    @Override 
    public void enterBreakStmt(EMxStarParser.BreakStmtContext ctx) { }
    
    @Override 
    public void exitBreakStmt(EMxStarParser.BreakStmtContext ctx) { }
    
    @Override 
    public void enterReturnStmt(EMxStarParser.ReturnStmtContext ctx) { }
    
    @Override 
    public void exitReturnStmt(EMxStarParser.ReturnStmtContext ctx) { }
    
    @Override 
    public void enterNewExpr(EMxStarParser.NewExprContext ctx) { }
    
    @Override 
    public void exitNewExpr(EMxStarParser.NewExprContext ctx) { }
    
    @Override 
    public void enterPrefixExpr(EMxStarParser.PrefixExprContext ctx) { }
    
    @Override 
    public void exitPrefixExpr(EMxStarParser.PrefixExprContext ctx) { }
    
    @Override 
    public void enterPrimaryExpr(EMxStarParser.PrimaryExprContext ctx) { }
    
    @Override 
    public void exitPrimaryExpr(EMxStarParser.PrimaryExprContext ctx) { }
    
    @Override 
    public void enterSubscriptExpr(EMxStarParser.SubscriptExprContext ctx) { }
    
    @Override 
    public void exitSubscriptExpr(EMxStarParser.SubscriptExprContext ctx) { }
    
    @Override 
    public void enterSuffixExpr(EMxStarParser.SuffixExprContext ctx) { }
    
    @Override 
    public void exitSuffixExpr(EMxStarParser.SuffixExprContext ctx) { }
    
    @Override 
    public void enterBinaryExpr(EMxStarParser.BinaryExprContext ctx) { }
    
    @Override 
    public void exitBinaryExpr(EMxStarParser.BinaryExprContext ctx) { }
    
    @Override 
    public void enterMemberAccessExpr(EMxStarParser.MemberAccessExprContext ctx) { }
    
    @Override 
    public void exitMemberAccessExpr(EMxStarParser.MemberAccessExprContext ctx) { }
    
    @Override 
    public void enterFuncCallExpr(EMxStarParser.FuncCallExprContext ctx) { }
    
    @Override 
    public void exitFuncCallExpr(EMxStarParser.FuncCallExprContext ctx) { }
    
    @Override 
    public void enterAssignExpr(EMxStarParser.AssignExprContext ctx) { }
    
    @Override 
    public void exitAssignExpr(EMxStarParser.AssignExprContext ctx) { }
    
    @Override 
    public void enterIdentifierExpr(EMxStarParser.IdentifierExprContext ctx) { }
    
    @Override 
    public void exitIdentifierExpr(EMxStarParser.IdentifierExprContext ctx) { }
    
    @Override 
    public void enterConstExpr(EMxStarParser.ConstExprContext ctx) { }
    
    @Override 
    public void exitConstExpr(EMxStarParser.ConstExprContext ctx) { }
    
    @Override 
    public void enterSubExpr(EMxStarParser.SubExprContext ctx) { }
    
    @Override 
    public void exitSubExpr(EMxStarParser.SubExprContext ctx) { }
    
    @Override 
    public void enterIntConst(EMxStarParser.IntConstContext ctx) { }
    
    @Override 
    public void exitIntConst(EMxStarParser.IntConstContext ctx) { }
    
    @Override 
    public void enterStringConst(EMxStarParser.StringConstContext ctx) { }
    
    @Override 
    public void exitStringConst(EMxStarParser.StringConstContext ctx) { }
    
    @Override 
    public void enterNullLiteral(EMxStarParser.NullLiteralContext ctx) { }
    
    @Override 
    public void exitNullLiteral(EMxStarParser.NullLiteralContext ctx) { }
    
    @Override 
    public void enterBoolConst(EMxStarParser.BoolConstContext ctx) { }
    
    @Override 
    public void exitBoolConst(EMxStarParser.BoolConstContext ctx) { }
    
    @Override 
    public void enterErrorCreator(EMxStarParser.ErrorCreatorContext ctx) { }
    
    @Override 
    public void exitErrorCreator(EMxStarParser.ErrorCreatorContext ctx) { }
    
    @Override 
    public void enterArrayCreator(EMxStarParser.ArrayCreatorContext ctx) { }
    
    @Override 
    public void exitArrayCreator(EMxStarParser.ArrayCreatorContext ctx) { }
    
    @Override 
    public void enterNonArrayCreator(EMxStarParser.NonArrayCreatorContext ctx) { }
    
    @Override 
    public void exitNonArrayCreator(EMxStarParser.NonArrayCreatorContext ctx) { }
    
    @Override 
    public void enterParameterList(EMxStarParser.ParameterListContext ctx) { }
    
    @Override 
    public void exitParameterList(EMxStarParser.ParameterListContext ctx) { }

    
    @Override 
    public void enterEveryRule(ParserRuleContext ctx) { }
    
    @Override 
    public void exitEveryRule(ParserRuleContext ctx) { }
    
    @Override 
    public void visitTerminal(TerminalNode node) { }
    
    @Override 
    public void visitErrorNode(ErrorNode node) { }
}
