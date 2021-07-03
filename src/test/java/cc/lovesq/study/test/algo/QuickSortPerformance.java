package cc.lovesq.study.test.algo;

import zzz.study.algorithm.sort.IntCmp;
import zzz.study.algorithm.sort.QuickSort;
import zzz.study.patterns.strategy.SortingStrategy;
import zzz.study.testdata.generator.ArrayGenerator;
import zzz.study.testdata.generator.ArrayGeneratorFactory;

import static junit.framework.Assert.assertEquals;

public class QuickSortPerformance {

    private QuickSortPerformance() {

    }

    public static void main(String[] args) {
        /* 1. 该快速排序实现在含有很多重复元素的数组的情况下表现出最差性能，因此随着规模的增长，运行时间可能不太有规律 */
        /* 2. 该快速排序实现在不含有重复元素的数组的情况下表现出平均性能，因此随着规模的增长，运行时间符合理论上规律 O(nlogn) */
        System.out.println("*************** 可能含有很多重复元素的数组的快速排序 性能比较 ****************** ");
        cmpperformance(new ArrayGeneratorFactory().newProbablyDupArray());
        System.out.println("*************** 不含有重复元素的数组的快速排序 性能比较 ****************** ");
        cmpperformance(new ArrayGeneratorFactory().newNoDupArray());
    }

    public static void cmpperformance(ArrayGenerator ag) {
        SortingStrategy ss = new QuickSort();
        for (int size = 10; size <= 10000000; size *= 10) {
            int[] intArr = ag.geneArray(size);
            Integer[] igrArr = new Integer[size];
            for (int i = 0; i < size; i++) {
                igrArr[i] = intArr[i];
            }
            isEqualArray(intArr, igrArr);

            long start = System.nanoTime();
            ss.sort(intArr);
            long end = System.nanoTime();
            long intArrRuntime = end - start;

            long start2 = System.nanoTime();
            QuickSort.sort(igrArr, new IntCmp());
            long end2 = System.nanoTime();
            long igrArrRuntime = end2 - start2;

            System.out.printf("size = %-10d:\n", size);
            System.out.printf("排序 int 类型数组时间： %7.2f (ms), ", (double) intArrRuntime / 1000000);
            System.out.printf("排序 Integer 类型数组的时间: %7.2f (ms)\n", (double) igrArrRuntime / 1000000);
            System.out.printf("前者相比后者，时间降低了： %6.2f%%\n", ((double) (igrArrRuntime - intArrRuntime) / igrArrRuntime) * 100);
        }
    }


    public static void isEqualArray(int[] arr, Integer[] igr) {
        assertEquals("数组长度不等!", arr.length, igr.length);
        for (int i = 0; i < arr.length; i++) {
            assertEquals("数组元素不等!", arr[i], igr[i].intValue());
        }
    }


}
