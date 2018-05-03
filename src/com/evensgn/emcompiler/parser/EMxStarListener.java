// Generated from EMxStar.g4 by ANTLR 4.7.1

package com.evensgn.emcompiler.parser;

import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link EMxStarParser}.
 */
public interface EMxStarListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link EMxStarParser#program}.
	 * @param ctx the parse tree
	 */
	void enterProgram(EMxStarParser.ProgramContext ctx);
	/**
	 * Exit a parse tree produced by {@link EMxStarParser#program}.
	 * @param ctx the parse tree
	 */
	void exitProgram(EMxStarParser.ProgramContext ctx);
	/**
	 * Enter a parse tree produced by {@link EMxStarParser#programSection}.
	 * @param ctx the parse tree
	 */
	void enterProgramSection(EMxStarParser.ProgramSectionContext ctx);
	/**
	 * Exit a parse tree produced by {@link EMxStarParser#programSection}.
	 * @param ctx the parse tree
	 */
	void exitProgramSection(EMxStarParser.ProgramSectionContext ctx);
	/**
	 * Enter a parse tree produced by {@link EMxStarParser#functionDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterFunctionDeclaration(EMxStarParser.FunctionDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link EMxStarParser#functionDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitFunctionDeclaration(EMxStarParser.FunctionDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link EMxStarParser#classDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterClassDeclaration(EMxStarParser.ClassDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link EMxStarParser#classDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitClassDeclaration(EMxStarParser.ClassDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link EMxStarParser#variableDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterVariableDeclaration(EMxStarParser.VariableDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link EMxStarParser#variableDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitVariableDeclaration(EMxStarParser.VariableDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link EMxStarParser#variableDeclaratorList}.
	 * @param ctx the parse tree
	 */
	void enterVariableDeclaratorList(EMxStarParser.VariableDeclaratorListContext ctx);
	/**
	 * Exit a parse tree produced by {@link EMxStarParser#variableDeclaratorList}.
	 * @param ctx the parse tree
	 */
	void exitVariableDeclaratorList(EMxStarParser.VariableDeclaratorListContext ctx);
	/**
	 * Enter a parse tree produced by {@link EMxStarParser#variableDeclarator}.
	 * @param ctx the parse tree
	 */
	void enterVariableDeclarator(EMxStarParser.VariableDeclaratorContext ctx);
	/**
	 * Exit a parse tree produced by {@link EMxStarParser#variableDeclarator}.
	 * @param ctx the parse tree
	 */
	void exitVariableDeclarator(EMxStarParser.VariableDeclaratorContext ctx);
	/**
	 * Enter a parse tree produced by {@link EMxStarParser#memberDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterMemberDeclaration(EMxStarParser.MemberDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link EMxStarParser#memberDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitMemberDeclaration(EMxStarParser.MemberDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link EMxStarParser#parameterDeclarationList}.
	 * @param ctx the parse tree
	 */
	void enterParameterDeclarationList(EMxStarParser.ParameterDeclarationListContext ctx);
	/**
	 * Exit a parse tree produced by {@link EMxStarParser#parameterDeclarationList}.
	 * @param ctx the parse tree
	 */
	void exitParameterDeclarationList(EMxStarParser.ParameterDeclarationListContext ctx);
	/**
	 * Enter a parse tree produced by {@link EMxStarParser#parameterDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterParameterDeclaration(EMxStarParser.ParameterDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link EMxStarParser#parameterDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitParameterDeclaration(EMxStarParser.ParameterDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link EMxStarParser#typeTypeOrVoid}.
	 * @param ctx the parse tree
	 */
	void enterTypeTypeOrVoid(EMxStarParser.TypeTypeOrVoidContext ctx);
	/**
	 * Exit a parse tree produced by {@link EMxStarParser#typeTypeOrVoid}.
	 * @param ctx the parse tree
	 */
	void exitTypeTypeOrVoid(EMxStarParser.TypeTypeOrVoidContext ctx);
	/**
	 * Enter a parse tree produced by the {@code arrayType}
	 * labeled alternative in {@link EMxStarParser#typeType}.
	 * @param ctx the parse tree
	 */
	void enterArrayType(EMxStarParser.ArrayTypeContext ctx);
	/**
	 * Exit a parse tree produced by the {@code arrayType}
	 * labeled alternative in {@link EMxStarParser#typeType}.
	 * @param ctx the parse tree
	 */
	void exitArrayType(EMxStarParser.ArrayTypeContext ctx);
	/**
	 * Enter a parse tree produced by the {@code nonArrayType}
	 * labeled alternative in {@link EMxStarParser#typeType}.
	 * @param ctx the parse tree
	 */
	void enterNonArrayType(EMxStarParser.NonArrayTypeContext ctx);
	/**
	 * Exit a parse tree produced by the {@code nonArrayType}
	 * labeled alternative in {@link EMxStarParser#typeType}.
	 * @param ctx the parse tree
	 */
	void exitNonArrayType(EMxStarParser.NonArrayTypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link EMxStarParser#nonArrayTypeType}.
	 * @param ctx the parse tree
	 */
	void enterNonArrayTypeType(EMxStarParser.NonArrayTypeTypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link EMxStarParser#nonArrayTypeType}.
	 * @param ctx the parse tree
	 */
	void exitNonArrayTypeType(EMxStarParser.NonArrayTypeTypeContext ctx);
	/**
	 * Enter a parse tree produced by the {@code blockStmt}
	 * labeled alternative in {@link EMxStarParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterBlockStmt(EMxStarParser.BlockStmtContext ctx);
	/**
	 * Exit a parse tree produced by the {@code blockStmt}
	 * labeled alternative in {@link EMxStarParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitBlockStmt(EMxStarParser.BlockStmtContext ctx);
	/**
	 * Enter a parse tree produced by the {@code exprStmt}
	 * labeled alternative in {@link EMxStarParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterExprStmt(EMxStarParser.ExprStmtContext ctx);
	/**
	 * Exit a parse tree produced by the {@code exprStmt}
	 * labeled alternative in {@link EMxStarParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitExprStmt(EMxStarParser.ExprStmtContext ctx);
	/**
	 * Enter a parse tree produced by the {@code condStmt}
	 * labeled alternative in {@link EMxStarParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterCondStmt(EMxStarParser.CondStmtContext ctx);
	/**
	 * Exit a parse tree produced by the {@code condStmt}
	 * labeled alternative in {@link EMxStarParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitCondStmt(EMxStarParser.CondStmtContext ctx);
	/**
	 * Enter a parse tree produced by the {@code loopStmt}
	 * labeled alternative in {@link EMxStarParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterLoopStmt(EMxStarParser.LoopStmtContext ctx);
	/**
	 * Exit a parse tree produced by the {@code loopStmt}
	 * labeled alternative in {@link EMxStarParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitLoopStmt(EMxStarParser.LoopStmtContext ctx);
	/**
	 * Enter a parse tree produced by the {@code jumpStmt}
	 * labeled alternative in {@link EMxStarParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterJumpStmt(EMxStarParser.JumpStmtContext ctx);
	/**
	 * Exit a parse tree produced by the {@code jumpStmt}
	 * labeled alternative in {@link EMxStarParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitJumpStmt(EMxStarParser.JumpStmtContext ctx);
	/**
	 * Enter a parse tree produced by the {@code blankStmt}
	 * labeled alternative in {@link EMxStarParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterBlankStmt(EMxStarParser.BlankStmtContext ctx);
	/**
	 * Exit a parse tree produced by the {@code blankStmt}
	 * labeled alternative in {@link EMxStarParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitBlankStmt(EMxStarParser.BlankStmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link EMxStarParser#block}.
	 * @param ctx the parse tree
	 */
	void enterBlock(EMxStarParser.BlockContext ctx);
	/**
	 * Exit a parse tree produced by {@link EMxStarParser#block}.
	 * @param ctx the parse tree
	 */
	void exitBlock(EMxStarParser.BlockContext ctx);
	/**
	 * Enter a parse tree produced by the {@code stmt}
	 * labeled alternative in {@link EMxStarParser#blockStatement}.
	 * @param ctx the parse tree
	 */
	void enterStmt(EMxStarParser.StmtContext ctx);
	/**
	 * Exit a parse tree produced by the {@code stmt}
	 * labeled alternative in {@link EMxStarParser#blockStatement}.
	 * @param ctx the parse tree
	 */
	void exitStmt(EMxStarParser.StmtContext ctx);
	/**
	 * Enter a parse tree produced by the {@code varDeclStmt}
	 * labeled alternative in {@link EMxStarParser#blockStatement}.
	 * @param ctx the parse tree
	 */
	void enterVarDeclStmt(EMxStarParser.VarDeclStmtContext ctx);
	/**
	 * Exit a parse tree produced by the {@code varDeclStmt}
	 * labeled alternative in {@link EMxStarParser#blockStatement}.
	 * @param ctx the parse tree
	 */
	void exitVarDeclStmt(EMxStarParser.VarDeclStmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link EMxStarParser#conditionStatement}.
	 * @param ctx the parse tree
	 */
	void enterConditionStatement(EMxStarParser.ConditionStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link EMxStarParser#conditionStatement}.
	 * @param ctx the parse tree
	 */
	void exitConditionStatement(EMxStarParser.ConditionStatementContext ctx);
	/**
	 * Enter a parse tree produced by the {@code whileStmt}
	 * labeled alternative in {@link EMxStarParser#loopStatement}.
	 * @param ctx the parse tree
	 */
	void enterWhileStmt(EMxStarParser.WhileStmtContext ctx);
	/**
	 * Exit a parse tree produced by the {@code whileStmt}
	 * labeled alternative in {@link EMxStarParser#loopStatement}.
	 * @param ctx the parse tree
	 */
	void exitWhileStmt(EMxStarParser.WhileStmtContext ctx);
	/**
	 * Enter a parse tree produced by the {@code forStmt}
	 * labeled alternative in {@link EMxStarParser#loopStatement}.
	 * @param ctx the parse tree
	 */
	void enterForStmt(EMxStarParser.ForStmtContext ctx);
	/**
	 * Exit a parse tree produced by the {@code forStmt}
	 * labeled alternative in {@link EMxStarParser#loopStatement}.
	 * @param ctx the parse tree
	 */
	void exitForStmt(EMxStarParser.ForStmtContext ctx);
	/**
	 * Enter a parse tree produced by the {@code continueStmt}
	 * labeled alternative in {@link EMxStarParser#jumpStatement}.
	 * @param ctx the parse tree
	 */
	void enterContinueStmt(EMxStarParser.ContinueStmtContext ctx);
	/**
	 * Exit a parse tree produced by the {@code continueStmt}
	 * labeled alternative in {@link EMxStarParser#jumpStatement}.
	 * @param ctx the parse tree
	 */
	void exitContinueStmt(EMxStarParser.ContinueStmtContext ctx);
	/**
	 * Enter a parse tree produced by the {@code breakStmt}
	 * labeled alternative in {@link EMxStarParser#jumpStatement}.
	 * @param ctx the parse tree
	 */
	void enterBreakStmt(EMxStarParser.BreakStmtContext ctx);
	/**
	 * Exit a parse tree produced by the {@code breakStmt}
	 * labeled alternative in {@link EMxStarParser#jumpStatement}.
	 * @param ctx the parse tree
	 */
	void exitBreakStmt(EMxStarParser.BreakStmtContext ctx);
	/**
	 * Enter a parse tree produced by the {@code returnStmt}
	 * labeled alternative in {@link EMxStarParser#jumpStatement}.
	 * @param ctx the parse tree
	 */
	void enterReturnStmt(EMxStarParser.ReturnStmtContext ctx);
	/**
	 * Exit a parse tree produced by the {@code returnStmt}
	 * labeled alternative in {@link EMxStarParser#jumpStatement}.
	 * @param ctx the parse tree
	 */
	void exitReturnStmt(EMxStarParser.ReturnStmtContext ctx);
	/**
	 * Enter a parse tree produced by the {@code newExpr}
	 * labeled alternative in {@link EMxStarParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterNewExpr(EMxStarParser.NewExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code newExpr}
	 * labeled alternative in {@link EMxStarParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitNewExpr(EMxStarParser.NewExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code prefixExpr}
	 * labeled alternative in {@link EMxStarParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterPrefixExpr(EMxStarParser.PrefixExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code prefixExpr}
	 * labeled alternative in {@link EMxStarParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitPrefixExpr(EMxStarParser.PrefixExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code primaryExpr}
	 * labeled alternative in {@link EMxStarParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterPrimaryExpr(EMxStarParser.PrimaryExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code primaryExpr}
	 * labeled alternative in {@link EMxStarParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitPrimaryExpr(EMxStarParser.PrimaryExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code subscriptExpr}
	 * labeled alternative in {@link EMxStarParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterSubscriptExpr(EMxStarParser.SubscriptExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code subscriptExpr}
	 * labeled alternative in {@link EMxStarParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitSubscriptExpr(EMxStarParser.SubscriptExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code suffixExpr}
	 * labeled alternative in {@link EMxStarParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterSuffixExpr(EMxStarParser.SuffixExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code suffixExpr}
	 * labeled alternative in {@link EMxStarParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitSuffixExpr(EMxStarParser.SuffixExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code binaryExpr}
	 * labeled alternative in {@link EMxStarParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterBinaryExpr(EMxStarParser.BinaryExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code binaryExpr}
	 * labeled alternative in {@link EMxStarParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitBinaryExpr(EMxStarParser.BinaryExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code memberAccessExpr}
	 * labeled alternative in {@link EMxStarParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterMemberAccessExpr(EMxStarParser.MemberAccessExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code memberAccessExpr}
	 * labeled alternative in {@link EMxStarParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitMemberAccessExpr(EMxStarParser.MemberAccessExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code funcCallExpr}
	 * labeled alternative in {@link EMxStarParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterFuncCallExpr(EMxStarParser.FuncCallExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code funcCallExpr}
	 * labeled alternative in {@link EMxStarParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitFuncCallExpr(EMxStarParser.FuncCallExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code assignExpr}
	 * labeled alternative in {@link EMxStarParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterAssignExpr(EMxStarParser.AssignExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code assignExpr}
	 * labeled alternative in {@link EMxStarParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitAssignExpr(EMxStarParser.AssignExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code identifierExpr}
	 * labeled alternative in {@link EMxStarParser#primaryExpression}.
	 * @param ctx the parse tree
	 */
	void enterIdentifierExpr(EMxStarParser.IdentifierExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code identifierExpr}
	 * labeled alternative in {@link EMxStarParser#primaryExpression}.
	 * @param ctx the parse tree
	 */
	void exitIdentifierExpr(EMxStarParser.IdentifierExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code thisExpr}
	 * labeled alternative in {@link EMxStarParser#primaryExpression}.
	 * @param ctx the parse tree
	 */
	void enterThisExpr(EMxStarParser.ThisExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code thisExpr}
	 * labeled alternative in {@link EMxStarParser#primaryExpression}.
	 * @param ctx the parse tree
	 */
	void exitThisExpr(EMxStarParser.ThisExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code constExpr}
	 * labeled alternative in {@link EMxStarParser#primaryExpression}.
	 * @param ctx the parse tree
	 */
	void enterConstExpr(EMxStarParser.ConstExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code constExpr}
	 * labeled alternative in {@link EMxStarParser#primaryExpression}.
	 * @param ctx the parse tree
	 */
	void exitConstExpr(EMxStarParser.ConstExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code subExpr}
	 * labeled alternative in {@link EMxStarParser#primaryExpression}.
	 * @param ctx the parse tree
	 */
	void enterSubExpr(EMxStarParser.SubExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code subExpr}
	 * labeled alternative in {@link EMxStarParser#primaryExpression}.
	 * @param ctx the parse tree
	 */
	void exitSubExpr(EMxStarParser.SubExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code intConst}
	 * labeled alternative in {@link EMxStarParser#constant}.
	 * @param ctx the parse tree
	 */
	void enterIntConst(EMxStarParser.IntConstContext ctx);
	/**
	 * Exit a parse tree produced by the {@code intConst}
	 * labeled alternative in {@link EMxStarParser#constant}.
	 * @param ctx the parse tree
	 */
	void exitIntConst(EMxStarParser.IntConstContext ctx);
	/**
	 * Enter a parse tree produced by the {@code stringConst}
	 * labeled alternative in {@link EMxStarParser#constant}.
	 * @param ctx the parse tree
	 */
	void enterStringConst(EMxStarParser.StringConstContext ctx);
	/**
	 * Exit a parse tree produced by the {@code stringConst}
	 * labeled alternative in {@link EMxStarParser#constant}.
	 * @param ctx the parse tree
	 */
	void exitStringConst(EMxStarParser.StringConstContext ctx);
	/**
	 * Enter a parse tree produced by the {@code nullLiteral}
	 * labeled alternative in {@link EMxStarParser#constant}.
	 * @param ctx the parse tree
	 */
	void enterNullLiteral(EMxStarParser.NullLiteralContext ctx);
	/**
	 * Exit a parse tree produced by the {@code nullLiteral}
	 * labeled alternative in {@link EMxStarParser#constant}.
	 * @param ctx the parse tree
	 */
	void exitNullLiteral(EMxStarParser.NullLiteralContext ctx);
	/**
	 * Enter a parse tree produced by the {@code boolConst}
	 * labeled alternative in {@link EMxStarParser#constant}.
	 * @param ctx the parse tree
	 */
	void enterBoolConst(EMxStarParser.BoolConstContext ctx);
	/**
	 * Exit a parse tree produced by the {@code boolConst}
	 * labeled alternative in {@link EMxStarParser#constant}.
	 * @param ctx the parse tree
	 */
	void exitBoolConst(EMxStarParser.BoolConstContext ctx);
	/**
	 * Enter a parse tree produced by {@link EMxStarParser#nonArrayTypeCreator}.
	 * @param ctx the parse tree
	 */
	void enterNonArrayTypeCreator(EMxStarParser.NonArrayTypeCreatorContext ctx);
	/**
	 * Exit a parse tree produced by {@link EMxStarParser#nonArrayTypeCreator}.
	 * @param ctx the parse tree
	 */
	void exitNonArrayTypeCreator(EMxStarParser.NonArrayTypeCreatorContext ctx);
	/**
	 * Enter a parse tree produced by the {@code errorCreator}
	 * labeled alternative in {@link EMxStarParser#creator}.
	 * @param ctx the parse tree
	 */
	void enterErrorCreator(EMxStarParser.ErrorCreatorContext ctx);
	/**
	 * Exit a parse tree produced by the {@code errorCreator}
	 * labeled alternative in {@link EMxStarParser#creator}.
	 * @param ctx the parse tree
	 */
	void exitErrorCreator(EMxStarParser.ErrorCreatorContext ctx);
	/**
	 * Enter a parse tree produced by the {@code arrayCreator}
	 * labeled alternative in {@link EMxStarParser#creator}.
	 * @param ctx the parse tree
	 */
	void enterArrayCreator(EMxStarParser.ArrayCreatorContext ctx);
	/**
	 * Exit a parse tree produced by the {@code arrayCreator}
	 * labeled alternative in {@link EMxStarParser#creator}.
	 * @param ctx the parse tree
	 */
	void exitArrayCreator(EMxStarParser.ArrayCreatorContext ctx);
	/**
	 * Enter a parse tree produced by the {@code nonArrayCreator}
	 * labeled alternative in {@link EMxStarParser#creator}.
	 * @param ctx the parse tree
	 */
	void enterNonArrayCreator(EMxStarParser.NonArrayCreatorContext ctx);
	/**
	 * Exit a parse tree produced by the {@code nonArrayCreator}
	 * labeled alternative in {@link EMxStarParser#creator}.
	 * @param ctx the parse tree
	 */
	void exitNonArrayCreator(EMxStarParser.NonArrayCreatorContext ctx);
	/**
	 * Enter a parse tree produced by {@link EMxStarParser#parameterList}.
	 * @param ctx the parse tree
	 */
	void enterParameterList(EMxStarParser.ParameterListContext ctx);
	/**
	 * Exit a parse tree produced by {@link EMxStarParser#parameterList}.
	 * @param ctx the parse tree
	 */
	void exitParameterList(EMxStarParser.ParameterListContext ctx);
}