package zzz.study.datastructure.list;

public class LinkedListOperation {

    public static void main(String[] args) {
        ListNode h = construct(3);
        ListNode reversed = reverse(h);
        desc(reversed);
        System.out.println();

        ListNode h2 = construct(3, 4, 5, 6);
        ListNode reversed2 = reverse(h2);
        desc(reversed2);
        System.out.println();

        ListNode s1 = construct(1, 2, 4, 6, 8);
        ListNode s2 = construct(3, 7, 9, 10, 12);
        desc(mergeSorted(s1, s2));
        System.out.println();

        ListNode s3 = construct();
        ListNode s4 = construct();
        desc(mergeKLists(new ListNode[]{s3, s4}));

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
        for (int i : nums) {
            ListNode n = new ListNode(i);
            p.next = n;
            p = p.next;
        }
        return h.next;
    }

    public static void desc(ListNode h) {
        ListNode p = h;
        while (p != null) {
            if (p.val < 0) {
                p = p.next;
                continue;
            }
            System.out.print(p.val + "-");
            p = p.next;
        }
    }

    public static ListNode mergeKLists(ListNode[] lists) {
        if (lists == null || lists.length == 0) {
            return null;
        }
        if (lists.length == 1) {
            return lists[0];
        }
        ListNode result = lists[0];
        for (int i = 1; i < lists.length; i++) {
            result = mergeSorted(result, lists[i]);
        }
        ListNode p = result;
        while (p != null && p.val == -1) {
            p = p.next;
        }
        return p;
    }

    public static ListNode mergeSorted(ListNode s1, ListNode s2) {
        if (s1 == null) {
            return s2;
        }
        if (s2 == null) {
            return s1;
        }
        ListNode res = new ListNode(-1);
        ListNode cur = res;
        while (s1 != null && s2 != null) {
            if (s1.val < s2.val) {
                cur.next = s1;
                s1 = s1.next;
            } else {
                cur.next = s2;
                s2 = s2.next;
            }
            cur = cur.next;
        }
        if (s1 == null) {
            cur.next = s2;
        }
        if (s2 == null) {
            cur.next = s1;
        }
        return res;

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
                v = v - 10;
                inc = 1;
            } else {
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

    ListNode(int x) {
        val = x;
    }
}
