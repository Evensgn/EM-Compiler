grammar EMxStar; // Evensgn-Mx*

@header {
package com.evensgn.EMCompiler.Parser;
}

program
    :   programSection* EOF
    ;

programSection
    :   functionDeclaration     # funcDecl
    |   classDeclaration        # classDecl
    |   variableDeclaration     # varDecl
    ;

// ---- Declaration -----
functionDeclaration
    :   typeTypeOrVoid Identifier '(' parameterDeclarationList? ')' block
    ;

classDeclaration
    :   Class Identifier '{' memberDeclaration* '}'
    ;

variableDeclaration
    :   typeType variableDeclaratorList ';'
    ;

variableDeclaratorList
    :   variableDeclarator (',' variableDeclarator)*
    ;

variableDeclarator
    :   Identifier ('=' expression)?
    ;

memberDeclaration
    :   functionDeclaration | variableDeclaration
    ;

parameterDeclarationList
    :   parameterDeclaration (',' parameterDeclaration)*
    ;

parameterDeclaration
    :   typeType Identifier
    ;

typeTypeOrVoid
    :   typeType
    |   Void
    ;

typeType
    :   typeType '[' ']'        # arrayType
    |   nonArrayTypeType        # nonArrayType
    ;

// ---- Statement ----
statement
    :   block                   # blockStmt
    |   expression ';'          # exprStmt
    |   conditionStmt           # condStmt
    |   loopStatement           # loopStmt
    |   jumpStatement           # jumpStmt
    |   ';'                     # blankStmt
    ;

block
    :   '{' blockStatement* '}'
    ;

blockStatement
    :   statement               # stmt
    |   variableDeclaration     # varDecl
    ;

conditionStatement
    :   If '(' expression ')' statement (Else statement)?
    ;

loopStatement
    :   While '(' expression ')' statement statement    # whileStmt
    |   For '(' declinit=variableDeclaration ';'
                cond=expression? ';'
                step=expression? ')' statement          # forStmt
    |   For '(' init=expression? ';'
                cond=expression? ';'
                step=expression? ')' statement          # forStmt
    ;

jumpStatement
    :   Continue ';'                # continueStmt
    |   Break ';'                   # breakStmt
    |   Return expression? ';'      # returnStmt
    ;

// ---- Expression ----
expression
    :   expression op=('++' | '--')                     # suffixExpr
    |   expression '(' parameterList? ')'               # funcCallExpr
    |   expression '[' expression ']'                   # subscriptExpr
    |   expression '.' Identifier                       # memberAccessExpr
    |   <assoc=right> op=('++'|'--') expression         # unaryExpr
    |   <assoc=right> op=('+' | '-') expression         # unaryExpr
    |   <assoc=right> op=('!' | '~') expression         # unaryExpr
    |   <assoc=right> New creator                       # newExpr
    |   expression op=('*' | '/' | '%') expression      # binaryExpr
    |   expression op=('+' | '-') expression            # binaryExpr
    |   expression op=('<<'|'>>') expression            # binaryExpr
    |   expression op=('<' | '>') expression            # binaryExpr
    |   expression op=('<='|'>=') expression            # binaryExpr
    |   expression op=('=='|'!=') expression            # binaryExpr
    |   expression op='&' expression                    # binaryExpr
    |   expression op='^' expression                    # binaryExpr
    |   expression op='|' expression                    # binaryExpr
    |   expression op='&&' expression                   # binaryExpr
    |   expression op='||' expression                   # binaryExpr
    |   <assoc=right> expression op='=' expression      # assignExpr
    |   primaryExpression                               # primaryExpr
    ;

primaryExpression
    :   Identifier                                      # identifierExpr
    |   literal                                         # literalExpr
    |   '(' expression ')'                              # subExpr
    |
    ;

literal
    :   type=

// ---- Identifier ----
Identifier
    :   IdentifierNonDigit (IdentifierNonDigit | Digit)*
    ;

IdentifierNonDigit
    :   [a-zA-Z_]
    ;

Digit
    :   [0-9]
    ;

// ---- Skip ----
WhiteSpace
    :   [ \t]+ -> skip
    ;

NewLine
    :   '\r'? '\n' -> skip
    ;

LineComment
    :   '//' ~[\r\n]* -> skip
    ;

BlockComment
    :   '/*' .*? '*/' -> skip
    ;