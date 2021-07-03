package zzz.study.datastructure.tree;


public class BinaryTreeTester {

    public static void testInvalid(String generalList) {
        try {
            BinaryTree.createBinaryTree(generalList);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void testTreeInfo(BinaryTree bt) {

        System.out.println("二叉树的广义表表示：" + bt.getGeneralListString());
        System.out.println("二叉树高度: " + bt.height(bt.getRoot()));
        System.out.println("二叉树总结点数： " + bt.size(bt.getRoot()));
        System.out.println("二叉树总叶子结点数： " + bt.leafCounter(bt.getRoot()));
        System.out.println("二叉树的所有叶子结点: " + bt.listToString(bt.getLeafNodes()));
        System.out.println("二叉树的所有叶子结点到根的路径: ");
        bt.printLeafPaths();
        System.out.println("二叉树中的一条最长路径: " + bt.listToString(bt.longestPath()));

    }

    public static void testTraverse(BinaryTree bt) {

        System.out.println("二叉树的结点关联表示：");
        System.out.println(bt.getNodelinkList());

        System.out.println("二叉树的先序遍历：");
        System.out.println("递归: " + bt.listToString(bt.getPreOrderList(true)));
        System.out.println("非递归: " + bt.listToString(bt.getPreOrderList(false)));

        System.out.println("二叉树的中序遍历：");
        System.out.println("递归:" + bt.listToString(bt.getInOrderList(true)));
        System.out.println("非递归:" + bt.listToString(bt.getInOrderList(false)));

        System.out.println("二叉树的后序遍历：");
        System.out.println("递归: " + bt.listToString(bt.getPostOrderList(true)));
        System.out.println("非递归:" + bt.listToString(bt.getPostOrderList(false)));

        System.out.println("二叉树的层序遍历：");
        System.out.println(bt.listToString(bt.getFloorOrderList()));

    }

    public static void testAll(BinaryTree bt) {
        testTreeInfo(bt);
        testTraverse(bt);
    }

    public static void main(String[] args) {

        String expression = "	-(  +(a, *(b, -(c,d))), /(e,f)\t)	";
        // String expression = "b( , %)";
        BinaryTree bt = null;

        try {
            bt = BinaryTree.createBinaryTree(expression);
            testAll(bt);
            System.out.println();

            System.out.println(" ----------- 根据前序及中序遍历列表构造二叉树  ------------ ");
            bt = BinaryTree.createBinaryTree("ABCDEFG", "CBEDAFG");
            testAll(bt);
            System.out.println();

            System.out.println(" ----------- 根据中序及后序遍历列表构造二叉树  ------------ ");
            bt = BinaryTree.createBinaryTree("CBEDAFG", "CEDBGFA", true);
            testAll(bt);
            System.out.println();

            System.out.println("复制二叉树: ********************* ");
            BinaryTree copy = bt.copyBinaryTree();

            testAll(copy);
            System.out.println("*************  二叉树比较： bt VS copy : ****************** ");
            System.out.println("bt: " + bt.getGeneralListString());
            System.out.println("copy: " + copy.getGeneralListString());
            System.out.println("比较广义表表示是否相等? " + bt.getGeneralListString().equals(copy.getGeneralListString()));
            System.out.println("比较给定二叉树是否全等? " + (bt.equalsTo(copy) ? "是" : "否"));

            testInvalid("A(B(C,D)");
            testInvalid("A(B,), C(D)");
            testInvalid("A(B(C, D),E),F),)");
            testInvalid("A(B)");
            testInvalid("AB(C,D)");

            bt = BinaryTree.createBinaryTree("A(B(D, ), C(E,F))");
            testAll(bt);
            bt.swapTree();
            testAll(bt);

        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

    }

}
