package zzz.study.datastructure.list;

import java.util.Comparator;


/**
 * 实现带有头结点的单链表  author shuqin1984 2011-3-20
 */

public class MyLinkedList<T> {

    private LinkNode<T> head = new LinkNode<T>(null, null); // 头结点，不计入链表结点范围

    private int size = 0;

    /**
     * mergeList : 合并两个给定的非递减有序序列，返回一个非递减有序序列，并将结果保存到新的链表中
     *
     * @param la lb 要合并的非递减有序序列
     * @return 一个非递减序列，包含有序表 list1 和 list2 中的全部元素
     * @throws Exception 若合并操作失败，则抛出异常.
     *                   <p>
     *                   NOTE： 合并操作会完全破坏原来的序列链表，因此，进行此操作之前，注意先通过操作 copyList() 复制链表。
     */

    public static <T> MyLinkedList<T> mergeList(MyLinkedList<T> la, MyLinkedList<T> lb) {
        MyLinkedList<T> lc = new MyLinkedList<T>();
        LinkNode<T> pa = la.head.next;
        LinkNode<T> pb = lb.head.next;
        LinkNode<T> pc = lc.head;
        lc.size = la.size() + lb.size();

        while (pa != null && pb != null) {
            if (pa.compare(pa.data, pb.data) == -1) {
                pc.next = pa;
                pc = pa;
                pa = pa.next;
            } else {
                pc.next = pb;
                pc = pb;
                pb = pb.next;
            }
        }
        if (pa == null) {
            pc.next = pb;
        }
        if (pb == null) {
            pc.next = pa;
        }
        la.head.next = null;
        lb.head.next = null;
        return lc;
    }

    /**
     * getNodeData : 获取给定链表结点的元素
     */
    public T getNodeData(LinkNode<T> node) {
        if (node != null) {
            return node.data;
        }
        return null;
    }

