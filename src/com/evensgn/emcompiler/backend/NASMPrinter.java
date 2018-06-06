package com.evensgn.emcompiler.backend;

import com.evensgn.emcompiler.Configuration;
import com.evensgn.emcompiler.ir.*;
import com.evensgn.emcompiler.utils.CompilerError;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;

import static com.evensgn.emcompiler.ir.IRBinaryOperation.IRBinaryOp.*;

public class NASMPrinter implements IRVisitor {
    private PrintStream out;
    private Map<String, Integer> idCounter = new HashMap<>();
    private Map<Object, String> idMap = new HashMap<>();

    public NASMPrinter(PrintStream out) {
        this.out = out;
    }

    private boolean isBssSection, isDataSection;

    private String newId(String id) {
        int nowCnt = idCounter.getOrDefault(id, 0) + 1;
        idCounter.put(id, nowCnt);
        return id + "_" + nowCnt;
    }

    private String dataId(StaticData data) {
        String id = idMap.get(data);
        if (id == null) {
            id = "__static_data_" + newId(data.getName());
            idMap.put(data, id);
        }
        return id;
    }

    private String bbId(BasicBlock bb) {
        String id = idMap.get(bb);
        if (id == null) {
            id = "__block_" + newId(bb.getName());
            idMap.put(bb, id);
        }
        return id;
    }

