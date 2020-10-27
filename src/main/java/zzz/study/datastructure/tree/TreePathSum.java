package zzz.study.datastructure.tree;

import org.apache.commons.lang.StringUtils;
import zzz.study.datastructure.stack.DyStack;
import zzz.study.datastructure.stack.Stack;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static java.lang.Integer.max;

public class TreePathSum {

    public static void main(String[] args) {
        TreePathSum treePathSum = new TreePathSum();
        Method[] methods = treePathSum.getClass().getDeclaredMethods();

        try {
            // time for starting up arthas trace method commands
            TimeUnit.SECONDS.sleep(20);
        } catch (InterruptedException e) {
            //
        }

        for (Method m: methods) {
            if (m.isAnnotationPresent(TreeBuilder.class)) {
                try {
                    TreeNode t = (TreeNode) m.invoke(treePathSum, null);
                    System.out.println("height: " + t.height());
                    //treePathSum.test2(t);
                    treePathSum.testNonRec(t);
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

        assert sum == sum2;
    }

    public void test2(TreeNode root) {
        System.out.println("Rec Implementation");
        List<Path> paths = findAllPaths(root);
        System.out.println(paths);

        System.out.println("Non Rec Implementation");
        List<Path> paths2 = findAllPathsNonRec(root);
        System.out.println(paths2);

        assert paths.size() == paths2.size();
        for (int i=0; i < paths.size(); i++) {
            assert paths.get(i).toString().equals(paths2.get(i).toString());
        }

    }

    // use arthas trace to measure costs of findAllPathsNonRec
    // trace zzz.study.datastructure.tree.TreePathSum findAllPathsNonRec
    public void testNonRec(TreeNode root) {

        System.out.println("Non Rec Implementation");
        List<Path> paths2 = findAllPathsNonRec(root);
        System.out.println(paths2);

    }

    @TreeBuilder
    public TreeNode buildTreeOnlyRoot() {
        TreeNode tree = new TreeNode(9);
        return tree;
    }

    @TreeBuilder
    public TreeNode buildTreeWithL() {
        return buildTreeWithL(5, 1);
    }

    public TreeNode buildTreeWithL(int rootVal, int leftVal) {
        TreeNode tree = new TreeNode(rootVal);
        TreeNode left = new TreeNode(leftVal);
        tree.left = left;
        return tree;
    }

    @TreeBuilder
    public TreeNode buildTreeWithR() {
        return buildTreeWithR(5,2);
    }

    public TreeNode buildTreeWithR(int rootVal, int rightVal) {
        TreeNode tree = new TreeNode(rootVal);
        TreeNode right = new TreeNode(rightVal);
        tree.right = right;
        return tree;
    }

    @TreeBuilder
    public TreeNode buildTreeWithLR() {
        return buildTreeWithLR(5,1,2);
    }

    public TreeNode buildTreeWithLR(int rootVal, int leftVal, int rightVal) {
        TreeNode tree = new TreeNode(rootVal);
        TreeNode left = new TreeNode(leftVal);
        TreeNode right = new TreeNode(rightVal);
        tree.right = right;
        tree.left = left;
        return tree;
    }

    Random rand = new Random(System.currentTimeMillis());

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

    @TreeBuilder
    public TreeNode buildTreeWithMore2() {
        TreeNode tree = new TreeNode(5);
        TreeNode left = new TreeNode(1);
        TreeNode right = new TreeNode(2);
        TreeNode left2 = new TreeNode(3);
        TreeNode right2 = new TreeNode(4);
        tree.right = right;
        tree.left = left;
        right.left = left2;
        right.right = right2;
        return tree;
    }

    @TreeBuilder
    public TreeNode buildTreeWithMore3() {
        TreeNode tree = new TreeNode(5);
        TreeNode left = new TreeNode(1);
        TreeNode right = new TreeNode(2);
        TreeNode left2 = new TreeNode(3);
        TreeNode right2 = new TreeNode(4);
        tree.right = right;
        tree.left = left;
        right.right = left2;
        left2.right = right2;
        return tree;
    }

    @TreeBuilder
    public TreeNode buildTreeWithMore4() {
        TreeNode tree = new TreeNode(5);
        TreeNode left = new TreeNode(1);
        TreeNode right = new TreeNode(2);
        TreeNode left2 = new TreeNode(3);
        TreeNode right2 = new TreeNode(4);
        TreeNode right3 = new TreeNode(6);
        tree.right = right;
        tree.left = left;
        left.right = right3;
        right.right = left2;
        left2.right = right2;
        return tree;
    }

    public TreeNode treeWithRandom() {
        int c = rand.nextInt(3);
        switch (c) {
            case 0: return buildTreeWithL(rand.nextInt(9), rand.nextInt(9));
            case 1: return buildTreeWithR(rand.nextInt(9), rand.nextInt(9));
            case 2: return buildTreeWithLR(rand.nextInt(9), rand.nextInt(9), rand.nextInt(9));
            default: return buildTreeOnlyRoot();
        }
    }

    public TreeNode linkRandom(TreeNode t1, TreeNode t2) {
        if (t2.left == null) {
            t2.left = t1;
        }
        else if (t2.right == null) {
            t2.right = t1;
        }
        else {
            int c = rand.nextInt(4);
            switch (c) {
                case 0: t2.left.left = t1;
                case 1: t2.left.right = t1;
                case 2: t2.right.left = t1;
                case 3: t2.right.right = t1;
                default: t2.left.left = t1;
            }
        }
        return t2;
    }

    @TreeBuilder
    public TreeNode buildTreeWithRandom() {
        TreeNode root = treeWithRandom();
        int i = 20;
        while (i > 0) {
            TreeNode t = treeWithRandom();
            root = linkRandom(root, t);
            i--;
        }
        return root;
    }

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


interface Path {
    void append(Integer i);
    Long getValue();
}

class ListPath implements Path {
    List<Integer> path = new ArrayList<>();

    public ListPath(int i) {
        this.path.add(i);
    }

    public ListPath(List list) {
        this.path.addAll(list);
    }

    @Override
    public void append(Integer i) {
        path.add(i);
    }

    @Override
    public Long getValue() {
        StringBuilder s = new StringBuilder();
        path.forEach( e-> {
            s.append(e);
        });
        return Long.parseLong(s.reverse().toString());
    }

    public String toString() {
        return StringUtils.join(path.toArray(), "");
    }
}

class StringPath implements Path {
    StringBuilder s = new StringBuilder();

    public StringPath() {
    }

    public StringPath(Integer i) {
        s.append(i);
    }

    public StringPath(List list) {
        list.forEach( e-> {
            s.append(e);
        });
    }

    public StringPath(String str) { this.s = new StringBuilder(str); }

    public Long getValue() {
        return Long.parseLong(s.reverse().toString());
    }

    public void append(Integer i) {
        s.append(i);
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