    /**
     * isEmpty: 判断链表是否为空；若为空，则返回true, 否则返回 false
     */
    public boolean isEmpty() {
        if (size == 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * clear: 清空链表内容
     *
     * @throws Exception 若清空操作失败，则抛出异常
     */
    public void clear() throws Exception {
        if (isEmpty()) {
            return;
        }
        int count = size();
        for (int i = 1; i <= count; i++) {
            System.out.println("删除元素: " + delete(1));
        }
    }

    /**
     * size: 返回链表长度（链表中元素的数目）
     */
    public int size() {
        return size;
    }

    /**
     * search : 在链表中查找给定的元素
     *
     * @param data 要查找的元素
     * @return 若查找成功，返回第一个匹配的元素的结点；否则返回 null.
     */
    public LinkNode<T> search(T data) {
        LinkNode<T> pNode = head.next;
        while (pNode != null && pNode.compare(pNode.data, data) != 0) {
            pNode = pNode.next;
        }
        return pNode;
    }

    /**
     * precessor : 在链表中查找给定元素的前趋
     *
     * @param data 给定元素
     * @return 若查找成功，返回元素的前趋结点，否则返回 null.
     */
    public LinkNode<T> precessor(T data) {
        LinkNode<T> pNode = head.next;
        LinkNode<T> preNode = head;
        while (pNode != null && pNode.compare(pNode.data, data) != 0) {
            pNode = pNode.next;
            preNode = preNode.next;
        }
        if (pNode == null || preNode == head) { // 链表中无此结点，或者给定元素是首元素
            preNode = null;
        }
        return preNode;
    }


    /**
     * successor : 查找给定元素的后继结点
     *
     * @param data 给定元素
     * @return 若查找成功，返回其后继结点，否则返回 null.
     */
    public LinkNode<T> successor(T data) {
        LinkNode<T> pNode = search(data);
        if (pNode == null) {
            return null;
        }
        return pNode.next;
    }


    /**
     * insert: 将给定元素插入到链表的第 i 个位置上
     *
     * @param data 给定元素
     * @param i    指定位置
     * @throws Exception 若指定元素超出链表范围，则抛出异常
     */
    public void insert(T data, int i) throws Exception {
        rangeCheck(i);
        LinkNode<T> pNode = head;
        LinkNode<T> newNode = new LinkNode<T>(data, null);
        while (i > 1 && pNode.next != null) {  // pNode 指向要插入位置的前趋
            pNode = pNode.next;
            i--;
        }
        newNode.next = pNode.next;
        pNode.next = newNode;
        size++;
    }

    /**
     * oneTimeInsert: 从链表的给定位置起始，一次性插入多个元素
     *
     * @param data 要插入的元素数组
     * @throws Exception 若插入失败，抛出异常
     */
    public void oneTimeInsert(T[] datas, int i) throws Exception {
        rangeCheck(i);
        for (T data : datas) {
            insert(data, i++);
        }
    }

    /**
     * rangeCheck : 检测指定位置是否超出链表范围
     *
     * @param i 给定位置
     * @throws Exception 若指定位置超过链表返回，则抛出异常
     */
    private void rangeCheck(int i) throws Exception {
        if (i < 1) {
            throw new Exception("参数错误，指定插入位置必须为正整数!");
        }
        if (i > size + 1) {
            throw new Exception("参数错误，指定位置不能超过: " + (size + 1) + ".");
        }
    }

    /**
     * delete: 删除指定位置上的元素，并将其返回
     *
     * @param i 给定位置
     * @return 被删除的元素
     * @throws Exception 若链表空或者指定元素超出链表范围，则抛出异常
     */
    public LinkNode<T> delete(int i) throws Exception {
        rangeCheck2(i);
        LinkNode<T> pNode = head;
        while (i > 1 && pNode.next != null) {  // pNode 指向要删除元素的前趋
            pNode = pNode.next;
            i--;
        }
        LinkNode<T> delNode = pNode.next;
        pNode.next = pNode.next.next;
        size--;
        return delNode;
    }

    /**
     * deleteElem: 若给定元素在链表中存在，则删除该元素的结点，并返回该结点；否则返回 null
     *
     * @param data 给定元素
     * @return 若给定元素在链表中存在，则删除并返回它；否则返回 null
     */
    public LinkNode<T> deleteElem(T data) {
        LinkNode<T> preNode = precessor(data);
        LinkNode<T> eNode = null;
        if (preNode == null) {  // 前趋结点为 null ， 分两种情况： 给定元素 data 在链表中不存在或者是链表首元素
            if (head.next.data != data) {
                return null;
            } else {
                eNode = head.next;
                head.next = head.next.next;
                size--;
                return eNode;
            }
        }
        eNode = preNode.next;
        preNode.next = preNode.next.next;
        size--;
        return eNode;
    }

    /**
     * addFirst: 将给定元素插入到链表的第一个位置上
     *
     * @param data 给定元素
     * @throws Exception 若插入失败，则抛出异常
     */
    public void addFirst(T data) throws Exception {
        insert(data, 1);
    }

    /**
     * addLast: 将给定元素插入到链表的第一个位置上
     *
     * @param data 给定元素
     * @throws Exception 若插入失败，则抛出异常
     */
    public void addLast(T data) throws Exception {
        insert(data, size + 1);
    }

    /**
     * removeFirst: 删除链表的第一个元素
     *
     * @return 返回被删除的元素
     * @throws Exception 若删除失败，则抛出异常
     */
    public LinkNode<T> removeFirst() throws Exception {
        return delete(1);
    }

    /**
     * removeLast: 删除链表的第一个元素
     *
     * @return 返回被删除的元素
     * @throws Exception 若删除失败，则抛出异常
     */
    public LinkNode<T> removeLast() throws Exception {
        return delete(size);
    }

    /**
     * locateElem: 获得链表中的第 i 个位置的元素
     *
     * @param i 指定位置
     * @return 返回指定位置上的结点元素
     * @throws Exception 若链表空或者指定元素超出链表范围，则抛出异常
     */
    public LinkNode<T> locateElem(int i) throws Exception {
        rangeCheck2(i);
        LinkNode<T> pNode = head.next;
        while (i > 1 && pNode.next != null) {
            pNode = pNode.next;
            i--;
        }
        return pNode;
    }

    /**
     * setElem: 用给定元素设置链表中的指定位置的元素
     *
     * @param i    指定位置
     * @param data 给定元素
     * @throws Exception 若链表空或指定元素超出链表范围，则抛出异常
     */
    public void setElem(T data, int i) throws Exception {
        rangeCheck2(i);
        LinkNode<T> pNode = head.next;
        while (i > 1 && pNode.next != null) {
            pNode = pNode.next;
            i--;
        }
        pNode.data = data;
    }

    /**
     * rangeCheck2 : 检测指定位置是否超出链表范围
     *
     * @param i 给定位置
     * @throws Exception 若链表空或者指定位置超过链表范围，则抛出异常
     */
    private void rangeCheck2(int i) throws Exception {
        if (isEmpty()) {
            throw new Exception("表空，无法删除元素！");
        }
        if (i < 1) {
            throw new Exception("参数错误，指定插入位置必须为正整数!");
        }
        if (i > size) {
            throw new Exception("参数错误，指定位置不能超过表长: " + size + ".");
        }
    }

    /**
     * reverse: 若链表非空，则逆置该链表
     */
    public void reverse() {
        LinkNode<T> lead = head.next;
        LinkNode<T> tail = null, middle = null;
        while (lead != null) {
            tail = middle;
            middle = lead;
            lead = lead.next;
            middle.next = tail;
        }
        head.next = middle;
    }


    /**
     * append : 将指定链表追加到此链表的末尾
     *
     * @param list 给定链表
     */
    public void append(MyLinkedList<T> appendedList) {
        if (appendedList.isEmpty()) {
            return;
        }
        LinkNode<T> pNode = head;
        while (pNode.next != null) {
            pNode = pNode.next;
        }
        pNode.next = appendedList.getFirstNode();
        size += appendedList.size();
    }

    /**
     * getFirstNode : 返回链表中的首元素结点
     */
    private LinkNode<T> getFirstNode() {
        return head.next;
    }

    /**
     * copyList : 复制链表
     *
     * @return 复制后的链表
     * @throws Exception 若复制操作失败，抛出异常
     */
    public MyLinkedList<T> copyList() throws Exception {
        MyLinkedList<T> copy = new MyLinkedList<T>();
        LinkNode<T> pNode = head.next;
        while (pNode != null) {
            copy.insert(pNode.data, copy.size() + 1);
            pNode = pNode.next;
        }
        return copy;
    }

    /**
     * toString: 返回链表的字符串表示
     */
    public String toString() {
        if (isEmpty()) {
            return "表空!";
        }
        LinkNode<T> pNode = head.next;
        StringBuilder sbBuilder = new StringBuilder(" [ ");
        for (; pNode != null; pNode = pNode.next) {
            sbBuilder.append(pNode);
            sbBuilder.append(" ---> ");
        }
        sbBuilder.append("null");
        sbBuilder.append(" ] ");
        return sbBuilder.toString();
    }

    private static class LinkNode<T> implements Comparator<T> {

        private T data;
        private LinkNode<T> next;


        public LinkNode(T data, LinkNode<T> next) {
            this.data = data;
            this.next = next;
        }

        public String toString() {
            String nextData = (next == null ? " " : String.valueOf(next.data));
            return "(" + data + " , " + nextData + ")";
        }

        public int compare(T o1, T o2) {
            return ((Comparable) o1).compareTo(o2);
        }

    }


}
