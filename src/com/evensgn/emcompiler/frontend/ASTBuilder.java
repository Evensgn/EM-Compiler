package com.evensgn.emcompiler.frontend;

import com.evensgn.emcompiler.ast.*;
import com.evensgn.emcompiler.compiler.Compiler;
import com.evensgn.emcompiler.parser.*;
import com.evensgn.emcompiler.type.*;
import com.evensgn.emcompiler.utils.CompilerError;
import com.evensgn.emcompiler.utils.SemanticError;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.TerminalNode;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class ASTBuilder extends EMxStarBaseVisitor<Node> {
    private final String ARRAY_CREATOR_IDX_NAME = "__array_idx_";
    private TypeNode typeForVarDecl;

    @Override
    public Node visitProgram(EMxStarParser.ProgramContext ctx) {
        List<DeclNode> decls = new ArrayList<>();
        if (ctx.programSection() != null) {
            for (ParserRuleContext programSection : ctx.programSection()) {
                Node decl = visit(programSection);
                if (decl instanceof VarDeclListNode) decls.addAll(((VarDeclListNode) decl).getDecls());
                else decls.add((DeclNode) decl);
            }
        }
        return new ProgramNode(decls, Location.fromCtx(ctx));
    }

    @Override
    public Node visitProgramSection(EMxStarParser.ProgramSectionContext ctx) {
        if (ctx.functionDeclaration() != null) return visit(ctx.functionDeclaration());
        else if (ctx.classDeclaration() != null) return visit(ctx.classDeclaration());
        else if (ctx.variableDeclaration() != null) return visit(ctx.variableDeclaration());
        else throw new CompilerError(Location.fromCtx(ctx), "Invalid program section");
    }

    @Override
    public Node visitFunctionDeclaration(EMxStarParser.FunctionDeclarationContext ctx) {
        TypeNode returnType;
        if (ctx.typeTypeOrVoid() != null) returnType = (TypeNode) visit(ctx.typeTypeOrVoid());
        else returnType = null;
        String name = ctx.Identifier().getText();
        List<VarDeclNode> parameterList = new ArrayList<>();
        Node paraDecl;
        if (ctx.parameterDeclarationList() != null) {
            for (ParserRuleContext parameterDeclaration : ctx.parameterDeclarationList().parameterDeclaration()) {
                paraDecl = visit(parameterDeclaration);
                parameterList.add((VarDeclNode) paraDecl);
            }
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
        if (ctx.memberDeclaration() != null) {
            for (ParserRuleContext memberDeclaration : ctx.memberDeclaration()) {
                memberDecl = visit(memberDeclaration);
                if (memberDecl instanceof VarDeclListNode) varMember.addAll(((VarDeclListNode) memberDecl).getDecls());
                else if (memberDecl instanceof FuncDeclNode) funcMember.add((FuncDeclNode) memberDecl);
                else throw new CompilerError(Location.fromCtx(ctx), "Invalid member declaration");
            }
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
        else if (ctx.variableDeclaration() != null) return visit(ctx.variableDeclaration());
        else throw new CompilerError(Location.fromCtx(ctx), "Invalid member declaration");
    }

    // no use
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
        else return new TypeNode(VoidType.getInstance(), Location.fromCtx(ctx));
    }

    @Override
    public Node visitArrayType(EMxStarParser.ArrayTypeContext ctx) {
        TypeNode baseType = (TypeNode) visit(ctx.typeType());
        return new TypeNode(new ArrayType(baseType.getType()), Location.fromCtx(ctx));
    }

    @Override
    public Node visitNonArrayType(EMxStarParser.NonArrayTypeContext ctx) {
        return visit(ctx.nonArrayTypeType());
    }

    private Type getTypeFromNonArrayType(TerminalNode anInt, TerminalNode bool, TerminalNode string, TerminalNode identifier, Location location) {
        Type type;
        if (anInt != null) type = IntType.getInstance();
        else if (bool != null) type = BoolType.getInstance();
        else if (string != null) type = StringType.getInstance();
        else if (identifier != null) type = new ClassType(identifier.getText());
        else throw new CompilerError(location, "Invalid primitive type");
        return type;
    }

    @Override
    public Node visitNonArrayTypeType(EMxStarParser.NonArrayTypeTypeContext ctx) {
        if (ctx.Identifier() != null) return new TypeNode(new ClassType(ctx.Identifier().getText()), Location.fromCtx(ctx));
        Type type;
        type = getTypeFromNonArrayType(ctx.Int(), ctx.Bool(), ctx.String(), ctx.Identifier(), Location.fromCtx(ctx));
        return new TypeNode(type, Location.fromCtx(ctx));
    }

    @Override
    public Node visitNonArrayTypeCreator(EMxStarParser.NonArrayTypeCreatorContext ctx) {
        if (ctx.Identifier() != null) return new TypeNode(new ClassType(ctx.Identifier().getText()), Location.fromCtx(ctx));
        Type type;
        type = getTypeFromNonArrayType(ctx.Int(), ctx.Bool(), ctx.String(), ctx.Identifier(), Location.fromCtx(ctx));
        return new TypeNode(type, Location.fromCtx(ctx));
    }

    @Override
    public Node visitBlockStmt(EMxStarParser.BlockStmtContext ctx) {
        return visit(ctx.block());
    }

    @Override
    public Node visitExprStmt(EMxStarParser.ExprStmtContext ctx) {
        ExprNode expr = (ExprNode) visit(ctx.expression());
        return new ExprStmtNode(expr, Location.fromCtx(ctx));
    }

    @Override
    public Node visitCondStmt(EMxStarParser.CondStmtContext ctx) {
        return visit(ctx.conditionStatement());
    }

    @Override
    public Node visitLoopStmt(EMxStarParser.LoopStmtContext ctx) {
        return visit(ctx.loopStatement());
    }

    @Override
    public Node visitJumpStmt(EMxStarParser.JumpStmtContext ctx) {
        return visit(ctx.jumpStatement());
    }

    @Override
    public Node visitBlankStmt(EMxStarParser.BlankStmtContext ctx) {
        return null;
    }

    @Override
    public Node visitBlock(EMxStarParser.BlockContext ctx) {
        List<Node> stmtsAndVarDecls = new ArrayList<>();
        if (ctx.blockStatement() != null) {
            for (ParserRuleContext blockStatement : ctx.blockStatement()) {
                Node node = visit(blockStatement);
                if (node != null) {
                    if (node instanceof VarDeclListNode)
                        stmtsAndVarDecls.addAll(((VarDeclListNode) node).getDecls());
                    else stmtsAndVarDecls.add(node);
                }
            }
        }
        return new BlockStmtNode(stmtsAndVarDecls, Location.fromCtx(ctx));
    }

    @Override
    public Node visitStmt(EMxStarParser.StmtContext ctx) {
        return visit(ctx.statement());
    }

    @Override
    public Node visitVarDeclStmt(EMxStarParser.VarDeclStmtContext ctx) {
        return visit(ctx.variableDeclaration());
    }

    @Override
    public Node visitConditionStatement(EMxStarParser.ConditionStatementContext ctx) {
        ExprNode cond = (ExprNode) visit(ctx.expression());
        StmtNode thenStmt = (StmtNode) visit(ctx.thenStmt);
        StmtNode elseStmt;
        if (ctx.elseStmt != null) elseStmt = (StmtNode) visit(ctx.elseStmt);
        else elseStmt = null;
        return new CondStmtNode(cond, thenStmt, elseStmt, Location.fromCtx(ctx));
    }

    @Override
    public Node visitWhileStmt(EMxStarParser.WhileStmtContext ctx) {
        ExprNode cond = (ExprNode) visit(ctx.expression());
        StmtNode stmt = (StmtNode) visit(ctx.statement());
        return new WhileStmtNode(cond, stmt, Location.fromCtx(ctx));
    }

    @Override
    public Node visitForStmt(EMxStarParser.ForStmtContext ctx) {
        ExprNode init, cond, step;
        if (ctx.init != null) init = (ExprNode) visit(ctx.init);
        else init = null;
        if (ctx.cond != null) cond = (ExprNode) visit(ctx.cond);
        else cond = null;
        if (ctx.step != null) step = (ExprNode) visit(ctx.step);
        else step = null;
        StmtNode stmt = (StmtNode) visit(ctx.statement());
        return new ForStmtNode(init, cond, step, stmt, Location.fromCtx(ctx));
    }

    @Override
    public Node visitContinueStmt(EMxStarParser.ContinueStmtContext ctx) {
        return new ContinueStmtNode(Location.fromCtx(ctx));
    }

    @Override
    public Node visitBreakStmt(EMxStarParser.BreakStmtContext ctx) {
        return new BreakStmtNode(Location.fromCtx(ctx));
    }

    @Override
    public Node visitReturnStmt(EMxStarParser.ReturnStmtContext ctx) {
        ExprNode expr;
        if (ctx.expression() != null) expr = (ExprNode) visit(ctx.expression());
        else expr = null;
        return new ReturnStmtNode(expr, Location.fromCtx(ctx));
    }

    @Override
    public Node visitNewExpr(EMxStarParser.NewExprContext ctx) {
        return visit(ctx.creator());
    }

    @Override
    public Node visitPrefixExpr(EMxStarParser.PrefixExprContext ctx) {
        PrefixExprNode.PrefixOps op;
        switch (ctx.op.getText()) {
            case "++" : op = PrefixExprNode.PrefixOps.PREFIX_INC; break;
            case "--" : op = PrefixExprNode.PrefixOps.PREFIX_DEC; break;
            case "+"  : op = PrefixExprNode.PrefixOps.POS; break;
            case "-"  : op = PrefixExprNode.PrefixOps.NEG; break;
            case "!"  : op = PrefixExprNode.PrefixOps.LOGIC_NOT; break;
            case "~"  : op = PrefixExprNode.PrefixOps.BITWISE_NOT; break;
            default   : throw new CompilerError(Location.fromCtx(ctx), "Invalid prefix operator");
        }
        ExprNode expr = (ExprNode) visit(ctx.expression());
        return new PrefixExprNode(op, expr, Location.fromCtx(ctx));
    }

    @Override
    public Node visitPrimaryExpr(EMxStarParser.PrimaryExprContext ctx) {
        return visit(ctx.primaryExpression());
    }

    @Override
    public Node visitSubscriptExpr(EMxStarParser.SubscriptExprContext ctx) {
        ExprNode arr = (ExprNode) visit(ctx.arr);
        ExprNode sub = (ExprNode) visit(ctx.sub);
        return new SubscriptExprNode(arr, sub, Location.fromCtx(ctx));
    }

    @Override
    public Node visitSuffixExpr(EMxStarParser.SuffixExprContext ctx) {
        SuffixExprNode.SuffixOps op;
        switch (ctx.op.getText()) {
            case "++" : op = SuffixExprNode.SuffixOps.SUFFIX_INC; break;
            case "--" : op = SuffixExprNode.SuffixOps.SUFFIX_DEC; break;
            default   : throw new CompilerError(Location.fromCtx(ctx), "Invalid suffix operator");
        }
        ExprNode expr = (ExprNode) visit(ctx.expression());
        return new SuffixExprNode(op, expr, Location.fromCtx(ctx));
    }

    @Override
    public Node visitBinaryExpr(EMxStarParser.BinaryExprContext ctx) {
        BinaryExprNode.BinaryOps op;
        switch (ctx.op.getText()) {
            case "*"  : op = BinaryExprNode.BinaryOps.MUL; break;
            case "/"  : op = BinaryExprNode.BinaryOps.DIV; break;
            case "%"  : op = BinaryExprNode.BinaryOps.MOD; break;
            case "+"  : op = BinaryExprNode.BinaryOps.ADD; break;
            case "-"  : op = BinaryExprNode.BinaryOps.SUB; break;
            case "<<" : op = BinaryExprNode.BinaryOps.SHL; break;
            case ">>" : op = BinaryExprNode.BinaryOps.SHR; break;
            case "<"  : op = BinaryExprNode.BinaryOps.LESS; break;
            case ">"  : op = BinaryExprNode.BinaryOps.GREATER; break;
            case "<=" : op = BinaryExprNode.BinaryOps.LESS_EQUAL; break;
            case ">=" : op = BinaryExprNode.BinaryOps.GREATER_EQUAL; break;
            case "==" : op = BinaryExprNode.BinaryOps.EQUAL; break;
            case "!=" : op = BinaryExprNode.BinaryOps.INEQUAL; break;
            case "&"  : op = BinaryExprNode.BinaryOps.BITWISE_AND; break;
            case "^"  : op = BinaryExprNode.BinaryOps.BITWISE_XOR; break;
            case "|"  : op = BinaryExprNode.BinaryOps.BITWISE_OR; break;
            case "&&" : op = BinaryExprNode.BinaryOps.LOGIC_AND; break;
            case "||" : op = BinaryExprNode.BinaryOps.LOGIC_OR; break;
            default   : throw new CompilerError(Location.fromCtx(ctx), "Invalid binary operator");
        }
        ExprNode lhs = (ExprNode) visit(ctx.lhs);
        ExprNode rhs = (ExprNode) visit(ctx.rhs);
        return new BinaryExprNode(op, lhs, rhs, Location.fromCtx(ctx));
    }

    @Override
    public Node visitMemberAccessExpr(EMxStarParser.MemberAccessExprContext ctx) {
        ExprNode expr = (ExprNode) visit(ctx.expression());
        String member = ctx.Identifier().getText();
        return new MemberAccessExprNode(expr, member, Location.fromCtx(ctx));
    }

    @Override
    public Node visitFuncCallExpr(EMxStarParser.FuncCallExprContext ctx) {
        ExprNode func = (ExprNode) visit(ctx.expression());
        List<ExprNode> args = new ArrayList<>();
        if (ctx.parameterList() != null) {
            for (ParserRuleContext parameter : ctx.parameterList().expression()) {
                args.add((ExprNode) visit(parameter));
            }
        }
        return new FuncCallExprNode(func, args, Location.fromCtx(ctx));
    }

    @Override
    public Node visitAssignExpr(EMxStarParser.AssignExprContext ctx) {
        ExprNode lhs = (ExprNode) visit(ctx.lhs);
        ExprNode rhs = (ExprNode) visit(ctx.rhs);
        return new AssignExprNode(lhs, rhs, Location.fromCtx(ctx));
    }

    @Override
    public Node visitIdentifierExpr(EMxStarParser.IdentifierExprContext ctx) {
        return new IdentifierExprNode(ctx.Identifier().getText(), Location.fromCtx(ctx));
    }

    @Override
    public Node visitThisExpr(EMxStarParser.ThisExprContext ctx) {
        return new ThisExprNode(Location.fromCtx(ctx));
    }

    @Override
    public Node visitConstExpr(EMxStarParser.ConstExprContext ctx) {
        return visit(ctx.constant());
    }

    @Override
    public Node visitSubExpr(EMxStarParser.SubExprContext ctx) {
        return visit(ctx.expression());
    }

    @Override
    public Node visitIntConst(EMxStarParser.IntConstContext ctx) {
        int value;
        try {
            value = Integer.parseInt(ctx.getText());
        }
        catch (Exception e) {
            throw new SemanticError(Location.fromCtx(ctx), "Invalid integer constant: " + e);
        }
        return new IntConstExprNode(value, Location.fromCtx(ctx));
    }

    private String unescape(String str) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < str.length(); ++i) {
            if (i + 1 < str.length() && str.charAt(i) == '\\') {
                switch (str.charAt(i + 1)) {
                    case '\\': sb.append('\\'); break;
                    case 'n': sb.append('\n'); break;
                    case '\"': sb.append('\"'); break;
                    default: throw new CompilerError("invalid escaped string");
                }
                ++i;
            } else {
                sb.append(str.charAt(i));
            }
        }
        return sb.toString();
    }

    @Override
    public Node visitStringConst(EMxStarParser.StringConstContext ctx) {
        String str = ctx.getText();
        return new StringConstExprNode(unescape(str.substring(1, str.length() - 1)), Location.fromCtx(ctx));
    }

    @Override
    public Node visitNullLiteral(EMxStarParser.NullLiteralContext ctx) {
        return new NullExprNode(Location.fromCtx(ctx));
    }

    @Override
    public Node visitBoolConst(EMxStarParser.BoolConstContext ctx) {
        boolean value;
        switch (ctx.getText()) {
            case "true"  : value = true; break;
            case "false" : value = false; break;
            default      : throw new CompilerError(Location.fromCtx(ctx), "Invalid boolean constant");
        }
        return new BoolConstExprNode(value, Location.fromCtx(ctx));
    }

    @Override
    public Node visitErrorCreator(EMxStarParser.ErrorCreatorContext ctx) {
        throw new SemanticError(Location.fromCtx(ctx), "Invalid creator for new expression");
    }

    @Override
    public Node visitArrayCreator(EMxStarParser.ArrayCreatorContext ctx) {
        TypeNode newType = (TypeNode) visit(ctx.nonArrayTypeType());
        List<ExprNode> dims = new ArrayList<>();
        for (ParserRuleContext dim : ctx.expression()) {
            dims.add((ExprNode) visit(dim));
        }
        int numDim = (ctx.getChildCount() - 1 - dims.size()) / 2;
        for (int i = 0; i < numDim; ++i) newType.setType(new ArrayType(newType.getType()));
        return new NewExprNode(newType, dims, numDim, Location.fromCtx(ctx));
    }

    @Override
    public Node visitNonArrayCreator(EMxStarParser.NonArrayCreatorContext ctx) {
        TypeNode newType = (TypeNode) visit(ctx.nonArrayTypeCreator());
        return new NewExprNode(newType, null, 0, Location.fromCtx(ctx));
    }

    // no use
    @Override
    public Node visitParameterList(EMxStarParser.ParameterListContext ctx) {
        return super.visitParameterList(ctx);
    }
}
