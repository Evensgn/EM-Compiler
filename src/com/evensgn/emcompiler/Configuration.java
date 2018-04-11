package com.evensgn.emcompiler;

/**
 * @author Zhou Fan
 * @since 2018/3/29
 */
public class Configuration {
    private static final int INT_SIZE = 4;

    public static String configInfo() {
        return "==== emcompiler Configuration ====\n" +
                String.format("Size of int: %d\n", INT_SIZE);
    }
}
