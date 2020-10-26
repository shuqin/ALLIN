package zzz.study.datastructure.tree;

class TraceNode {

    private TreeNode parent;
    private int accessed;  // 0 均未访问 1 已访问左 2 已访问右

    public TraceNode(TreeNode parent, int child) {
        this.parent = parent;
        this.accessed = child;
    }

    public static TraceNode getNoAccessedNode(TreeNode parent) {
        return new TraceNode(parent, 0);
    }

    public static TraceNode getLeftAccessedNode(TreeNode parent) {
        return new TraceNode(parent, 1);
    }

    public static TraceNode getRightAccessedNode(TreeNode parent) {
        return new TraceNode(parent, 2);
    }

    public boolean needAccessLeft() {
        return parent.left != null && accessed == 0;
    }

    public boolean needAccessRight() {
        return parent.right != null && accessed < 2;
    }

    public boolean hasAccessedLeft() {
        return parent.left == null || (parent.left != null && accessed == 1);
    }

    public boolean hasNoLeft() {
        return parent.left == null;
    }

    public boolean hasAllAccessed() {
        if (parent.left != null && parent.right == null && accessed == 1) {
            return true;
        }
        if (parent.right != null && accessed == 2) {
            return true;
        }
        return false;
    }

    public TreeNode getParent() {
        return parent;
    }

    public int getAccessed() {
        return accessed;
    }
}
