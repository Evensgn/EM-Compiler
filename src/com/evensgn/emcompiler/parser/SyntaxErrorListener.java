package com.evensgn.emcompiler.parser;

import com.evensgn.emcompiler.ast.Location;
import com.evensgn.emcompiler.utils.SyntaxError;
import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;

public class SyntaxErrorListener extends BaseErrorListener {
    @Override
    public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol, int line, int charPositionInLine, String msg, RecognitionException e) {
        throw new SyntaxError(new Location(line, charPositionInLine), msg);
    }
}