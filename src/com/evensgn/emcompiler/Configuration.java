package com.evensgn.emcompiler;

public class Configuration {
    private static final int INT_SIZE = 4;

    public static String configInfo() {
        return "==== emcompiler Configuration ====\n" +
                String.format("Size of int: %d\n", INT_SIZE);
    }
}
