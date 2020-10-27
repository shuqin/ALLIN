package zzz.study.datastructure.tree;

import static java.lang.Integer.max;

public class TreeNode {

    int val;
    TreeNode left;
    TreeNode right;
    TreeNode(int x) { val = x; }

    public int height() {
        if (left == null && right == null) {
            return 1;
        }
        int leftHeight = 0;
        int rightHeight = 0;
        if (left != null) {
            leftHeight = left.height();
        }
        if (right != null) {
            rightHeight = right.height();
        }
        return 1 + max(leftHeight, rightHeight);
    }
}
