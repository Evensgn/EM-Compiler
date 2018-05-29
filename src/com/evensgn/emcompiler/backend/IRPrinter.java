package com.evensgn.emcompiler.backend;

import com.evensgn.emcompiler.ir.*;

import java.io.PrintStream;
import java.util.*;

// ************************************************************************************************
// This file is used to print out IR in the format of LeLeIR (LeLe -> Lequn Chen)
// so that one can use LLIRInterpreter to test IR.
// Actually LeLeIR is more like MIPS rather than NASM assembly, but for test purpose it's enough.
// [repo of LLIRInterpreter] https://github.com/abcdabcd987/LLIRInterpreter
// credit to Lequn Chen (@abcdabcd987)
// ************************************************************************************************

public class IRPrinter implements IRVisitor {
    private PrintStream out;

    public IRPrinter(PrintStream out) {
        this.out = out;
    }

    private Map<BasicBlock, String> bbMap = new HashMap<>();
    private Map<VirtualRegister, String> vregMap = new HashMap<>();
    private Map<StaticData, String> staticDataMap = new HashMap<>();

    private Map<String, Integer> bbCnt = new HashMap<>();
    private Map<String, Integer> vregCnt = new HashMap<>();
    private Map<String, Integer> staticDataCnt = new HashMap<>();

    private Set<BasicBlock> bbVisited = new HashSet<>();

    private boolean isStaticDef;

    private String genID(String name, Map<String, Integer> cnt) {
        int cntName = cnt.getOrDefault(name, 0) + 1;
        cnt.put(name, cntName);
        if (cntName == 1) return name;
        return name + "_" + cntName;
    }

    private String getBBID(BasicBlock bb) {
        String id = bbMap.get(bb);
        if (id == null) {
            if (bb.getName() == null) {
                id = genID("bb", bbCnt);
            } else {
                id = genID(bb.getName(), bbCnt);
            }
            bbMap.put(bb, id);
        }
        return id;
    }

    private String getVRegID(VirtualRegister vreg) {
        String id = vregMap.get(vreg);
        if (id == null) {
            if (vreg.getName() == null) {
                id = genID("vreg", vregCnt);
            } else {
                id = genID(vreg.getName(), vregCnt);
            }
            vregMap.put(vreg, id);
        }
        return id;
    }

    private String getStaticDataID(StaticData data) {
        String id = staticDataMap.get(data);
        if (id == null) {
            if (data.getName() == null) {
                id = genID("staticData", staticDataCnt);
            } else {
                id = genID(data.getName(), staticDataCnt);
            }
            staticDataMap.put(data, id);
        }
        return id;
    }

    @Override
    public void visit(IRRoot node) {
        // Static Data
        isStaticDef = true;
        for (StaticData staticData : node.getStaticDataList()) {
            staticData.accept(this);
        }
        isStaticDef = false;
        for (StaticString staticStr : node.getStaticStrs().values()) {
            staticStr.accept(this);
        }
        out.println();
        for (IRFunction func : node.getFuncs().values()) {
            func.accept(this);
        }
    }

    @Override
    public void visit(IRFunction node) {
        vregMap = new IdentityHashMap<>();
        vregCnt = new HashMap<>();
        out.printf("func %s ", node.getName());
        for (VirtualRegister paraVReg : node.getArgVRegList()) {
            out.printf("$%s ", getVRegID(paraVReg));
        }
        out.printf("{\n");
        for (BasicBlock bb : node.getReversePostOrder()) {
            bb.accept(this);
        }
        out.printf("}\n\n");
    }

    @Override
    public void visit(BasicBlock node) {
        if (bbVisited.contains(node)) return;
        bbVisited.add(node);
        out.println("%" + getBBID(node) + ":");
        for (IRInstruction inst = node.getFirstInst(); inst != null; inst = inst.getNextInst()) {
            inst.accept(this);
        }
    }

    @Override
    public void visit(IRBranch node) {
        out.print("    br ");
        node.getCond().accept(this);
        out.println(" %" + getBBID(node.getThenBB()) + " %" + getBBID(node.getElseBB()));
        out.println();
    }

