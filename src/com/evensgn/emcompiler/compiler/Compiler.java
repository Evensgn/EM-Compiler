package com.evensgn.emcompiler.compiler;

import com.evensgn.emcompiler.Configuration;
import com.evensgn.emcompiler.ast.ProgramNode;
import com.evensgn.emcompiler.backend.*;
import com.evensgn.emcompiler.frontend.*;
import com.evensgn.emcompiler.ir.IRBinaryOperation;
import com.evensgn.emcompiler.ir.IRFunction;
import com.evensgn.emcompiler.ir.IRRoot;
import com.evensgn.emcompiler.nasm.NASMRegisterSet;
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
    private PrintStream astOutS, irOutS, nasmOutS;
    private ProgramNode ast;

    public Compiler(InputStream inS, PrintStream astOutS, PrintStream irOutS, PrintStream nasmOutS) {
        this.inS = inS;
        this.astOutS = astOutS;
        this.irOutS = irOutS;
        this.nasmOutS = nasmOutS;
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
        buildAST();
        if (astOutS != null) new ASTPrinter(astOutS).visit(ast);
        GlobalScopePreScanner globalScopePreScanner = new GlobalScopePreScanner();
        globalScopePreScanner.visit(ast);
        ClassVarMemberScanner classVarMemberScanner = new ClassVarMemberScanner(globalScopePreScanner.getScope());
        classVarMemberScanner.visit(ast);
        FunctionScopeScanner functionScopeScanner = new FunctionScopeScanner(classVarMemberScanner.getGlobalScope());
        functionScopeScanner.visit(ast);
        IRBuilder irBuilder = new IRBuilder(functionScopeScanner.getGlobalScope());
        irBuilder.visit(ast);
        IRRoot ir = irBuilder.getIR();
        new TwoRegOpTransformer(ir).run();
        if (Configuration.isEnableFunctionInline()) new FunctionInlineProcessor(ir).run();
        if (irOutS != null) new IRPrinter(irOutS).visit(ir);
        new StaticDataProcessor(ir).run();
        new RegLivelinessAnalysis(ir).run();
        new RegisterPreprocessor(ir).run();
        new RegisterAllocator(ir, NASMRegisterSet.generalRegs).run();
        new NASMTransformer(ir).run();
        new NASMPrinter(nasmOutS).visit(ir);
    }
}