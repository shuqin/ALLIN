package cc.lovesq.study.test.algo;

import zzz.study.algorithm.select.RandomSelector;

import java.util.Arrays;

import static junit.framework.Assert.assertEquals;
import static zzz.study.algorithm.select.ElementSelection.getPosition;
import static zzz.study.algorithm.select.ElementSelection.kthMinSelect;

public class ElemSelectTester {

    public static void testElemSelect() {
        int[] arr = RandomSelector.selectMDisorderedRandInts3(10, 30);
        System.out.println("��ɵ����飺 " + Arrays.toString(arr));
        for (int i = 1; i <= arr.length; i++) {
            try {
                int kth = kthMinSelect(arr, i);
                System.out.println("�� " + i + " ��СԪ�أ� " + kth);
                assertEquals(getPosition(arr, kth), i);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

    }

    public static void testElemSelectRegion() {
        int[] arr = RandomSelector.selectMDisorderedRandInts3(10, 30);
        System.out.println("��ɵ����飺 " + Arrays.toString(arr));

        int left = -1, right = arr.length;
        int kth = arr.length / 2;
        while (true) {
            try {
                int[] region = null;
                try {
                    region = Arrays.copyOfRange(arr, left, right + 1);
                    System.out.println(Arrays.toString(region));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                int kthRegion = kthMinSelect(arr, left, right, kth);
                System.out.println("�� " + kth + " ��СԪ�أ� " + kthRegion);
                assertEquals(getPosition(region, kthRegion), kth);

            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            left++;
            right--;
            if (left > right) break;
        }
    }

    public static void main(String[] args) {
        testElemSelect();
        testElemSelectRegion();
    }


}
