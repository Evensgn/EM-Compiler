package com.evensgn.emcompiler.backend;

import com.evensgn.emcompiler.Configuration;
import com.evensgn.emcompiler.ir.*;
import com.evensgn.emcompiler.utils.CompilerError;

import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;

import static com.evensgn.emcompiler.ir.IRBinaryOperation.IRBinaryOp.DIV;
import static com.evensgn.emcompiler.ir.IRBinaryOperation.IRBinaryOp.MOD;

public class NASMPrinter implements IRVisitor {
    private PrintStream out;
    private Map<String, Integer> idCounter = new HashMap<>();
    private Map<Object, String> idMap = new HashMap<>();

    public NASMPrinter(PrintStream out) {
        this.out = out;
    }

    private boolean isBssSection;

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

        isBssSection = true;
        out.println("\t\tsection\t.bss");
        for (StaticData staticData : node.getStaticDataList()) {
            staticData.accept(this);
        }
        out.println();
        isBssSection = false;

        out.println("\t\tsection\t.text\n");
        for (IRFunction irFunction : node.getFuncs().values()) {
            irFunction.accept(this);
        }
        out.println();

        // TO DO add built-in functions
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
            out.print("\t\tmov\t\trax, ");
            node.getLhs().accept(this);
            out.println();
            out.println("\t\tcqo");
            out.print("\t\tmov\t\trdi, ");
            node.getRhs().accept(this);
            out.println("\t\tidiv\trdi");
            out.print("\t\tmov\t\t");
            node.getDest().accept(this);
            if (node.getOp() == DIV) {
                out.println(", rax");
            } else {
                out.println(", rdx");
            }
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
                    op = "add";
                    break;
                case SUB:
                    if (node.getRhs() instanceof IntImmediate && ((IntImmediate) node.getRhs()).getValue() == 1) {
                        out.print("\t\tdec\t\t");
                        node.getLhs().accept(this);
                        out.println();
                        return;
                    }
                    op = "sub";
                    break;
                case MUL:
                    if (node.getRhs() instanceof IntImmediate && ((IntImmediate) node.getRhs()).getValue() == 1) {
                        return;
                    }
                    op = "imul";
                    break;
                case SHR:
                    op = "sar";
                    break;
                case SHL:
                    op = "sal";
                    break;
                case BITWISE_OR:
                    op = "or";
                    break;
                case BITWISE_XOR:
                    op = "xor";
                    break;
                case BITWISE_AND:
                    op = "and";
                    break;
                default:
                    throw new CompilerError("invalid binary operation");
            }
            out.print("\t\t" + op + "\t\t");
            node.getLhs().accept(this);
            out.print(", ");
            node.getRhs().accept(this);
            out.println();
        }
    }

    @Override
    public void visit(IRComparison node) {
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

    @Override
    public void visit(IRLoad node) {
        if (node.getSize() != Configuration.getRegSize()) {
            throw new CompilerError("this load instruction is not of reg size");
        }
        out.print("\t\tmov\t\t");
        node.getDest().accept(this);
        out.print(", [");
        node.getAddr().accept(this);
        if (node.getAddrOffset() < 0) {
            out.print(node.getAddrOffset());
        } else if (node.getAddrOffset() > 0) {
            out.print("+" + node.getAddrOffset());
        }
        out.println("]\n");
    }

    @Override
    public void visit(IRStore node) {
        if (node.getSize() != Configuration.getRegSize()) {
            throw new CompilerError("this store instruction is not of reg size");
        }
        out.print("\t\tmov\t\t[");
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
        out.println("\t\tcall\t" + bbId(node.getFunc().getStartBB()));
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
        if (isBssSection) out.printf("%s:\tdq\t\t%d\n", dataId(node), node.getSize());
        else out.print(dataId(node));
    }

    @Override
    public void visit(StaticString node) {
        // TO DO
    }
}
