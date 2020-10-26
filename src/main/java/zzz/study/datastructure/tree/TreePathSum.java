package zzz.study.datastructure.tree;

import zzz.study.datastructure.stack.DyStack;
import zzz.study.datastructure.stack.Stack;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TreePathSum {

    public static void main(String[] args) {
        TreePathSum treePathSum = new TreePathSum();
        Method[] methods = treePathSum.getClass().getDeclaredMethods();
        for (Method m: methods) {
            if (m.isAnnotationPresent(TreeBuilder.class)) {
                try {
                    treePathSum.test((TreeNode) m.invoke(treePathSum, null));
                } catch (Exception ex) {
                    System.err.println(ex.getMessage());
                }

            }
        }
    }

    public void test(TreeNode root) {

        System.out.println("Rec Implementation");

        List<Path> paths = findAllPaths(root);
        Long sum = paths.stream().collect(Collectors.summarizingLong(Path::getValue)).getSum();
        System.out.println(paths);
        System.out.println(sum);

        System.out.println("Non Rec Implementation");

        List<Path> paths2 = findAllPathsNonRec(root);
        Long sum2 = paths2.stream().collect(Collectors.summarizingLong(Path::getValue)).getSum();
        System.out.println(paths2);
        System.out.println(sum2);
    }

    @TreeBuilder
    public TreeNode buildTreeOnlyRoot() {
        TreeNode tree = new TreeNode(5);
        return tree;
    }

    @TreeBuilder
    public TreeNode buildTreeWithL() {
        TreeNode tree = new TreeNode(5);
        TreeNode left = new TreeNode(1);
        tree.left = left;
        return tree;
    }

    @TreeBuilder
    public TreeNode buildTreeWithR() {
        TreeNode tree = new TreeNode(5);
        TreeNode right = new TreeNode(2);
        tree.right = right;
        return tree;
    }

    @TreeBuilder
    public TreeNode buildTreeWithLR() {
        TreeNode tree = new TreeNode(5);
        TreeNode left = new TreeNode(1);
        TreeNode right = new TreeNode(2);
        tree.right = right;
        tree.left = left;
        return tree;
    }

    @TreeBuilder
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

    public List<Path> findAllPathsNonRec(TreeNode root) {

        List<Path> allPaths = new ArrayList<>();
        Stack<Integer> treeData = new DyStack<>();
        Stack<TraceNode> trace = new DyStack<>();

        TreeNode p = root;
        TraceNode traceNode = TraceNode.getNoAccessedNode(p);
        while(p != null) {
            if (p.left == null && p.right == null) {
                // 叶子节点的情形，需要记录路径，并回溯到父节点
                treeData.push(p.val);
                allPaths.add(new Path(treeData.unmodifiedList()));
                treeData.pop();
                if (treeData.isEmpty()) {
                    break;
                }
                traceNode = trace.pop();
                p = traceNode.getParent();
                continue;
            }
            else if (traceNode.needAccessLeft()) {
                // 需要访问左子树的情形
                treeData.push(p.val);
                trace.push(TraceNode.getLeftAccessedNode(p));
                p = p.left;
            }
            else if (traceNode.needAccessRight()) {
                // 需要访问右子树的情形
                if (!traceNode.hasAccessedLeft()) {
                    // 访问左节点时已经入栈过，这里不重复入栈
                    treeData.push(p.val);
                }
                trace.push(TraceNode.getRightAccessedNode(p));
                p = p.right;
            }
            else if (traceNode.hasAllAccessed()) {
                // 左右子树都已经访问了，需要回溯到父节点
                if (trace.isEmpty()) {
                    break;
                }
                treeData.pop();
                traceNode = trace.pop();
                p = traceNode.getParent();
            }
        }
        return allPaths;
    }

    public List<Path> findAllPathsNonRecDeadLoop(TreeNode root) {

        List<Path> allPaths = new ArrayList<>();
        Stack<Integer> s = new DyStack<Integer>();

        TreeNode p = root;
        while(p != null) {
            s.push(p.val);
            if (p.left == null && p.right == null) {
                allPaths.add(new Path(s.unmodifiedList()));
                s.pop();
                if (s.isEmpty()) {
                    break;
                }
            }
            if (p.left != null) {
                p = p.left;
            }
            else if (p.right != null) {
                p = p.right;
            }
        }
        return allPaths;
    }

}



class Path {
    StringBuilder s = new StringBuilder();

    public Path() {
    }

    public Path(Integer i) {
        s.append(i);
    }

    public Path(List list) {
        list.forEach( e-> {
            s.append(e);
        });
    }

    public Path(String str) { this.s = new StringBuilder(str); }

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
