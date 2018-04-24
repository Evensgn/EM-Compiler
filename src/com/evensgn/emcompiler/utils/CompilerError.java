package com.evensgn.emcompiler.utils;

import com.evensgn.emcompiler.ast.Location;

public class CompilerError extends Error {
    public CompilerError(Location location, String message) {
        super(String.format("[Compiler Error] at %s: %s", location.toString(), message));
    }

    public CompilerError(String message) {
        super(String.format("[Compiler Error]: %s", message));
    }
}
