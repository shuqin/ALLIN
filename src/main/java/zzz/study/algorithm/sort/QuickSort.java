package zzz.study.algorithm.sort;

import zzz.study.algorithm.dividing.Partition;
import zzz.study.patterns.strategy.SortingStrategy;

public class QuickSort implements SortingStrategy {

    public static void sort(Object[] arr, Cmp cmp) {
        if (arr == null || arr.length == 0) {
            System.out.println("the array is empty.");
            return;
        }
        sort(arr, 0, arr.length - 1, cmp);
    }

    private static void sort(Object[] arr, int begin, int end, Cmp cmp) {
        if (begin <= end) {
            int partition = Partition.partition(arr, begin, end, cmp);
            sort(arr, begin, partition - 1, cmp);
            sort(arr, partition + 1, end, cmp);
        }
    }

    public void sort(int[] arr) {
        if (arr == null || arr.length == 0) {
            System.out.println("the array is empty.");
            return;
        }
        sort(arr, 0, arr.length - 1);
    }

    private void sort(int[] arr, int begin, int end) {
        if (begin <= end) {
            int partition = Partition.partition(arr, begin, end);
            sort(arr, begin, partition - 1);
            sort(arr, partition + 1, end);
        }
    }


}
