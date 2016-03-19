package zzz.study.datastructure.tree;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class TreePathSum {

    TreeUtil treeUtil = new TreeUtil();
    Random rand = new Random(System.currentTimeMillis());

    public static void main(String[] args) {

        try {
            // time for starting up arthas trace method commands
            TimeUnit.SECONDS.sleep(25);
        } catch (InterruptedException e) {
            //
        }


        TreePathSum treePathSum = new TreePathSum();

        System.out.println("---------- Validating ----------");

        //treePathSum.testValidate();

        System.out.println("---------- Performance ----------");

        treePathSum.testNonRecPerformance();
    }

    public void test(TreeNode root) {

        System.out.println("Rec Implementation");

        List<Path> paths = treeUtil.findAllPaths(root);
        Long sum = paths.stream().collect(Collectors.summarizingLong(Path::getValue)).getSum();
        System.out.println(paths);
        System.out.println(sum);

        System.out.println("Non Rec Implementation");

        List<Path> paths2 = treeUtil.findAllPathsNonRec(root);
        Long sum2 = paths2.stream().collect(Collectors.summarizingLong(Path::getValue)).getSum();
        System.out.println(paths2);
        System.out.println(sum2);

        assert sum == sum2;
    }

    public void testValidate() {

        TreePathSum treePathSum = new TreePathSum();
        Method[] methods = treePathSum.getClass().getDeclaredMethods();

        for (Method m : methods) {
            if (m.isAnnotationPresent(TreeBuilder.class)) {
                try {
                    TreeNode t = (TreeNode) m.invoke(treePathSum, null);
                    System.out.println("height: " + t.height());
                    treePathSum.test2(t);
                } catch (Exception ex) {
                    System.err.println(ex.getMessage());
                }

            }
        }

        for (int i = 0; i < 100; i++) {
            for (int j = 1; j < 10; j++) {
                TreeNode t = treePathSum.buildTreeWithRandom(j);
                treePathSum.test2(t);
            }
        }
    }

    public void test2(TreeNode root) {

        System.out.println("Rec Implementation");
        List<Path> paths = treeUtil.findAllPaths(root);
        System.out.println(paths);

        System.out.println("Non Rec Implementation");
        List<Path> paths2 = treeUtil.findAllPathsNonRec(root);
        System.out.println(paths2);

        System.out.println("Effectively Non Rec Implementation");
        List<Path> paths3 = treeUtil.findAllPathsNonRecEffectively(root);
        System.out.println(paths3);

        assert paths.size() == paths2.size();
        assert paths.size() == paths3.size();
        for (int i = 0; i < paths.size(); i++) {
            assert paths.get(i).toString().equals(paths2.get(i).toString());
            assert paths.get(i).toString().equals(paths3.get(i).toString());
        }

        System.out.println("Validate OK");

    }

    // use arthas trace to measure costs of findAllPathsNonRec
    // trace zzz.study.datastructure.tree.TreePathSum findAllPathsNonRec
    public void testNonRecPerformance() {

        for (int h = 10; h < 30; h++) {

            System.out.println("h: " + h);

            for (int c = 0; c < 50; c++) {
                TreeNode t = buildTreeWithRandom(h);
                System.out.println("Tree Height : " + t.height());

                long start = System.currentTimeMillis();
                List<Path> paths = treeUtil.findAllPathsNonRec(t);
                long end = System.currentTimeMillis();
                //List<Path> pathsEffectively = treeUtil.findAllPathsNonRecImprove(t);

                long endEffectively = System.currentTimeMillis();

                System.out.println("time cost: non-rec -- " + (end - start) + " Effectively -- " + (endEffectively - end));
            }

        }

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
        return buildTreeWithR(5, 2);
    }

    public TreeNode buildTreeWithR(int rootVal, int rightVal) {
        TreeNode tree = new TreeNode(rootVal);
        TreeNode right = new TreeNode(rightVal);
        tree.right = right;
        return tree;
    }

    @TreeBuilder
    public TreeNode buildTreeWithLR() {
        return buildTreeWithLR(5, 1, 2);
    }

    public TreeNode buildTreeWithLR(int rootVal, int leftVal, int rightVal) {
        TreeNode tree = new TreeNode(rootVal);
        TreeNode left = new TreeNode(leftVal);
        TreeNode right = new TreeNode(rightVal);
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
            case 0:
                return buildTreeWithL(rand.nextInt(9), rand.nextInt(9));
            case 1:
                return buildTreeWithR(rand.nextInt(9), rand.nextInt(9));
            case 2:
                return buildTreeWithLR(rand.nextInt(9), rand.nextInt(9), rand.nextInt(9));
            default:
                return buildTreeOnlyRoot();
        }
    }

    public TreeNode linkRandom(TreeNode t1, TreeNode t2) {
        if (t2.left == null) {
            t2.left = t1;
        } else if (t2.right == null) {
            t2.right = t1;
        } else {
            int c = rand.nextInt(4);
            switch (c) {
                case 0:
                    t2.left.left = t1;
                case 1:
                    t2.left.right = t1;
                case 2:
                    t2.right.left = t1;
                case 3:
                    t2.right.right = t1;
                default:
                    t2.left.left = t1;
            }
        }
        return t2;
    }

    @TreeBuilder
    public TreeNode buildTreeWithRandom() {
        TreeNode root = treeWithRandom();
        int i = 3;
        while (i > 0) {
            TreeNode t = treeWithRandom();
            root = linkRandom(root, t);
            i--;
        }
        return root;
    }

    public TreeNode buildTreeWithRandom(int h) {
        TreeNode root = treeWithRandom();
        while (h > 0) {
            TreeNode t = treeWithRandom();
            root = linkRandom(root, t);
            h--;
        }
        return root;
    }


}
