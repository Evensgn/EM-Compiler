package com.evensgn.emcompiler;

/**
 * @author Zhou Fan
 * @since 2018/3/29
 */
public class Configuration {
    private static final int REG_SIZE = 8;

    public static String configInfo() {
        return "==== emcompiler Configuration ====\n" +
                String.format("Size of register: %d\n", REG_SIZE);
    }

    public static int getRegSize() {
        return REG_SIZE;
    }
}