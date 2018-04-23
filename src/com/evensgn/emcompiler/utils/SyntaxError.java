package com.evensgn.emcompiler.utils;

import com.evensgn.emcompiler.ast.Location;

public class SyntaxError extends Error {
    public SyntaxError(Location location, String message) {
        super(String.format("[Syntax Error] at %s: %s", location.toString(), message));
    }
}
