package zzz.study.datastructure.list;

import static zzz.study.datastructure.list.LinkedListOperation.*;

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
        ListNode h1 = reverse(construct(n1));
        ListNode h2 = reverse(construct(n2));
        ListNode sum = addTwoNum(h1, h2);
        desc(reverse(sum));
        System.out.println();


    }
}
