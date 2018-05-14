# Mx\* Compiler 中期报告

范舟 (@Evensgn)
Project Repository: [Evensgn/EM-Compiler](https://github.com/Evensgn/EM-Compiler)

## Implementation

本项目使用 Java 语言实现，到中期实现了 Lexing, Parsing, Semantic Analysis 阶段. 其中 Lexer 与 Parser 使用 Parser Generator ANTLR4 生成。

在 Lexer 与 Parser 构建出 ANTLR 的 CST 后，使用 visitor 接口，遍历 CST 并将每个结构转换为 AST 的节点. 在建好 AST 后，使用 visitor 模式多次遍历 AST，完成输出 AST、进行 Semantic Analysis 等步骤。
在 Semantic Analysis 阶段，为了支持函数与类的超前引用，首先 visit 一遍 AST，将所有函数签名与类定义加入 scope 中。再进入类定义内部，将类的成员变量、成员函数加入对应类的 scope 中。最后再进入所有函数体（包含类的成员函数）中，对语句进行逐条分析，检查表达式的类型、左值右值是否符合语义规范。

## Features

### User Friendliness

编译器有用户友好的命令行参数设定，提供了灵活的调用方式，用户可以通过 `-h` 或 `--help` 查看帮助信息：
```
$ java com.evensgn.emcompiler.Main --version --help
emcompiler (Evensgn Mx* compiler) 0.0.0
Usage: emcompiler [options] file...
Options:
  -h, --help                    Display this information
  -v, --version                 Display compiler version information
  -c, --config                  Display compiler configurations
  -o <file>                     Place the output into <file>
  --ast <file>                  Dump abstract syntax tree into <file>
```

### 完善的错误提示

本项目将编译器运行过程中遇到的异常/错误分为三类，并分别定义了三个类用于返回报错信息：
- `SyntaxError`: 语法错误，编译器外层函数捕获由 Lexer 和 Parser 抛出的错误后用此类包装并抛出。
- `SemanticError`: 语义错误，在 Semantic Analysis 阶段检查源代码中可能存在的各种语义错误，并构造一个 `SemanticError` 类抛出。
- `CompilerError`: 编译器内部错误，目的是方便编译器代码的调试，理论上不应该出现这种错误。

错误信息输出中包含出错位置的行号、列号以及详细的错误类别、错误原因。
报错信息示例：
```
[Semantic Error] at (141:4): Assignment operator cannot be applied to different types "PrimitiveType(INT)" and "PrimitiveType(STRING)"
```
```
[Semantic Error] at (53:4): Continue statement cannot be used outside of loop statement
```
```
[Syntax Error] at (53:8): mismatched input 'this' expecting {'[', Identifier}
```

### 输出 AST

为了方便调试，编译器提供了输出 AST 的功能，可以将在前端构建出的抽象语法树以结构化的格式输出。输出的不同层级节点有相应缩进，并带有每个节点在源代码中所在行号、列号等详细属性。
输出效果示例：
```
@ ProgramNode (1:0):
>>> decls:
    @ ClassDeclNode (1:0):
    >>> name: Array
    >>> varMember: null
    >>> funcMember:
        @ FuncDeclNode (2:4):
        >>> isContruct: false
        >>> returnType:
            @ TypeNode (2:4):
            >>> type: PrimitiveType(INT)
        >>> name: size
        >>> parameterList: null
        >>> body:
            @ BlockStmtNode (2:14):
            >>> stmtsAndVarDecls: null
```