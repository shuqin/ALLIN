package zzz.study.datastructure.tree;

import zzz.study.datastructure.stack.DyStack;
import zzz.study.datastructure.stack.Stack;

import java.util.ArrayList;
import java.util.List;

public class TreeUtil {

    public static List<Path> findAllPaths(TreeNode root) {
        List<Path> le = new ArrayList<>();
        List<Path> ri = new ArrayList<>();
        if (root != null) {

            if (root.left == null && root.right == null) {
                List<Path> single = new ArrayList<>();
                single.add(new ListPath(root.val));
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

    public static List<Path> findAllPathsNonRec(TreeNode root) {

        List<Path> allPaths = new ArrayList<>();
        Stack<Integer> treeData = new DyStack<>();
        Stack<TraceNode> trace = new DyStack<>();

        TreeNode p = root;
        TraceNode traceNode = TraceNode.getNoAccessedNode(p);
        while(p != null) {
            if (p.left == null && p.right == null) {
                // 叶子节点的情形，需要记录路径，并回溯到父节点
                treeData.push(p.val);
                allPaths.add(new ListPath(treeData.unmodifiedList()));
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
                traceNode = TraceNode.getNoAccessedNode(p);
            }
            else if (traceNode.needAccessRight()) {
                // 需要访问右子树的情形
                if (traceNode.hasNoLeft()) {
                    treeData.push(p.val);
                }
                if (!traceNode.hasAccessedLeft()) {
                    // 访问左节点时已经入栈过，这里不重复入栈
                    treeData.push(p.val);
                }
                trace.push(TraceNode.getRightAccessedNode(p));
                p = p.right;
                traceNode = TraceNode.getNoAccessedNode(p);
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

    public static List<Path> findAllPathsNonRecDeadLoop(TreeNode root) {

        List<Path> allPaths = new ArrayList<>();
        Stack<Integer> s = new DyStack<Integer>();

        TreeNode p = root;
        while(p != null) {
            s.push(p.val);
            if (p.left == null && p.right == null) {
                allPaths.add(new ListPath(s.unmodifiedList()));
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
