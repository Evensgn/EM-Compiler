grammar EMxStar; // Evensgn-Mx*

@header {
package com.evensgn.emcompiler.parser;
}

program
    :   programSection* EOF
    ;

programSection
    :   functionDeclaration
    |   classDeclaration
    |   variableDeclaration
    ;

// ---- Declaration -----
functionDeclaration
    :   typeTypeOrVoid? Identifier '(' parameterDeclarationList? ')' block
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

nonArrayTypeType
    :   Int
    |   Bool
    |   String
    |   Identifier
    ;

// ---- Statement ----
statement
    :   block                   # blockStmt
    |   expression ';'          # exprStmt
    |   conditionStatement      # condStmt
    |   loopStatement           # loopStmt
    |   jumpStatement           # jumpStmt
    |   ';'                     # blankStmt
    ;

block
    :   '{' blockStatement* '}'
    ;

blockStatement
    :   statement               # stmt
    |   variableDeclaration     # varDeclStmt
    ;

conditionStatement
    :   If '(' expression ')' thenStmt=statement (Else elseStmt=statement)?
    ;

loopStatement
    :   While '(' expression ')' statement              # whileStmt
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
    :   expression op=('++' | '--')                             # suffixExpr
    |   expression '.' Identifier                               # memberAccessExpr
    |   arr=expression '[' sub=expression ']'                   # subscriptExpr
    |   expression '(' parameterList? ')'                       # funcCallExpr
    |   <assoc=right> op=('++'|'--') expression                 # prefixExpr
    |   <assoc=right> op=('+' | '-') expression                 # prefixExpr
    |   <assoc=right> op=('!' | '~') expression                 # prefixExpr
    |   <assoc=right> New creator                               # newExpr
    |   lhs=expression op=('*' | '/' | '%') rhs=expression      # binaryExpr
    |   lhs=expression op=('+' | '-') rhs=expression            # binaryExpr
    |   lhs=expression op=('<<'|'>>') rhs=expression            # binaryExpr
    |   lhs=expression op=('<' | '>') rhs=expression            # binaryExpr
    |   lhs=expression op=('<='|'>=') rhs=expression            # binaryExpr
    |   lhs=expression op=('=='|'!=') rhs=expression            # binaryExpr
    |   lhs=expression op='&' rhs=expression                    # binaryExpr
    |   lhs=expression op='^' rhs=expression                    # binaryExpr
    |   lhs=expression op='|' rhs=expression                    # binaryExpr
    |   lhs=expression op='&&' rhs=expression                   # binaryExpr
    |   lhs=expression op='||' rhs=expression                   # binaryExpr
    |   <assoc=right> lhs=expression op='=' rhs=expression      # assignExpr
    |   primaryExpression                                       # primaryExpr
    ;

primaryExpression
    :   Identifier                                      # identifierExpr
    |   This                                            # thisExpr
    |   constant                                        # constExpr
    |   '(' expression ')'                              # subExpr
    ;

constant
    :   IntegerConstant             # intConst
    |   StringConst                 # stringConst
    |   NullLiteral                 # nullLiteral
    |   BoolConstant                # boolConst
    ;

creator
    :   nonArrayTypeType ('[' expression ']')+ ('[' ']')+ ('[' expression ']')+     # errorCreator
    |   nonArrayTypeType ('[' expression ']')+ ('[' ']')*                           # arrayCreator
    |   nonArrayTypeType                                                            # nonArrayCreator
    ;

parameterList
    :   expression (',' expression)*
    ;

// ---- Reserved Keywords ----
Bool                : 'bool';
Int                 : 'int';
String              : 'string';
fragment Null       : 'null';
Void                : 'void';
fragment True       : 'true';
fragment False      : 'false';
If                  : 'if';
Else                : 'else';
For                 : 'for';
While               : 'while';
Break               : 'break';
Continue            : 'continue';
Return              : 'return';
New                 : 'new';
Class               : 'class';
This                : 'this';

// ---- Constant ----
IntegerConstant
    :   [1-9] [0-9]*
    |   '0'
    ;

StringConst
    :   '"' StringCharacter* '"'
    ;

fragment StringCharacter
    :   ~["\\\r\n]
    |   '\\' ["n\\]
    ;

NullLiteral
    :   Null
    ;

BoolConstant
    :   True
    |   False
    ;

// ---- Identifier ----
Identifier
    :   IdentifierNonDigit (IdentifierNonDigit | Digit)*
    ;

fragment IdentifierNonDigit
    :   [a-zA-Z_]
    ;

fragment Digit
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