    @Override
    public void visit(IRRoot node) {
        idMap.put(node.getFuncs().get("main").getStartBB(), "main");

        out.println("\t\tglobal\tmain");
        out.println();

        out.println("\t\textern\tmalloc");
        out.println();

        if (node.getStaticDataList().size() > 0) {
            isBssSection = true;
            out.println("\t\tsection\t.bss");
            for (StaticData staticData : node.getStaticDataList()) {
                staticData.accept(this);
            }
            out.println();
            isBssSection = false;
        }

        if (node.getStaticStrs().size() > 0) {
            isDataSection = true;
            out.println("\t\tsection\t.data");
            for (StaticString staticString : node.getStaticStrs().values()) {
                staticString.accept(this);
            }
            out.println();
            isDataSection = false;
        }

        out.println("\t\tsection\t.text\n");
        for (IRFunction irFunction : node.getFuncs().values()) {
            irFunction.accept(this);
        }
        out.println();

        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader("lib/builtin_functions.asm"));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                out.println(line);
            }
        } catch (IOException e) {
            throw new CompilerError("IO exception when reading builtin functions from file");
        }
    }

    @Override
    public void visit(IRFunction node) {
        out.printf("# function %s\n\n", node.getName());
        int bbIdx = 0;
        for (BasicBlock bb : node.getReversePostOrder()) {
            bb.accept(this);
            ++bbIdx;
        }
    }

    @Override
    public void visit(BasicBlock node) {
        out.printf("%s:\n", bbId(node));
        for (IRInstruction inst = node.getFirstInst(); inst != null; inst = inst.getNextInst()) {
            inst.accept(this);
        }
        out.println();
    }

    @Override
    public void visit(IRBranch node) {
        if (node.getCond() instanceof IntImmediate) {
            int boolValue = ((IntImmediate) node.getCond()).getValue();
            out.printf("\t\tjmp\t\t%s\n", boolValue == 1 ? bbId(node.getThenBB()) : bbId(node.getElseBB()));
            return;
        }
        out.print("\t\tcmp\t\t");
        node.getCond().accept(this);
        out.println(", 1");
        out.printf("\t\tje\t\t%s\n", bbId(node.getThenBB()));
        out.printf("\t\tjmp\t\t%s\n", bbId(node.getElseBB()));
    }

    @Override
    public void visit(IRJump node) {
        out.printf("\t\tjmp\t\t%s\n", bbId(node.getTargetBB()));
    }

    @Override
    public void visit(IRReturn node) {
        out.println("\t\tret");
    }

    @Override
    public void visit(IRUnaryOperation node) {
        String op;
        switch (node.getOp()) {
            case BITWISE_NOT:
                op = "not";
                break;
            case NEG:
                op = "neg";
                break;
            default:
                throw new CompilerError("invalid unary operation");
        }
        out.print("\t\tmov\t\t");
        node.getDest().accept(this);
        out.print(", ");
        node.getRhs().accept(this);
        out.print("\n\t\t" + op + "\t\t");
        node.getDest().accept(this);
        out.println();
    }

    @Override
    public void visit(IRBinaryOperation node) {
        if (node.getOp() == DIV || node.getOp() == MOD) {
            // to be optimized: not pushing rdx, rbx
            out.println("\t\tpush\trbx");
            out.print("\t\tmov\t\trax, ");
            node.getLhs().accept(this);
            out.println();
            out.print("\t\tmov\t\trbx, ");
            node.getRhs().accept(this);
            out.println();
            out.println("\t\tpush\trdx");
            out.println("\t\tcdq");
            out.println("\t\tidiv\trbx");
            out.println("\t\tmov\t\trbx, qword [rsp+8]");
            out.print("\t\tmov\t\t");
            node.getDest().accept(this);
            if (node.getOp() == DIV) {
                out.println(", rax");
            } else {
                out.println(", rdx");
            }
            out.println("\t\tpop\t\trdx");
            out.println("\t\tadd\t\trsp, 8");
        } else if (node.getOp() == SHL ||
                node.getOp() == SHR) {
            out.println("\t\tmov\t\trbx, rcx");
            out.print("\t\tmov\t\trcx, ");
            node.getRhs().accept(this);
            if (node.getOp() == SHL) {
                out.print("\n\t\tsal\t\t");
            } else {
                out.print("\n\t\tsar\t\t");
            }
            node.getLhs().accept(this);
            out.println(", cl");
            out.println("\t\tmov\t\trcx, rbx");
            out.print("\t\tand\t\t");
            node.getLhs().accept(this);
            out.println(", -1");
        } else {
            if (node.getDest() != node.getLhs())
                throw new CompilerError("binary operation should have same dest and lhs");
            String op;
            switch (node.getOp()) {
                case ADD:
                    if (node.getRhs() instanceof IntImmediate && ((IntImmediate) node.getRhs()).getValue() == 1) {
                        out.print("\t\tinc\t\t");
                        node.getLhs().accept(this);
                        out.println();
                        return;
                    }
                    op = "add\t";
                    break;
                case SUB:
                    if (node.getRhs() instanceof IntImmediate && ((IntImmediate) node.getRhs()).getValue() == 1) {
                        out.print("\t\tdec\t\t");
                        node.getLhs().accept(this);
                        out.println();
                        return;
                    }
                    op = "sub\t";
                    break;
                case MUL:
                    if (node.getRhs() instanceof IntImmediate && ((IntImmediate) node.getRhs()).getValue() == 1) {
                        return;
                    }
                    op = "imul";
                    break;
                case BITWISE_OR:
                    op = "or\t";
                    break;
                case BITWISE_XOR:
                    op = "xor\t";
                    break;
                case BITWISE_AND:
                    op = "and\t";
                    break;
                default:
                    throw new CompilerError("invalid binary operation");
            }
            out.print("\t\t" + op + "\t");
            node.getLhs().accept(this);
            out.print(", ");
            node.getRhs().accept(this);
            out.println();
        }
    }

    @Override
    public void visit(IRComparison node) {
        if (node.getLhs() instanceof PhysicalRegister) {
            out.print("\t\tand\t\t");
            node.getLhs().accept(this);
            out.println(", -1");
        }
        if (node.getRhs() instanceof PhysicalRegister) {
            out.print("\t\tand\t\t");
            node.getRhs().accept(this);
            out.println(", -1");
        }
        out.println("\t\txor\t\trax, rax");
        out.print("\t\tcmp\t\t");
        node.getLhs().accept(this);
        out.print(", ");
        node.getRhs().accept(this);
        out.println();
        String op;
        switch (node.getOp()) {
            case EQUAL:
                op = "sete";
                break;
            case INEQUAL:
                op = "setne";
                break;
            case LESS:
                op = "setl";
                break;
            case LESS_EQUAL:
                op = "setle";
                break;
            case GREATER:
                op = "setg";
                break;
            case GREATER_EQUAL:
                op = "setge";
                break;
            default:
                throw new CompilerError("invalid comparison operation");
        }
        out.println("\t\t" + op + "\tal");
        out.print("\t\tmov\t\t");
        node.getDest().accept(this);
        out.println(", rax");
    }

    @Override
    public void visit(IRMove node) {
        out.print("\t\tmov\t\t");
        node.getLhs().accept(this);
        out.print(", ");
        node.getRhs().accept(this);
        out.println();
    }

    private String sizeStr(int memSize) {
        String sizeStr;
        switch (memSize) {
            case 1:
                sizeStr = "byte";
                break;
            case 2:
                sizeStr = "word";
                break;
            case 4:
                sizeStr = "dword";
                break;
            case 8:
                sizeStr = "qword";
                break;
            default:
                throw new CompilerError("invalid load size: " + memSize);
        }
        return sizeStr;
    }

    @Override
    public void visit(IRLoad node) {
        if (node.getAddr() instanceof StaticString) {
            out.print("\t\tmov\t\t");
            node.getDest().accept(this);
            out.print(", " + sizeStr(node.getSize()) + " ");
            node.getAddr().accept(this);
            out.println();
            return;
        }
        out.print("\t\tmov\t\t");
        node.getDest().accept(this);
        out.print(", " + sizeStr(node.getSize()) + " [");
        node.getAddr().accept(this);
        if (node.getAddrOffset() < 0) {
            out.print(node.getAddrOffset());
        } else if (node.getAddrOffset() > 0) {
            out.print("+" + node.getAddrOffset());
        }
        out.println("]");
    }

    @Override
    public void visit(IRStore node) {
        if (node.getAddr() instanceof StaticString) {
            out.print("\t\tmov\t\t" + sizeStr(node.getSize()) + " ");
            node.getAddr().accept(this);
            out.print(" ");
            node.getValue().accept(this);
            out.println();
            return;
        }
        out.print("\t\tmov\t\t" + sizeStr(node.getSize()) + " [");
        node.getAddr().accept(this);
        if (node.getAddrOffset() < 0) {
            out.print(node.getAddrOffset());
        } else if (node.getAddrOffset() > 0) {
            out.print("+" + node.getAddrOffset());
        }
        out.print("], ");
        node.getValue().accept(this);
        out.println();
    }

    @Override
    public void visit(IRFunctionCall node) {
        if (node.getFunc().isBuiltIn()) out.println("\t\tcall\t" + node.getFunc().getBuiltInCallLabel());
        else out.println("\t\tcall\t" + bbId(node.getFunc().getStartBB()));
    }

    @Override
    public void visit(IRHeapAlloc node) {
        out.println("\t\tcall\tmalloc");
    }

    @Override
    public void visit(IRPush node) {
        out.print("\t\tpush\t");
        node.getValue().accept(this);
        out.println();
    }

    @Override
    public void visit(IRPop node) {
        out.print("\t\tpop\t\t");
        node.getPreg().accept(this);
        out.println();
    }

    @Override
    public void visit(VirtualRegister node) {
        throw new CompilerError("should not visit virtual register node in NASMPrinter");
    }

    @Override
    public void visit(PhysicalRegister node) {
        out.print(node.getName());
    }

    @Override
    public void visit(IntImmediate node) {
        out.print(node.getValue());
    }

    @Override
    public void visit(StaticVar node) {
        if (isBssSection) {
            String op;
            switch (node.getSize()) {
                case 1: op = "resb"; break;
                case 2: op = "resw"; break;
                case 4: op = "resd"; break;
                case 8: op = "resq"; break;
                default: throw new CompilerError("invalid static data size");
            }
            out.printf("%s:\t%s\t1\n", dataId(node), op);
        }
        else out.print(dataId(node));
    }

    private String staticStrDataSection(String str) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0, n = str.length(); i < n; ++i) {
            char c = str.charAt(i);
            sb.append((int) c);
            sb.append(", ");
        }
        sb.append(0);
        return sb.toString();
    }

    @Override
    public void visit(StaticString node) {
        if (isDataSection) {
            out.printf("%s:\n", dataId(node));
            out.printf("\t\tdq\t\t%d\n", node.getValue().length());
            out.printf("\t\tdb\t\t%s\n", staticStrDataSection(node.getValue()));
        } else {
            out.print(dataId(node));
        }
    }
}
