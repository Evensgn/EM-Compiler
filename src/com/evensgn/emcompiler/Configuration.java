package com.evensgn.emcompiler;

/**
 * @author Zhou Fan
 * @since 2018/3/29
 */
public class Configuration {
    private static final int REG_SIZE = 8;
    private static final boolean ENABLE_FUNCTION_INLINE = true;

    public static String configInfo() {
        return "==== emcompiler Configuration ====\n" +
                String.format("Size of register: %d\n", REG_SIZE) +
                String.format("[Optimization] Enable function inline: %s\n", ENABLE_FUNCTION_INLINE ? "YES" : "NO");
    }

    public static int getRegSize() {
        return REG_SIZE;
    }

    public static boolean isEnableFunctionInline() {
        return ENABLE_FUNCTION_INLINE;
    }
}