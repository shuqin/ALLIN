package zzz.study.datastructure.list;

public class LinkedListOperation {

    public static void main(String[]args) {
        ListNode h = construct(3);
        ListNode reversed = reverse(h);
        desc(reversed);

        ListNode h2 = construct(3,4,5,6);
        ListNode reversed2 = reverse(h2);
        desc(reversed2);
    }

    public static ListNode reverse(ListNode h) {
        if (h == null || h.next == null) {
            return h;
        }
        ListNode p = h;
        ListNode q = p.next;
        p.next = null;
        while (q != null) {
            ListNode t = q.next;
            q.next = p;
            p = q;
            q = t;
        }
        return p;
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


}

class ListNode {
    int val;
    ListNode next;
    ListNode(int x) { val = x; }
}
