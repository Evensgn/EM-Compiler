// Generated from EMxStar.g4 by ANTLR 4.7.1

package com.evensgn.emcompiler.parser;

import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link EMxStarParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface EMxStarVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link EMxStarParser#program}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProgram(EMxStarParser.ProgramContext ctx);
	/**
	 * Visit a parse tree produced by {@link EMxStarParser#programSection}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProgramSection(EMxStarParser.ProgramSectionContext ctx);
	/**
	 * Visit a parse tree produced by {@link EMxStarParser#functionDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunctionDeclaration(EMxStarParser.FunctionDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link EMxStarParser#classDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitClassDeclaration(EMxStarParser.ClassDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link EMxStarParser#variableDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVariableDeclaration(EMxStarParser.VariableDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link EMxStarParser#variableDeclaratorList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVariableDeclaratorList(EMxStarParser.VariableDeclaratorListContext ctx);
	/**
	 * Visit a parse tree produced by {@link EMxStarParser#variableDeclarator}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVariableDeclarator(EMxStarParser.VariableDeclaratorContext ctx);
	/**
	 * Visit a parse tree produced by {@link EMxStarParser#memberDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMemberDeclaration(EMxStarParser.MemberDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link EMxStarParser#parameterDeclarationList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParameterDeclarationList(EMxStarParser.ParameterDeclarationListContext ctx);
	/**
	 * Visit a parse tree produced by {@link EMxStarParser#parameterDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParameterDeclaration(EMxStarParser.ParameterDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link EMxStarParser#typeTypeOrVoid}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTypeTypeOrVoid(EMxStarParser.TypeTypeOrVoidContext ctx);
	/**
	 * Visit a parse tree produced by the {@code arrayType}
	 * labeled alternative in {@link EMxStarParser#typeType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArrayType(EMxStarParser.ArrayTypeContext ctx);
	/**
	 * Visit a parse tree produced by the {@code nonArrayType}
	 * labeled alternative in {@link EMxStarParser#typeType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNonArrayType(EMxStarParser.NonArrayTypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link EMxStarParser#nonArrayTypeType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNonArrayTypeType(EMxStarParser.NonArrayTypeTypeContext ctx);
	/**
	 * Visit a parse tree produced by the {@code blockStmt}
	 * labeled alternative in {@link EMxStarParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBlockStmt(EMxStarParser.BlockStmtContext ctx);
	/**
	 * Visit a parse tree produced by the {@code exprStmt}
	 * labeled alternative in {@link EMxStarParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExprStmt(EMxStarParser.ExprStmtContext ctx);
	/**
	 * Visit a parse tree produced by the {@code condStmt}
	 * labeled alternative in {@link EMxStarParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCondStmt(EMxStarParser.CondStmtContext ctx);
	/**
	 * Visit a parse tree produced by the {@code loopStmt}
	 * labeled alternative in {@link EMxStarParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLoopStmt(EMxStarParser.LoopStmtContext ctx);
	/**
	 * Visit a parse tree produced by the {@code jumpStmt}
	 * labeled alternative in {@link EMxStarParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitJumpStmt(EMxStarParser.JumpStmtContext ctx);
	/**
	 * Visit a parse tree produced by the {@code blankStmt}
	 * labeled alternative in {@link EMxStarParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBlankStmt(EMxStarParser.BlankStmtContext ctx);
	/**
	 * Visit a parse tree produced by {@link EMxStarParser#block}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBlock(EMxStarParser.BlockContext ctx);
	/**
	 * Visit a parse tree produced by the {@code stmt}
	 * labeled alternative in {@link EMxStarParser#blockStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStmt(EMxStarParser.StmtContext ctx);
	/**
	 * Visit a parse tree produced by the {@code varDeclStmt}
	 * labeled alternative in {@link EMxStarParser#blockStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVarDeclStmt(EMxStarParser.VarDeclStmtContext ctx);
	/**
	 * Visit a parse tree produced by {@link EMxStarParser#conditionStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitConditionStatement(EMxStarParser.ConditionStatementContext ctx);
	/**
	 * Visit a parse tree produced by the {@code whileStmt}
	 * labeled alternative in {@link EMxStarParser#loopStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWhileStmt(EMxStarParser.WhileStmtContext ctx);
	/**
	 * Visit a parse tree produced by the {@code forStmt}
	 * labeled alternative in {@link EMxStarParser#loopStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitForStmt(EMxStarParser.ForStmtContext ctx);
	/**
	 * Visit a parse tree produced by the {@code continueStmt}
	 * labeled alternative in {@link EMxStarParser#jumpStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitContinueStmt(EMxStarParser.ContinueStmtContext ctx);
	/**
	 * Visit a parse tree produced by the {@code breakStmt}
	 * labeled alternative in {@link EMxStarParser#jumpStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBreakStmt(EMxStarParser.BreakStmtContext ctx);
	/**
	 * Visit a parse tree produced by the {@code returnStmt}
	 * labeled alternative in {@link EMxStarParser#jumpStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitReturnStmt(EMxStarParser.ReturnStmtContext ctx);
	/**
	 * Visit a parse tree produced by the {@code newExpr}
	 * labeled alternative in {@link EMxStarParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNewExpr(EMxStarParser.NewExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code prefixExpr}
	 * labeled alternative in {@link EMxStarParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrefixExpr(EMxStarParser.PrefixExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code primaryExpr}
	 * labeled alternative in {@link EMxStarParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrimaryExpr(EMxStarParser.PrimaryExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code subscriptExpr}
	 * labeled alternative in {@link EMxStarParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSubscriptExpr(EMxStarParser.SubscriptExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code suffixExpr}
	 * labeled alternative in {@link EMxStarParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSuffixExpr(EMxStarParser.SuffixExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code binaryExpr}
	 * labeled alternative in {@link EMxStarParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBinaryExpr(EMxStarParser.BinaryExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code memberAccessExpr}
	 * labeled alternative in {@link EMxStarParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMemberAccessExpr(EMxStarParser.MemberAccessExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code funcCallExpr}
	 * labeled alternative in {@link EMxStarParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFuncCallExpr(EMxStarParser.FuncCallExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code assignExpr}
	 * labeled alternative in {@link EMxStarParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssignExpr(EMxStarParser.AssignExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code identifierExpr}
	 * labeled alternative in {@link EMxStarParser#primaryExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIdentifierExpr(EMxStarParser.IdentifierExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code constExpr}
	 * labeled alternative in {@link EMxStarParser#primaryExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitConstExpr(EMxStarParser.ConstExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code subExpr}
	 * labeled alternative in {@link EMxStarParser#primaryExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSubExpr(EMxStarParser.SubExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code intConst}
	 * labeled alternative in {@link EMxStarParser#constant}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIntConst(EMxStarParser.IntConstContext ctx);
	/**
	 * Visit a parse tree produced by the {@code stringConst}
	 * labeled alternative in {@link EMxStarParser#constant}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStringConst(EMxStarParser.StringConstContext ctx);
	/**
	 * Visit a parse tree produced by the {@code nullLiteral}
	 * labeled alternative in {@link EMxStarParser#constant}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNullLiteral(EMxStarParser.NullLiteralContext ctx);
	/**
	 * Visit a parse tree produced by the {@code boolConst}
	 * labeled alternative in {@link EMxStarParser#constant}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBoolConst(EMxStarParser.BoolConstContext ctx);
	/**
	 * Visit a parse tree produced by the {@code errorCreator}
	 * labeled alternative in {@link EMxStarParser#creator}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitErrorCreator(EMxStarParser.ErrorCreatorContext ctx);
	/**
	 * Visit a parse tree produced by the {@code arrayCreator}
	 * labeled alternative in {@link EMxStarParser#creator}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArrayCreator(EMxStarParser.ArrayCreatorContext ctx);
	/**
	 * Visit a parse tree produced by the {@code nonArrayCreator}
	 * labeled alternative in {@link EMxStarParser#creator}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNonArrayCreator(EMxStarParser.NonArrayCreatorContext ctx);
	/**
	 * Visit a parse tree produced by {@link EMxStarParser#parameterList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParameterList(EMxStarParser.ParameterListContext ctx);
}