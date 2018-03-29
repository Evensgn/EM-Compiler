package com.evensgn.EMCompiler;

public class Configuration {
    private static final int INT_SIZE = 4;

    public static String configInfo() {
        return "==== EMCompiler Configuration Information ====\n" +
                String.format("Size of int: %d\n", INT_SIZE);
    }
}
