package zzz.study.datastructure.list;

public class AddTwoNumber {

    public static void main(String[] args) {
        test(new int[] {3}, new int[] {5});
        test(new int[] {3}, new int[] {7});
        test(new int[] {3}, new int[] {9});
        test(new int[] {1}, new int[] {9,9});
        test(new int[] {3,2}, new int[] {6,5});
        test(new int[] {3,2}, new int[] {6,8});
        test(new int[] {3,2}, new int[] {7,7});
        test(new int[] {3,2,6}, new int[] {7,7});
        test(new int[] {3,2,6,8}, new int[] {7,7});
        test(new int[] {3,2}, new int[] {7,7,5});
        test(new int[] {3,2}, new int[] {7,7,5,6});
    }

    public static void test(int[] n1, int[] n2) {
        ListNode h1 = construct(n1);
        ListNode h2 = construct(n2);
        ListNode sum = addTwoNum(h1, h2);
        desc(sum);
        System.out.println();
    }

    public static ListNode addTwoNum(ListNode h1, ListNode h2) {
        ListNode p1 = h1;
        ListNode p2 = h2;
        ListNode res = new ListNode(-1);
        ListNode p = res;
        int inc = 0;
        while (p1 != null && p2 != null) {
            int v = p1.val + p2.val + inc;
            if (v > 9) {
                v = v-10;
                inc=1;
            }
            else {
                inc = 0;
            }
            p.next = new ListNode(v);
            p1 = p1.next;
            p2 = p2.next;
            p = p.next;
        }
        if (inc == 1 && p1 == null && p2 == null) {
            p.next = new ListNode(inc);
            p = p.next;
        }
        if (p1 != null) {
            p.next = addTwoNum(p1, construct(inc));
        }
        if (p2 != null) {
            p.next = addTwoNum(p2, construct(inc));
        }

        return res.next;
    }

    public static ListNode construct(int... nums) {
        ListNode h = new ListNode(-1);
        ListNode p = h;
        for(int i: nums) {
            ListNode n = new ListNode(i);
            p.next = n;
            p = p.next;
        }
        return h.next;
    }

    public static void desc(ListNode h) {
        ListNode p = h;
        while (p!= null) {
            if (p.val < 0) {
                p = p.next;
                continue;
            }
            System.out.print(p.val);
            p = p.next;
        }
    }
}

class ListNode {
    int val;
    ListNode next;
    ListNode(int x) { val = x; }
}
