package zzz.study.datastructure.tree;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static zzz.study.datastructure.tree.TreeUtil.findAllPaths;
import static zzz.study.datastructure.tree.TreeUtil.findAllPathsNonRec;

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
                    treePathSum.test2(t);
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



}
