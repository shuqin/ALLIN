package zzz.study.algorithm.sort;

import zzz.study.testdata.generator.ArrayGenerator;
import zzz.study.testdata.generator.ArrayGeneratorFactory;

import java.util.Arrays;


public class InsertSortPerformance {

    public static void main(String[] args) {
        System.out.println("*********** 测试插入排序的性能及 递归与非递归实现的性能比较 *************");
        System.out.println("*********** 含重复元素比较多的数组的插入排序性能 ******************");
        cmpperformance(new ArrayGeneratorFactory().newProbablyDupArray());
        System.out.println("*********** 不含重复元素的数组的插入排序性能 ******************");
        cmpperformance(new ArrayGeneratorFactory().newNoDupArray());
    }

    public static void cmpperformance(ArrayGenerator ag) {
        InsertSort is = new InsertSort();
        for (int size = 10; size <= 5000; size += 100) {
            int[] intArr = ag.geneArray(size);
            int[] intArr2 = new int[size];
            for (int i = 0; i < intArr.length; i++) {
                intArr2[i] = intArr[i];
            }
            Arrays.equals(intArr, intArr2);   // 排序之前保证待排序是完全相同的

            long start = System.nanoTime();
            is.sort(intArr);
            long end = System.nanoTime();
            long intArrRuntime = end - start;

            long start2 = System.nanoTime();
            is.recSort(intArr2);
            long end2 = System.nanoTime();
            long igrArrRuntime = end2 - start2;

            Arrays.equals(intArr, intArr2);  // 排序之后检查递归与非递归排序的结果是完全相同的 

            System.out.printf("size = %-10d:\n", size);
            System.out.printf("非递归插入排序时间： %7.2f (ms), ", (double) intArrRuntime / 1000000);
            System.out.printf("递归插入排序的时间:  %7.2f (ms)\n", (double) igrArrRuntime / 1000000);
            System.out.printf("前者相比后者，时间降低了： %6.2f%%\n", ((double) (igrArrRuntime - intArrRuntime) / igrArrRuntime) * 100);
        }
    }

}
