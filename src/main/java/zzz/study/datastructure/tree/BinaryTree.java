/**
 * BinaryTree: 实现二叉树，可以根据给定的广义表表达式、前序及中序遍历列表、中序及后序遍历列表创建二叉树;
 * 二叉树的前序、中序、后序的递归和非递归遍历，层序遍历;求解二叉树的一些特性
 *
 * @author shuqin1984 2011-3-19
 */

package zzz.study.datastructure.tree;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class BinaryTree {

    /**
     * 用于检测二叉树广义表表达式合法性的编译了的正则表达式:
     * 合法的广义表表达式可以是：
     * [1] 基本表达式： A，A(,), A(B,), A(,B), A(B,C)
     * [2] 上述的 A，B，C 可以是 [1] 中的基本表达式
     * [3] A,B,C 可允许的取值范围由应用决定，这里仅允许是 大小写字母，数字，+-/*%
     * [4] 表达式中不含任何空格符，因此，在检查表达式之前，必须确保表达式中不含空格符
     *
     */
    private static String permittedChars = "[a-zA-Z0-9\\+\\-\\*\\/\\%]";
    private static String basicUnit = "[a-zA-Z0-9\\+\\-\\*\\/\\%\\(\\),]";
    private static Pattern basicPattern = Pattern.compile("" + "|" + permittedChars + "|" + permittedChars + "\\(" + permittedChars + "?," + permittedChars + "?\\)?");
    private static Pattern extendPattern = Pattern.compile(permittedChars + "\\(" + basicUnit + "*," + basicUnit + "*\\)");
    /**    二叉树的广义表表示  */
    private String expression;
    /**      树的根结点   */
    private TreeNode root;

    /**
     * 构造器
     * @param root 树的根结点
     */
    public BinaryTree(TreeNode root) {
        this.root = root;
    }

    /**
     * 构造器
     * @param expression 二叉树的广义表表达式
     */
    private BinaryTree(String expression) {
        this.expression = expression;
    }

    /**
     * createBinaryTree: 根据树的广义表表示构造二叉树
     * @throws Exception
     */
    public static BinaryTree createBinaryTree(String expression) throws Exception {
        BinaryTree bt = new BinaryTree(trimSpace(expression));
        bt.createBinaryTree();
        return bt;
    }

    /**
     * checkValid: 判断给定二叉树的广义表表示是否合法有效
     *
     * @param expression 给定二叉树的广义表表示【字符串形式】
     * @return 如果给定的二叉树广义表表示合法有效，返回true; 否则，返回 false
     *
     */
    private static boolean checkValid(String expression) {
        Matcher m = null;
        if (basicPattern.matcher(expression).matches())
            return true;
        else if ((m = extendPattern.matcher(expression)).matches()) {
            int index = separatorIndex(expression);
            if (index == -1) {  // 不存在能够分割二叉树广义表达式的左右子树表达式的逗号
                return false;
            }
            String rightEx = "";
            String leftEx = "";
            if (index > 2) {
                leftEx = expression.substring(2, index);  // 左子树的广义表达式
            }
            if (index < expression.length() - 2) {
                rightEx = expression.substring(index + 1, expression.length() - 1);  // 右子树的广义表达式
            }
            return checkValid(leftEx) && checkValid(rightEx);
        } else {
            return false;
        }
    }

    /**
     * createBinaryTree: 根据前序和中序遍历列表生成二叉树
     *
     * @param preOrderList  前序列表字符串
     * @param inOrderList   中序列表字符串
     * @throws Exception
     *
     */
    public static BinaryTree createBinaryTree(String preOrderList, String inOrderList) throws Exception {
        BinaryTree bt = new BinaryTree(getGeneralList(preOrderList, inOrderList));
        bt.createBinaryTree();
        return bt;
    }

    /**
     * getGeneralist: 根据前序和中序遍历列表生成二叉树的广义表表示【字符串形式】
     *
     * @param preOrderList  前序列表字符串
     * @param inOrderList   中序列表字符串
     * @return generalList  广义表表示
     *
     */

    private static String getGeneralList(String preOrderList, String inOrderList) {

        String s = "";
        if (preOrderList.length() > 0 || inOrderList.length() > 0) {

            // 如果只含一个结点值，就直接返回
            if (preOrderList.length() == 1)
                return preOrderList;

            // 根据前序遍历， 第一个是根结点的值
            char ch = preOrderList.charAt(0);

            // 根据中序遍历及根结点，将前序列表分为左右子树列表。
            int index = inOrderList.indexOf(ch);
            String inOrderLeft = inOrderList.substring(0, index);                     // 左子树的中序遍历列表
            String inOrderRight = inOrderList.substring(index + 1);                     // 右子树的中序遍历列表
            String preOrderLeft = preOrderList.substring(1, inOrderLeft.length() + 1);  // 左子树的前序遍历列表
            String preOrderRight = preOrderList.substring(inOrderLeft.length() + 1);    // 右子树的前序遍历列表
            s += ch;
            s += "(" + getGeneralList(preOrderLeft, inOrderLeft) + "," + getGeneralList(preOrderRight, inOrderRight) + ")";
        }
        return s;
    }

    /**
     * createBinaryTree: 根据中序和后序遍历列表生成二叉树
     *
     * @param  inOrderList   中序列表
     * @param  postOrderList 后序列表
     * @param  flag          标记
     *
     * @throws Exception
     */
    public static BinaryTree createBinaryTree(String inOrderList, String postOrderList, boolean flag) throws Exception {
        BinaryTree bt = new BinaryTree(getGeneralList(inOrderList, postOrderList, flag));
        bt.createBinaryTree();
        return bt;
    }

    /**
     * getGeneralList: 根据中序和后序遍历生成二叉树的广义表表示【字符串形式】
     *
     * @param  inOrderList   中序列表
     * @param  postOrderList 后序列表
     * @param  flag          标记
     * @return generalList   广义表表示
     *
     */

    private static String getGeneralList(String inOrderList, String postOrderList, boolean flag) {

        String s = "";
        if (inOrderList.length() > 0 || postOrderList.length() > 0) {

            // 如果只含一个结点值，就直接返回
            if (inOrderList.length() == 1)
                return inOrderList;

            // 根据后序遍历规则，最后一个是根结点的值
            char ch = postOrderList.charAt(postOrderList.length() - 1);

            // 根据中序遍历及根结点，将前序列表分为左右子树列表。
            int index = inOrderList.indexOf(ch);
            String inOrderLeft = inOrderList.substring(0, index);                     // 左子树的中序遍历列表
            String inOrderRight = inOrderList.substring(index + 1);                     // 右子树的中序遍历列表
            String postOrderLeft = postOrderList.substring(0, inOrderLeft.length());  // 左子树的前序遍历列表
            String postOrderRight = postOrderList.substring(inOrderLeft.length(),
                    inOrderLeft.length() + inOrderRight.length());    // 右子树的前序遍历列表
            s += ch;
            s += "(" + getGeneralList(inOrderLeft, postOrderLeft, true) + "," + getGeneralList(inOrderRight, postOrderRight, true) + ")";
        }
        return s;
    }

    /**
     * trimSpace: 将广义表表达式字符串中的空格符去掉
     */
    private static String trimSpace(String s) {
        StringBuilder sb = new StringBuilder("");
        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            if (!(new Character(ch).toString().matches("\\s"))) {
                sb.append(ch);
            }
        }
        return sb.toString();
    }

    /**
     * separatorIndex : 求解广义表表达式中分割左右子树的分隔符的位置
     *                  由于这里使用逗号分割左右子树，则当且仅当其位置应当满足：
     *                  在该分割符之前，左括号的数目恰好比右括号的数目多1.
     * @return 若存在满足条件的分隔符，则返回其位置；否则返回 -1
     */
    private static int separatorIndex(String expression) {
        int leftBracketCounter = 0, rightBacketCounter = 0;
        int index = 0;
        for (; index < expression.length(); index++) {
            char ch = expression.charAt(index);
            if (ch == '(') {
                leftBracketCounter++;
            } else if (ch == ')') {
                rightBacketCounter++;
            } else if (ch == ',') {
                if (leftBracketCounter == rightBacketCounter + 1) break;
            }
        }
        if (index < expression.length()) {
            return index;
        }
        return -1;
    }

    /**
     *  【设置/获取】属性
     */

    public TreeNode getRoot() {
        return root;
    }

    public void setRoot(TreeNode root) {
        this.root = root;
    }

    /**
     * getPreOrderList: 获得树的先序遍历列表
     * @param flag 是否采用递归遍历的标记；若 flag = true， 采用递归遍历；否则，采用非递归遍历
     * @return 二叉树的先序遍历列表
     */

    public List<TreeNode> getPreOrderList(boolean flag) {

        List<TreeNode> nodelist = new ArrayList<TreeNode>();
        if (flag == true) {
            nodelist = preOrderTraverse(getRoot(), nodelist);
        } else {
            nodelist = preOrderTraverseIter(getRoot());
        }
        return nodelist;
    }

    /**
     * getInOrderList: 获得树的中序遍历列表
     * @param flag 是否采用递归遍历的标记；若 flag = true， 采用递归遍历；否则，采用非递归遍历
     * @return 获得树的中序遍历列表
     */

    public List<TreeNode> getInOrderList(boolean flag) {

        List<TreeNode> nodelist = new ArrayList<TreeNode>();
        if (flag == true) {
            nodelist = inOrderTraverse(getRoot(), nodelist);
        } else {
            nodelist = inOrderTraverseIter(getRoot());
        }
        return nodelist;
    }

    /**
     * getPostOrderList: 获得树的后序遍历列表
     * @param flag 是否采用递归遍历的标记；若 flag = true， 采用递归遍历；否则，采用非递归遍历
     * @return 获得树的后序遍历列表
     */

    public List<TreeNode> getPostOrderList(boolean flag) {

        List<TreeNode> nodelist = new ArrayList<TreeNode>();
        if (flag == true) {
            nodelist = postOrderTraverse(getRoot(), nodelist);
        } else {
            nodelist = postOrderTraverseIter(getRoot());
        }
        return nodelist;
    }

    /**
     * 获得树的层序遍历列表
     *
     * @return 获得树的层序遍历列表
     */

    public List<TreeNode> getFloorOrderList() {

        List<TreeNode> nodelist = new ArrayList<TreeNode>();
        nodelist = floorOrderTraverse(getRoot());
        return nodelist;
    }

    /**
     * createBinaryTree: 根据二叉树的广义表表达式来创建二叉树
     * @exception 若二叉树的广义表表达式不合法，则抛出异常
     */
    private void createBinaryTree() throws Exception {

        // 检查传入的二叉树广义表示法是否合法有效
        if (!checkValid(expression))
            throw new Exception("广义表表达式不合法，无法创建二叉树!");

        // 使用 LinkedList 来执行栈的功能
        LinkedList<TreeNode> stack = new LinkedList<TreeNode>();
        TreeNode newnode = null;
        int flag = 0;  //  flag = 0: 创建左子树 | flag = 1: 创建右子树

        for (char ch : expression.toCharArray()) {
            switch (ch) {

                // 遇到  "(" 时，表示该结点可能有孩子结点，将该父结点压入栈中
                case '(':
                    stack.push(newnode);
                    flag = 0;
                    break;

                // 遇到  ")" 时，表示已经扫描完父结点的右孩子结点的值，弹出该父结点
                case ')':
                    stack.pop();
                    break;

                // 遇到 "," 时， 表示将要扫描父结点的右孩子结点的值。
                case ',':
                    flag = 1;
                    break;

                // 遇到结点的值，将其加入二叉树中
                default:

                    newnode = new TreeNode(ch, null, null);
                    if (root == null) {
                        root = newnode;
                    } else {
                        if (flag == 0) {
                            TreeNode topnode = stack.peek();
                            topnode.setLchild(newnode);
                        } else {
                            TreeNode topnode = stack.peek();
                            topnode.setRchild(newnode);
                        }
                    }
                    break;
            }
        }
    }

    /**
     * getGeneralList: 获取该二叉树的广义表表示(字符串表示)
     */
    public String getGeneralListString() {
        StringBuilder sb = new StringBuilder("");
        if (expression == null) {
            createGeneralList(root, sb);
            return sb.toString();
        }
        return expression;
    }

    /**
     * getGeneralList: 根据给定二叉树生成其广义表表示(字符串形式)
     * @param root 树的根结点
     * @return 树的广义表表示【字符串形式】
     */
    private void createGeneralList(TreeNode root, StringBuilder sb) {

        if (root != null) {

            sb.append(root.getCh());
            if (root.getLchild() != null || root.getRchild() != null) {
                sb.append('(');
                if (root.getLchild() != null) {
                    createGeneralList(root.getLchild(), sb);
                }
                sb.append(',');
                if (root.getRchild() != null) {
                    createGeneralList(root.getRchild(), sb);
                }
                sb.append(')');
            }
        }
    }

    /**
     * size: 获取二叉树的结点总数
     *
     * @param root 树的根结点
     * @return 树的结点总数
     *
     */
    public int size(TreeNode root) {

        if (root == null)
            return 0;
        else {
            return size(root.getLchild()) + size(root.getRchild()) + 1;
        }
    }

    /**
     * leafCounter: 获取二叉树的叶子结点数目
     *
     * @param root 树的根结点
     * @return 树的叶子结点数目
     *
     */
    public int leafCounter(TreeNode root) {
        if (root == null)
            return 0;
        else {
            if (root.getLchild() == null && root.getRchild() == null)
                return 1;
            else
                return leafCounter(root.getLchild()) + leafCounter(root.getRchild());
        }
    }

    /**
     * getLeafNodes : 获取该二叉树的所有叶子结点
     */
    public List<TreeNode> getLeafNodes() {
        List<TreeNode> leaflist = new ArrayList<TreeNode>();
        getLeafNodes(getRoot(), leaflist);
        return leaflist;
    }

    /**
     * printLeafPaths : 打印该二叉树的所有叶子结点到根的路径
     */
    public void printLeafPaths() {
        List<TreeNode> leafPath = new ArrayList<TreeNode>();
        buildLeafPaths(root, leafPath);
    }

    /**
     * buildLeafPaths : 递归求解给定二叉树的所有叶子结点到根的路径
     * @param root 给定二叉树的根结点
     * @param path 存放某个叶子结点到根的路径
     */
    public void buildLeafPaths(TreeNode root, List<TreeNode> path) {
        if (root != null) {
            path.add(root);  // 将从根结点到叶子结点的路径上的结点保存起来
            if (root.getLchild() == null && root.getRchild() == null) { // 到达叶子结点，完成一条路径，并可对其处理
                processPath(path);
            } else {
                buildLeafPaths(root.getLchild(), path);
                if (root.getLchild() != null) {
                    path.remove(path.size() - 1);  // 回溯，从左子树到右子树，删除前一条路径上的叶子结点
                }
                buildLeafPaths(root.getRchild(), path);
                if (root.getRchild() != null) {
                    path.remove(path.size() - 1);
                }
            }
        }
    }

    /**
     * processPath : 处理从某叶子结点到根结点的路径的操作
     */
    private void processPath(List<TreeNode> path) {
        System.out.println(listToString(path));
    }

    /**
     * getLeafNodes: 递归求解给定二叉树的所有叶子结点
     * @param root   给定二叉树的根结点
     * @param leaflist 给定二叉树的所有叶子结点
     */
    private void getLeafNodes(TreeNode root, List<TreeNode> leaflist) {
        if (root != null) {
            if (root.getLchild() == null && root.getRchild() == null) {
                leaflist.add(root);
                return;
            }
            getLeafNodes(root.getLchild(), leaflist);
            getLeafNodes(root.getRchild(), leaflist);
        }
    }

    /**
     * height: 获取二叉树的高度
     *
     * @param root   树的根结点
     * @return 树的高度
     *
     */

    public int height(TreeNode root) {
        if (root == null)
            return 0;
        else
            return 1 + Math.max(height(root.getLchild()), height(root.getRchild()));
    }

    /**
     * getNodelinkList: 获取该二叉树的结点关联列表
     * @return 树的结点关联列表
     */
    public List<NodeLink> getNodelinkList() {

        List<NodeLink> linklist = new ArrayList<NodeLink>();
        createNodelinkList(getRoot(), linklist);
        return linklist;

    }

    /**
     * createNodelinkList: 递归求解给定二叉树的结点关联列表表示
     * @param root 给定二叉树的根结点
     * @param linklist 存放给定二叉树的结点关联对象
     */
    private void createNodelinkList(TreeNode root, List<NodeLink> linklist) {

        if (root != null) {
            if (root.getLchild() != null) {
                NodeLink nodelink = new NodeLink(root, root.getLchild());
                linklist.add(nodelink);
                createNodelinkList(root.getLchild(), linklist);
            }
            if (root.getRchild() != null) {
                NodeLink nodelink = new NodeLink(root, root.getRchild());
                linklist.add(nodelink);
                createNodelinkList(root.getRchild(), linklist);
            }
        }
    }

    /**
     * preOrderTraverse: 二叉树的递归先序遍历
     *
     */
    private List<TreeNode> preOrderTraverse(TreeNode root, List<TreeNode> nodelist) {

        if (root != null) {
            nodelist.add(root);
            preOrderTraverse(root.getLchild(), nodelist);
            preOrderTraverse(root.getRchild(), nodelist);
        }

        return nodelist;
    }

    /**
     * preOrderTraverseIter: 二叉树的非递归先序遍历
     */
    private List<TreeNode> preOrderTraverseIter(TreeNode root) {
        LinkedList<TreeNode> stack = new LinkedList<TreeNode>();
        List<TreeNode> nodelist = new ArrayList<TreeNode>();
        TreeNode pNode = root;
        for (; ; ) {
            while (pNode != null) {
                nodelist.add(pNode);        // 访问根结点
                stack.push(pNode);          // 根结点入栈
                pNode = pNode.getLchild();  // 访问左子树
            }
            pNode = stack.pop();
            pNode = pNode.getRchild();      // 访问右子树
            if (pNode == null && stack.isEmpty()) {
                break;
            }
        }
        return nodelist;
    }

    /**
     * inOrderTraverse: 二叉树的递归中序遍历
     *
     */
    private List<TreeNode> inOrderTraverse(TreeNode root, List<TreeNode> nodelist) {

        if (root != null) {
            inOrderTraverse(root.getLchild(), nodelist);
            nodelist.add(root);
            inOrderTraverse(root.getRchild(), nodelist);
        }

        return nodelist;
    }

    /**
     * inOrderTraverseIter: 二叉树的非递归中序遍历
     */

    private List<TreeNode> inOrderTraverseIter(TreeNode root) {

        LinkedList<TreeNode> stack = new LinkedList<TreeNode>();
        List<TreeNode> nodelist = new ArrayList<TreeNode>();

        TreeNode pNode = root;
        for (; ; ) {
            while (pNode != null) {            // 访问左子树
                stack.push(pNode);
                pNode = pNode.getLchild();
            }
            pNode = stack.pop();
            nodelist.add(pNode);               // 访问根结点
            pNode = pNode.getRchild();           // 访问右子树
            if (pNode == null && stack.isEmpty()) {
                break;
            }
        }

        return nodelist;
    }

    /**
     * postOrderTraverse: 二叉树的递归后序遍历
     */
    private List<TreeNode> postOrderTraverse(TreeNode root, List<TreeNode> nodelist) {

        if (root != null) {
            postOrderTraverse(root.getLchild(), nodelist);
            postOrderTraverse(root.getRchild(), nodelist);
            nodelist.add(root);
        }

        return nodelist;
    }

    /**
     * postOrderTraverseIter: 二叉树的非递归后序遍历
     */
    private List<TreeNode> postOrderTraverseIter(TreeNode root) {

        LinkedList<TreeNode> stack = new LinkedList<TreeNode>();
        List<TreeNode> nodelist = new ArrayList<TreeNode>();

        int flag = 0;  // 标识是否访问过右子树; flag = 0 表示没有访问； flag = 1 表示已经访问过
        TreeNode pNode = root;
        TreeNode tmpNode = null;
        loop1:
        for (; ; ) {
            while (pNode != null) {        // 访问左子树
                stack.push(pNode);
                pNode = pNode.getLchild();
                flag = 0;
            }
            loop2:
            for (; ; ) {
                if (!stack.isEmpty()) {

                    if (flag == 0)               // 尚未访问根结点的右子树
                    {
                        pNode = stack.peek();     // 取根结点的右子树，访问其右子树
                        pNode = pNode.getRchild();
                        flag = 1;
                        continue loop1;
                    }

                    if (flag == 1) {            // 已经访问过右子树
                        pNode = stack.pop();
                        nodelist.add(pNode);    // 访问根结点，实际上是左右子树均为空的叶子结点
                        tmpNode = pNode;        // 访问某个结点后，立即访问其父结点的右子树
                        pNode = stack.peek();   // 取该结点的父结点
                        if (pNode != null) {    // 父结点不为空(没有回溯到整棵树的根结点)
                            if (tmpNode == pNode.getRchild()) {
                                // 如果刚刚访问的结点正是其父结点的右孩子，则直接回溯访问其父结点;
                                continue loop2;
                            } else {  // 否则，访问其父结点的右子树
                                pNode = pNode.getRchild();
                                continue loop1;
                            }
                        }

                    }


                } else   // 栈空，递归调用结束，退出
                {
                    break loop1;
                }
            }

        }
        return nodelist;
    }

    /**
     * floorOrderTraverse: 二叉树的层序遍历
     *
     * @param  root 树的根结点
     * @return 树的层序遍历列表
     *
     */
    private List<TreeNode> floorOrderTraverse(TreeNode root) {

        // 使用 LinkedList 来执行队列的功能
        LinkedList<TreeNode> queue = new LinkedList<TreeNode>();
        List<TreeNode> nodelist = new ArrayList<TreeNode>();
        if (root != null) {
            nodelist.add(root);
            queue.addLast(root);
            while (queue.size() > 0) {
                TreeNode node = queue.removeFirst();
                if (node.getLchild() != null) {
                    nodelist.add(node.getLchild());
                    queue.addLast(node.getLchild());
                }
                if (node.getRchild() != null) {
                    nodelist.add(node.getRchild());
                    queue.addLast(node.getRchild());
                }
            }
        }

        return nodelist;
    }

    /**
     * copyBinaryTree: 复制二叉树
     * @return 复制后的二叉树
     */
    public BinaryTree copyBinaryTree() {
        TreeNode anotherRoot = null;
        anotherRoot = copy(getRoot());
        return new BinaryTree(anotherRoot);
    }

    /**
     * copy: 复制二叉树
     * @param srcRoot 要复制的二叉树的根结点
     * @param destRoot 目标二叉树的根结点
     */
    private TreeNode copy(TreeNode srcRoot) {
        if (srcRoot != null) {
            TreeNode newNode = new TreeNode(srcRoot.getCh(), null, null);
            newNode.setLchild(copy(srcRoot.getLchild()));
            newNode.setRchild(copy(srcRoot.getRchild()));
            return newNode;
        }
        return null;
    }

    /**
     * equalsTo: 比较该二叉树与给定二叉树 another 是否全等；
     *           若全等则返回 true, 否则返回 false.
     */
    public boolean equalsTo(BinaryTree another) {
        return compareEqual(root, another.getRoot());
    }

    /**
     * equalsTo : 比较给定的两个二叉树是否全等
     *            两个二叉树全等当且仅当
     *            A. 两个二叉树均为空; 或者
     *            B. 两个二叉树均非空，且所有对应位置的结点都相同，对应结点之间的关联也完全相同.
     */
    private boolean compareEqual(TreeNode root, TreeNode anotherRoot) {
        return (root == null && anotherRoot == null) ||
                ((root != null && anotherRoot != null) &&
                        (root.getCh() == anotherRoot.getCh()) &&
                        (compareEqual(root.getLchild(), anotherRoot.getLchild())) &&
                        (compareEqual(root.getRchild(), anotherRoot.getRchild())));
    }

    /**
     * swapTree : 将该二叉树的所有结点的左右孩子互换
     */
    public void swapTree() {
        StringBuilder sb = new StringBuilder("");
        swapTree(root);
        createGeneralList(root, sb);
        expression = sb.toString();
    }

    /**
     * swapTree : 将给定的二叉树的所有结点的左右孩子互换
     * @param root 给定二叉树的根结点
     */
    private void swapTree(TreeNode root) {
        if (root != null) {
            TreeNode tmp = root.getLchild();
            root.setLchild(root.getRchild());
            root.setRchild(tmp);
            swapTree(root.getLchild());
            swapTree(root.getRchild());
        }
    }

    /**
     * longestPath: 获取该二叉树中的一条最长路径
     * @return 二叉树中的一条最长路径
     */
    public List<TreeNode> longestPath() {
        List<TreeNode> longestPath = new ArrayList<TreeNode>();
        longestPath(root, longestPath);
        return longestPath;
    }

    /**
     * longestPath: 递归求解给定二叉树的一条最长路径
     * @param root  给定二叉树的根结点
     * @param longestPath 存放二叉树的最长路径上的结点
     */
    private void longestPath(TreeNode root, List<TreeNode> longestPath) {
        if (root != null) {
            longestPath.add(root);
            if (root.getLchild() == null && root.getRchild() == null) { // 左右子树均空
                return;
            }
            if (root.getLchild() != null && root.getRchild() == null) { // 左子树非空，右子树为空，则最长路径的结点必在左子树路径上
                longestPath(root.getLchild(), longestPath);
            }
            if (root.getLchild() == null && root.getRchild() != null) { // 左子树非空，右子树为空，则最长路径的结点必在右子树路径上
                longestPath(root.getRchild(), longestPath);
            }
            if (root.getLchild() != null && root.getRchild() != null) { // 左右子树均非空;分别求解左右子树的最长路径，取最大者
                List<TreeNode> leftLongestPath = new ArrayList<TreeNode>();
                List<TreeNode> rightLongestPath = new ArrayList<TreeNode>();
                longestPath(root.getLchild(), leftLongestPath);
                longestPath(root.getRchild(), rightLongestPath);
                if (leftLongestPath.size() >= rightLongestPath.size()) {
                    longestPath.addAll(leftLongestPath);
                } else if (leftLongestPath.size() < rightLongestPath.size()) {
                    longestPath.addAll(rightLongestPath);
                }

            }
        }
    }

    /**
     * listToString: 返回二叉树的结点列表的字符串表示
     *
     * @param nodelist  树的结点列表
     * @return 二叉树的结点列表的字符串表示
     *
     */
    public String listToString(List<TreeNode> nodelist) {

        if (nodelist == null || nodelist.size() == 0) {
            return "[ 空树 ]";
        }
        StringBuilder str = new StringBuilder("[");
        Iterator<TreeNode> iter = nodelist.iterator();
        while (iter.hasNext()) {
            TreeNode node = iter.next();
            str.append(node.getCh() + " ");
        }
        str.deleteCharAt(str.length() - 1);
        str.append(']');
        return str.toString();
    }

    /**
     * 树结点实现
     */
    private class TreeNode {

        private char ch;
        private TreeNode rchild;
        private TreeNode lchild;


        public TreeNode(char ch, TreeNode rchild, TreeNode lchild) {
            this.ch = ch;
            this.rchild = rchild;
            this.lchild = lchild;
        }

        public char getCh() {
            return ch;
        }

        public TreeNode getRchild() {
            return rchild;
        }

        public void setRchild(TreeNode rchild) {
            this.rchild = rchild;
        }

        public TreeNode getLchild() {
            return lchild;
        }

        public void setLchild(TreeNode lchild) {
            this.lchild = lchild;
        }

        public String toString() {
            return "" + getCh();
        }

    }

    /**
     * 结点关联类
     */
    private class NodeLink {

        private TreeNode node1;
        private TreeNode node2;

        public NodeLink(TreeNode node1, TreeNode node2) {
            this.node1 = node1;
            this.node2 = node2;
        }

        public String toString() {

            return "(" + node1.getCh() + "," + node2.getCh() + ")";
        }


    }

}
