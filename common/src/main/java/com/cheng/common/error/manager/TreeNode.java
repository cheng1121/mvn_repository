package com.cheng.common.error.manager;


import java.util.List;

public class TreeNode {
    int code;
    String name;
    List<TreeNode> nodes;

    public TreeNode(int code, String name) {
        this.code = code;
        this.name = name;
    }

    public void setNodes(List<TreeNode> nodes) {
        this.nodes = nodes;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<TreeNode> getNodes() {
        return nodes;
    }

    @Override
    public String toString() {
        return "code=" + code +
                ", name='" + name;
    }
}
