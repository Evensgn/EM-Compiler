package com.evensgn.emcompiler;

import com.evensgn.emcompiler.compiler.Compiler;

import java.io.*;

/**
 * @author Zhou Fan
 * @since 2018/3/29
 */
public class Main {
    private static final String VERSION = "0.0.0";

    private static void printHelp() {
        System.out.println("Usage: emcompiler [options] file...");
        System.out.println("Options:");
        System.out.println("  -h, --help                    Display this information");
        System.out.println("  -v, --version                 Display compiler version information");
        System.out.println("  -c, --config                  Display compiler configurations");
        System.out.println("  -o <file>                     Place the output nasm code into <file>");
        System.out.println("  --ast <file>                  Output abstract syntax tree into <file>");
        System.out.println("  --ir <file>                   Output intermediate representation into <file>");
    }

    private static void printVersion() {
        System.out.printf("emcompiler (Evensgn Mx* compiler) %s\n", VERSION);
    }

    private static void printConfig() {
        System.out.println(Configuration.configInfo());
    }

    private static void errorArgs() {
        System.out.println("Error: invalid arguments");
        printHelp();
        System.exit(1);
    }

    public static void main(String[] args) throws Exception {
        String inFile = null, astOutFile = null, irOutFile = null, nasmOutFile = null;
        boolean isPrintHelp = false, isPrintVersion = false, isPrintConfig = false;
        for (int i = 0; i < args.length; ++i) {
            String arg = args[i];
            switch (arg) {
                case "-h":
                case "--help":
                    isPrintHelp = true;
                    break;

                case "-v":
                case "--version":
                    isPrintVersion = true;
                    break;

                case "-c":
                case "--config":
                    isPrintConfig = true;
                    break;

                case "-o":
                    if (i + 1 < args.length) nasmOutFile = args[++i];
                    else errorArgs();
                    break;

                case "--ast":
                    if (i + 1 < args.length) astOutFile = args[++i];
                    else errorArgs();
                    break;

                case "--ir":
                    if (i + 1 < args.length) irOutFile = args[++i];
                    else errorArgs();
                    break;

                default:
                    if (inFile == null) inFile = arg;
                    else errorArgs();
            }
        }

        //irOutFile = "testcase/ir.txt";
        if (isPrintVersion) printVersion();
        if (isPrintHelp) printHelp();
        if (isPrintConfig) printConfig();

        InputStream inS;
        PrintStream astOutS, irOutS, nasmOutS;
        if (inFile == null) inS = System.in;
        else inS = new FileInputStream(inFile);
        if (astOutFile == null) astOutS = null;
        else astOutS = new PrintStream(new FileOutputStream(astOutFile));
        if (irOutFile == null) irOutS = null;
        else irOutS = new PrintStream(new FileOutputStream(irOutFile));
        if (nasmOutFile == null) nasmOutS = System.out;
        else nasmOutS = new PrintStream(new FileOutputStream(nasmOutFile));

        Compiler compiler = new Compiler(inS, astOutS, irOutS, nasmOutS);
        try {
            compiler.compile();
        }
        catch (Error e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
    }
}