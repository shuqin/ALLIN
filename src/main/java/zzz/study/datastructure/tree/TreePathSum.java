package zzz.study.datastructure.tree;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TreePathSum {

    public static void main(String[] args) {
        TreePathSum treePathSum = new TreePathSum();
        treePathSum.test(treePathSum.buildTreeOnlyRoot());
        treePathSum.test(treePathSum.buildTreeWithLR());
        treePathSum.test(treePathSum.buildTreeWithMore());
    }

    public void test(TreeNode root) {
        List<Path> paths = findAllPaths(root);
        Long sum = paths.stream().collect(Collectors.summarizingLong(Path::getValue)).getSum();
        System.out.println(paths);
        System.out.println(sum);
    }

    public TreeNode buildTreeOnlyRoot() {
        TreeNode tree = new TreeNode(5);
        return tree;
    }

    public TreeNode buildTreeWithLR() {
        TreeNode tree = new TreeNode(5);
        TreeNode left = new TreeNode(1);
        TreeNode right = new TreeNode(2);
        tree.right = right;
        tree.left = left;
        return tree;
    }

    public TreeNode buildTreeWithMore() {
        TreeNode tree = new TreeNode(5);
        TreeNode left = new TreeNode(1);
        TreeNode right = new TreeNode(2);
        TreeNode left2 = new TreeNode(3);
        TreeNode right2 = new TreeNode(4);
        tree.right = right;
        tree.left = left;
        left.left = left2;
        left.right = right2;
        return tree;
    }

    public List<Path> findAllPaths(TreeNode root) {
        List<Path> le = new ArrayList<>();
        List<Path> ri = new ArrayList<>();
        if (root != null) {

            if (root.left == null && root.right == null) {
                List<Path> single = new ArrayList<>();
                single.add(new Path(root.val));
                return single;
            }

            if (root.left != null) {
                le = findAllPaths(root.left);
                for (Path p: le) {
                    p.append(root.val);
                }
            }
            if (root.right != null) {
                ri = findAllPaths(root.right);
                for (Path p: ri) {
                    p.append(root.val);
                }
            }
        }
        List<Path> paths = new ArrayList<>();
        paths.addAll(le);
        paths.addAll(ri);
        return paths;
    }

}

class Path {
    StringBuilder s = new StringBuilder();

    public Path() {
    }

    public Path(Integer i) {
        s.append(i);
    }

    public Long getValue() {
        return Long.parseLong(s.reverse().toString());
    }

    public StringBuilder append(Integer i) {
        return s.append(i);
    }

    public String toString() {
        return s.reverse().toString();
    }
}

class TreeNode {

    int val;
    TreeNode left;
    TreeNode right;
    TreeNode(int x) { val = x; }
}
