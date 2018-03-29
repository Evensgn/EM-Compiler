package com.evensgn.EMCompiler;

import com.evensgn.EMCompiler.Compiler.Compiler;

import java.io.*;

public class Main {
    private static final String VERSION = "0.0.0";

    private static void printHelp() {
        System.out.println("Usage: emcompiler [options] file...");
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

    public static void main(String[] args) throws Exception {
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
        }
        InputStream inS;
        OutputStream outS;
        if (inFile == null) inS = System.in;
        else inS = new FileInputStream(inFile);
        if (outFile == null) outS = System.out;
        else outS = new FileOutputStream(outFile);

        Compiler compiler = new Compiler(inS, outS);
        compiler.run();
    }
}