package com.evensgn.emcompiler.compiler;

import com.evensgn.emcompiler.ast.Node;
import com.evensgn.emcompiler.ast.ProgramNode;
import com.evensgn.emcompiler.frontend.ASTBuilder;
import com.evensgn.emcompiler.parser.EMxStarLexer;
import com.evensgn.emcompiler.parser.EMxStarParser;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author Zhou Fan
 * @since 2018/3/29
 */
public class Compiler {
    private InputStream inS;
    private OutputStream outS;

    public Compiler(InputStream inS, OutputStream outS) {
        this.inS = inS;
        this.outS = outS;
    }

    private void testTree() throws Exception {
        CharStream input = CharStreams.fromStream(inS);
        EMxStarLexer lexer = new EMxStarLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        EMxStarParser parser = new EMxStarParser(tokens);
        ParseTree tree = parser.program();
        ASTBuilder astBuilder = new ASTBuilder();
        ProgramNode ast = (ProgramNode) astBuilder.visit(tree);
    }

    public void run() throws Exception {
        System.out.println("compiler is running");
        testTree();
        System.out.println("compiler finished.");
    }
}
