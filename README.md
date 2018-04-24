# EM-Compiler

**EMCompiler** (Evensgn Mx* Compiler) is a compiler for course Compiler 2018 at ACM Class, Shanghai Jiao Tong University.

| Source Language | Target Language |
|:---------------:|:---------------:|
| [Mx\*](https://acm.sjtu.edu.cn/w/images/3/30/M_language_manual.pdf) (a C-and-Java-like language) | Linux x86-64 Assembly in NASM |

## Usage

```
$ java com.evensgn.emcompiler.Main --version --help
emcompiler (Evensgn Mx* compiler) 0.0.0
Usage: emcompiler [options] file...
Options:
  -h, --help                    Display this information
  -v, --version                 Display compiler version information
  -c, --config                  Display compiler configurations
  -o <file>                     Place the output into <file>
```