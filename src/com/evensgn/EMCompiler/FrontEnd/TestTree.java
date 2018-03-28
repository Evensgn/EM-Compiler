package com.evensgn.EMCompiler.FrontEnd;

import com.evensgn.EMCompiler.Parser.EMxStarLexer;
import com.evensgn.EMCompiler.Parser.EMxStarParser;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;
import java.io.*;

public class TestTree {
    public static void testTree() throws IOException {
        InputStream is = new FileInputStream("testcase/test.mx");
        System.out.println(is);
        CharStream input = CharStreams.fromStream(is);
        EMxStarLexer lexer = new EMxStarLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        EMxStarParser parser = new EMxStarParser(tokens);
        ParseTree tree = parser.program();

        System.out.println("LISP:");
        System.out.println(tree.toStringTree(parser));
        System.out.println();
    }
}
