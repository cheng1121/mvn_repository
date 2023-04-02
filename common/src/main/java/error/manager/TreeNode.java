package error.manager;

import java.util.List;
import java.util.Objects;

/**
 * @time: 2022/11/17 09:58
 * @author: licheng
 * @desc:
 */
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

    public String getName() {
        return name;
    }

    public List<TreeNode> getNodes() {
        return nodes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TreeNode treeNode = (TreeNode) o;
        return code == treeNode.code;
    }

    @Override
    public int hashCode() {
        return Objects.hash(code);
    }
}
