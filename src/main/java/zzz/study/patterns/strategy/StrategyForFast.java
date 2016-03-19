package zzz.study.patterns.strategy;

import zzz.study.algorithm.sort.QuickSort;

public class StrategyForFast implements SortingStrategy {

    private QuickSort qsort = new QuickSort();

    public StrategyForFast() {
        System.out.println("Using Fast Strategy:");
    }

    public void sort(int[] list) {
        qsort.sort(list);
    }


}
