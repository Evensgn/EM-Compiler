package com.evensgn.EMCompiler;

import com.evensgn.EMCompiler.FrontEnd.*;
import com.evensgn.EMCompiler.Parser.EMxStarLexer;
import com.evensgn.EMCompiler.Parser.EMxStarParser;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import java.io.*;

public class Main {
    private static final String VERSION = "0.0.0";
    private static InputStream inS;
    private static OutputStream outS;

    private static void testTree() throws IOException {
        CharStream input = CharStreams.fromStream(inS);
        EMxStarLexer lexer = new EMxStarLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        EMxStarParser parser = new EMxStarParser(tokens);
        ParseTree tree = parser.program();
        ASTBuilder astBuilder = new ASTBuilder();
        new ParseTreeWalker().walk(astBuilder, tree);
    }

    private static void printHelp() {
        System.out.println("Usage: EMCompiler [options] file...");
        System.out.println("Options:");
        System.out.println("  -h, --help                    Display this information");
        System.out.println("  -v, --version                 Display compiler version information");
        System.out.println("  -c, --config                  Display compiler configurations");
        System.out.println("  -o <file>                     Place the output into <file>");
    }

    private static void printVersion() {
        System.out.printf("EMCompiler (Evensgn Mx* Compiler) %s\n", VERSION);
    }

    private static void printConfig() {
        System.out.println(Configuration.configInfo());
    }

    private static void errorArgs() {
        System.out.println("Error: Got unknown arguments");
        printHelp();
    }

    public static void main(String[] args) throws IOException {
        String inFile = null, outFile = null;
        for (int i = 0; i < args.length; ++i) {
            String arg = args[i];
            switch (arg) {
                case "-h":
                case "--help":
                    printHelp();
                    break;

                case "-v":
                case "--version":
                    printVersion();
                    break;

                case "-c":
                case "--config":
                    printConfig();
                    break;

                case "-o":
                    if (i + 1 < args.length) outFile = args[++i];
                    else errorArgs();
                    break;

                default:
                    if (inFile == null) inFile = arg;
                    else errorArgs();
            }

            if (inFile == null) Main.inS = System.in;
            else Main.inS = new FileInputStream(inFile);
            if (outFile == null) Main.outS = System.out;
            else Main.outS = new FileOutputStream(outFile);
        }
        //testTree();
    }
}