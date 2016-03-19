package zzz.study.patterns.strategy;

import zzz.study.algorithm.sort.InsertSort;

public class StrategyForSimple implements SortingStrategy {
	
	private InsertSort insertSort = new InsertSort();
	
	public StrategyForSimple() 
	{
		System.out.println("Using Simple Strategy:");
	}
	
	public void sort(int[] list)
	{
		insertSort.sort(list);
	}

}
