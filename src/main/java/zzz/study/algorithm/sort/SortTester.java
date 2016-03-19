package zzz.study.algorithm.sort;

import zzz.study.patterns.strategy.SortingStrategy;
import zzz.study.testdata.generator.ArrayGeneratorFactory;

import java.util.Arrays;


public class SortTester {

    public static void main(String[] args) {
        SortingStrategy ss = new QuickSort();

        System.out.println("************ 针对特定类型的快速排序 ************");
        testEmpty(ss);
        testSmall(ss);
        testTypical(ss);

        System.out.println("************ 针对通用类型的快速排序 ************");
        testStringEmpty();
        testStringSmall();
        testIGRSmall();
        testIGRTypical();

        ss = new InsertSort();
        System.out.println("************** 插入排序测试 ***************");
        testEmpty(ss);
        testSmall(ss);
        testTypical(ss);

    }

    private static void testSort(int[] arr, SortingStrategy ss) {
        ss.sort(arr);
        System.out.println("the array after being sorted: " + Arrays.toString(arr));
        ;
    }

    private static void testSort(Object[] arr, Cmp cmp) {
        QuickSort.sort(arr, cmp);
        System.out.println("the array after being sorted: " + Arrays.toString(arr));
        ;
    }

    private static void testEmpty(SortingStrategy ss) {
        testSort((int[]) null, ss);
        testSort(new int[]{}, ss);
        testSort(new int[0], ss);
    }

    private static void testStringEmpty() {
        testSort((String[]) null, new StringCmp());
        testSort(new String[]{}, new StringCmp());
        testSort(new String[0], new StringCmp());
    }

    private static void testStringSmall() {
        testSort(new String[]{""}, new StringCmp());
        testSort(new String[]{"", " "}, new StringCmp());
        testSort(new String[]{"a", "c", "b"}, new StringCmp());
        testSort(new String[]{"i ", "have", "a ", "dream."}, new StringCmp());
    }

    private static void testSmall(SortingStrategy ss) {
        testSort(new int[]{1}, ss);
        testSort(new int[]{1, 2}, ss);
        testSort(new int[]{2, 1}, ss);
        testSort(new int[]{2, 2}, ss);

        testSort(new int[]{1, 2, 3}, ss);
        testSort(new int[]{1, 3, 2}, ss);
        testSort(new int[]{2, 1, 3}, ss);
        testSort(new int[]{2, 3, 1}, ss);
        testSort(new int[]{3, 2, 1}, ss);
        testSort(new int[]{3, 1, 2}, ss);
        testSort(new int[]{1, 2, 2}, ss);
        testSort(new int[]{2, 1, 2}, ss);
        testSort(new int[]{2, 2, 1}, ss);
        testSort(new int[]{2, 2, 2}, ss);

        testSort(new int[]{3, 2, 4, 1}, ss);
        testSort(new int[]{1, 1, 2, 2}, ss);
        testSort(new int[]{1, 2, 2, 1}, ss);
        testSort(new int[]{1, 2, 1, 2}, ss);
        testSort(new int[]{2, 2, 1, 1}, ss);
        testSort(new int[]{2, 1, 2, 1}, ss);
        testSort(new int[]{2, 1, 1, 2}, ss);
    }

    private static void testIGRSmall() {
        testSort(new Integer[]{1}, new IntCmp());
        testSort(new Integer[]{1, 2}, new IntCmp());
        testSort(new Integer[]{2, 1}, new IntCmp());
        testSort(new Integer[]{2, 2}, new IntCmp());

        testSort(new Integer[]{1, 2, 3}, new IntCmp());
        testSort(new Integer[]{1, 3, 2}, new IntCmp());
        testSort(new Integer[]{2, 1, 3}, new IntCmp());
        testSort(new Integer[]{2, 3, 1}, new IntCmp());
        testSort(new Integer[]{3, 2, 1}, new IntCmp());
        testSort(new Integer[]{3, 1, 2}, new IntCmp());
        testSort(new Integer[]{1, 2, 2}, new IntCmp());
        testSort(new Integer[]{2, 1, 2}, new IntCmp());
        testSort(new Integer[]{2, 2, 1}, new IntCmp());
        testSort(new Integer[]{2, 2, 2}, new IntCmp());

        testSort(new Integer[]{3, 2, 4, 1}, new IntCmp());
        testSort(new Integer[]{1, 1, 2, 2}, new IntCmp());
        testSort(new Integer[]{1, 2, 2, 1}, new IntCmp());
        testSort(new Integer[]{1, 2, 1, 2}, new IntCmp());
        testSort(new Integer[]{2, 2, 1, 1}, new IntCmp());
        testSort(new Integer[]{2, 1, 2, 1}, new IntCmp());
        testSort(new Integer[]{2, 1, 1, 2}, new IntCmp());
    }

    public static void testTypical(SortingStrategy ss) {
        testSort(new ArrayGeneratorFactory().newProbablyDupArray().geneArray(20), ss);
    }

    public static void testIGRTypical() {
        int[] arr = new ArrayGeneratorFactory().newProbablyDupArray().geneArray(20);
        Integer[] igrArr = new Integer[20];
        for (int i = 0; i < 20; i++) {
            igrArr[i] = arr[i];
        }
        testSort(igrArr, new IntCmp());
    }

}
