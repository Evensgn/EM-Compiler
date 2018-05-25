package com.evensgn.emcompiler.ir;

import com.evensgn.emcompiler.ast.ExprNode;
import com.evensgn.emcompiler.ast.VarDeclNode;

public class GlobalVarInit {
    private String name;
    private ExprNode initExpr;

    public GlobalVarInit(String name, ExprNode initExpr) {
        this.name = name;
        this.initExpr = initExpr;
    }

    public String getName() {
        return name;
    }

    public ExprNode getInitExpr() {
        return initExpr;
    }
}