    @Override
    public void visit(IRJump node) {
        out.printf("    jump %%%s\n\n", getBBID(node.getTargetBB()));
    }

    @Override
    public void visit(IRReturn node) {
        out.print("    ret ");
        if (node.getRetValue() != null) {
            node.getRetValue().accept(this);
        } else {
            out.print("0");
        }
        out.println();
        out.println();
    }

    @Override
    public void visit(IRUnaryOperation node) {
        out.print("    ");
        String op = null;
        switch (node.getOp()) {
            case NEG: op = "neg"; break;
            case BITWISE_NOT: op = "not"; break;
        }
        node.getDest().accept(this);
        out.printf(" = %s ", op);
        node.getRhs().accept(this);
        out.println();
    }

    @Override
    public void visit(IRBinaryOperation node) {
        out.print("    ");
        String op = null;
        switch (node.getOp()) {
            case ADD: op = "add"; break;
            case SUB: op = "sub"; break;
            case MUL: op = "mul"; break;
            case DIV: op = "div"; break;
            case MOD: op = "rem"; break;
            case SHL: op = "shl"; break;
            case SHR: op = "shr"; break;
            case BITWISE_AND: op = "and"; break;
            case BITWISE_OR: op = "or"; break;
            case BITWISE_XOR: op = "xor"; break;
        }
        node.getDest().accept(this);
        out.printf(" = %s ", op);
        node.getLhs().accept(this);
        out.printf(" ");
        node.getRhs().accept(this);
        out.println();
    }

    @Override
    public void visit(IRComparison node) {
        out.print("    ");
        String op = null;
        switch (node.getOp()) {
            case EQUAL: op = "seq"; break;
            case INEQUAL: op = "sne"; break;
            case GREATER: op = "sgt"; break;
            case GREATER_EQUAL: op = "sge"; break;
            case LESS: op = "slt"; break;
            case LESS_EQUAL: op = "sle"; break;
        }
        node.getDest().accept(this);
        out.printf(" = %s ", op);
        node.getLhs().accept(this);
        out.printf(" ");
        node.getRhs().accept(this);
        out.println();
    }

    @Override
    public void visit(IRMove node) {
        out.print("    ");
        node.getLhs().accept(this);
        out.print(" = move ");
        node.getRhs().accept(this);
        out.println();
    }

    @Override
    public void visit(IRLoad node) {
        out.print("    ");
        node.getDest().accept(this);
        out.printf(" = load %d ", node.getSize());
        node.getAddr().accept(this);
        out.println(" " + node.getAddrOffset());
    }

    @Override
    public void visit(IRStore node) {
        out.printf("    store %d ", node.getSize());
        node.getAddr().accept(this);
        out.print(" ");
        node.getValue().accept(this);
        out.println(" " + node.getAddrOffset());
    }

    @Override
    public void visit(IRFunctionCall node) {
        out.print("    ");
        if (node.getDest() != null) {
            node.getDest().accept(this);
            out.print(" = ");
        }
        out.printf("call %s ", node.getFunc().getName());
        for (RegValue arg : node.getArgs()) {
            arg.accept(this);
            out.print(" ");
        }
        out.println();
    }

    @Override
    public void visit(IRHeapAlloc node) {
        out.print("    ");
        node.getDest().accept(this);
        out.print(" = alloc ");
        node.getAllocSize().accept(this);
        out.println();
    }

    @Override
    public void visit(VirtualRegister node) {
        out.print("$" + getVRegID(node));
    }

    @Override
    public void visit(IntImmediate node) {
        out.print(node.getValue());
    }

    @Override
    public void visit(StaticVar node) {
        if (isStaticDef) out.printf("space @%s %d\n", getStaticDataID(node), node.getSize());
        else out.print("@" + getStaticDataID(node));
    }

    @Override
    public void visit(StaticString node) {
        if (isStaticDef) out.printf("asciiz @%s %s\n", getStaticDataID(node), node.getValue());
        else out.print("@" + getStaticDataID(node));
    }
}
