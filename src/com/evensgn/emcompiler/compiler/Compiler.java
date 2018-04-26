package com.evensgn.emcompiler.compiler;

import com.evensgn.emcompiler.ast.ProgramNode;
import com.evensgn.emcompiler.frontend.*;
import com.evensgn.emcompiler.parser.EMxStarLexer;
import com.evensgn.emcompiler.parser.EMxStarParser;
import com.evensgn.emcompiler.parser.SyntaxErrorListener;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import java.io.InputStream;
import java.io.PrintStream;

/**
 * @author Zhou Fan
 * @since 2018/3/29
 */
public class Compiler {
    private InputStream inS;
    private PrintStream outS;
    private ProgramNode ast;

    public Compiler(InputStream inS, PrintStream outS) {
        this.inS = inS;
        this.outS = outS;
    }

    private void buildAST() throws Exception {
        CharStream input = CharStreams.fromStream(inS);
        EMxStarLexer lexer = new EMxStarLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        EMxStarParser parser = new EMxStarParser(tokens);
        parser.removeErrorListeners();
        parser.addErrorListener(new SyntaxErrorListener());
        ParseTree tree = parser.program();
        ASTBuilder astBuilder = new ASTBuilder();
        ast = (ProgramNode) astBuilder.visit(tree);
    }

    public void compile() throws Exception {
        System.out.println("compiler is running");
        buildAST();
        ASTPrinter astPrinter = new ASTPrinter(outS);
        astPrinter.visit(ast);
        GlobalScopePreScanner globalScopePreScanner = new GlobalScopePreScanner();
        globalScopePreScanner.visit(ast);
        ClassVarMemberScanner classVarMemberScanner = new ClassVarMemberScanner(globalScopePreScanner.getScope());
        classVarMemberScanner.visit(ast);
        FunctionScopeScanner functionScopeScanner = new FunctionScopeScanner(classVarMemberScanner.getGlobalScope());
        functionScopeScanner.visit(ast);
        System.out.println("compiler finished.");
    }
}
