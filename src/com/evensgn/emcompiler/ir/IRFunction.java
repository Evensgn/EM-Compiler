package com.evensgn.emcompiler.ir;

import com.evensgn.emcompiler.ast.BlockStmtNode;
import com.evensgn.emcompiler.scope.FuncEntity;
import com.evensgn.emcompiler.scope.VarEntity;
import javafx.geometry.Pos;
import sun.net.www.http.PosterOutputStream;

import java.util.*;

public class IRFunction {
    private FuncEntity funcEntity;
    private BasicBlock startBB = null, endBB = null;
    private List<VirtualRegister> argVRegList = new ArrayList<>();
    private List<BasicBlock> reversePostOrder = null;

    public String getName() {
        return funcEntity.getName();
    }

    public List<VarEntity> getParameters() {
        return funcEntity.getParameters();
    }

    public IRFunction(FuncEntity funcEntity) {
        this.funcEntity = funcEntity;
    }

    public List<VirtualRegister> getArgVRegList() {
        return argVRegList;
    }

    public void addArgVReg(VirtualRegister vreg) {
        argVRegList.add(vreg);
    }

    public BasicBlock genFirstBB() {
        startBB = new BasicBlock(this, funcEntity.getName() + "_entry");
        return startBB;
    }

    public FuncEntity getFuncEntity() {
        return funcEntity;
    }

    private Set<BasicBlock> dfsVisited = null;

    private void dfsPostOrder(BasicBlock bb) {
        System.out.println("dfsPostOrder " + bb.getName());
        if (dfsVisited.contains(bb)) return;
        for (BasicBlock nextBB : bb.getNextBBSet()) {
            dfsPostOrder(nextBB);
        }
        // actually is post order now
        reversePostOrder.add(bb);
    }

    public List<BasicBlock> getReversePostOrder() {
        System.out.println("getReversePostOrder 1");
        if (reversePostOrder != null) return reversePostOrder;
        reversePostOrder = new ArrayList<>();
        dfsVisited = new HashSet<>();
        dfsPostOrder(startBB);

        System.out.println("getReversePostOrder 2");
        dfsVisited = null;
        for (int i = 0; i < reversePostOrder.size(); ++i) {
            reversePostOrder.get(i).setPostOrderIdx(i);
        }
        Collections.reverse(reversePostOrder);
        return reversePostOrder;
    }

    public void accept(IRVisitor visitor) {
        visitor.visit(this);
    }
}