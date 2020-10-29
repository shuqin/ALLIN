package zzz.study.datastructure.tree;

import zzz.study.datastructure.stack.DyStack;
import zzz.study.datastructure.stack.Stack;

import java.util.ArrayList;
import java.util.List;

public class TreeUtil {

    public List<Path> findAllPaths(TreeNode root) {
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

    // 非递归算法的效率主要取决于树的均衡性。通过 arthas 分析结果，主要取决于访问节点和出栈入栈次数
    // 一棵 h 高的满二叉树, 出入栈次数为 2^h-2 , 树越均衡，要比较的次数越多.
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

    public List<Path> findAllPathsNonRecEffectively(TreeNode root) {

        List<Path> allPaths = new ArrayList<>();
        Stack<Integer> treeData = new DyStack<>();
        Stack<TraceNode> trace = new DyStack<>();

        TreeNode p = root;
        int accessed = 0;
        while(p != null) {
            if (p.left == null && p.right == null) {
                // 叶子节点的情形，需要记录路径，并回溯到父节点
                treeData.push(p.val);
                allPaths.add(new ListPath(treeData.unmodifiedList()));
                treeData.pop();
                if (treeData.isEmpty()) {
                    break;
                }
                TraceNode traceNode = trace.pop();
                p = traceNode.getParent();
                accessed = traceNode.getAccessed();
                continue;
            }
            else if (p.left != null && accessed == 0) {
                // 需要访问左子树的情形
                treeData.push(p.val);
                trace.push(TraceNode.getLeftAccessedNode(p));
                p = p.left;
                accessed = 0;
            }
            else if (p.right != null && accessed < 2) {
                // 需要访问右子树的情形
                if (p.left == null) {
                    treeData.push(p.val);
                }
                if (!( p.left == null || (p.left != null && accessed == 1) )) {
                    // 访问左节点时已经入栈过，这里不重复入栈
                    treeData.push(p.val);
                }
                trace.push(TraceNode.getRightAccessedNode(p));
                p = p.right;
                accessed = 0;
            }
            else if (hasAllAccessed(p, accessed)) {
                // 左右子树都已经访问了，需要回溯到父节点
                if (trace.isEmpty()) {
                    break;
                }
                treeData.pop();
                TraceNode traceNode = trace.pop();
                p = traceNode.getParent();
                accessed = traceNode.getAccessed();
            }
        }
        return allPaths;
    }

    public boolean hasAllAccessed(TreeNode parent, int accessed) {
        if (parent.left != null && parent.right == null && accessed == 1) {
            return true;
        }
        if (parent.right != null && accessed == 2) {
            return true;
        }
        return false;
    }

    public List<Path> findAllPathsNonRecDeadLoop(TreeNode root) {

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
