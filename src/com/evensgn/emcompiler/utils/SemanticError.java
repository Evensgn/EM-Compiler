package com.evensgn.emcompiler.utils;

import com.evensgn.emcompiler.ast.Location;

public class SemanticError extends Error {
    public SemanticError(Location location, String message) {
        super(String.format("[Semantic Error] at %s: %s", location.toString(), message));
    }

    public SemanticError(String message) {
        super(String.format("[Semantic Error]: %s", message));
    }
}
