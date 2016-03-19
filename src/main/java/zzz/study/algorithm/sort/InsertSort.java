package zzz.study.algorithm.sort;

import zzz.study.patterns.strategy.SortingStrategy;
import zzz.study.algorithm.search.BinarySearch;

public class InsertSort implements SortingStrategy {
	
	public InsertSort() {
		
	}
	
	/**
	 * 非递归的插入排序
	 */
	public void sort(int[] arr)
	{
		if (arr == null || arr.length == 0) {
        	System.out.println("the array is empty.");
        	return ;
        }
		for (int i=0; i < arr.length-1; i++) {
			int key = arr[i+1];
			int insertIndex = BinarySearch.indexOfInserting(arr, 0, i, key);
			insert(arr, insertIndex, i, key);
		}
	}
	
//	/**
//	 *  此函数用于在 SortTester 中测试 recSort 的正确性
//	 */
//	public void sort(int[] arr)
//	{
//		recSort(arr);  
//	}
	
	/**
	 * 递归的插入排序
	 */
	public void recSort(int[] arr)
	{
		if (arr == null || arr.length == 0) {
        	System.out.println("the array is empty.");
        	return ;
        }
		recSort(arr, arr.length-1);
	}
	
	/*
	 * 对 arr[0: right] 进行递归插入排序:
	 * 先排序好 arr[0: right-1] ，然后将 arr[right] 插入到其中正确的位置上
	 */
	private void recSort(int[] arr, int right)
	{
		if (right > 0) {
		   recSort(arr, right-1);
		   int insertIndex = BinarySearch.indexOfInserting(arr, 0, right-1, arr[right]);
		   insert(arr, insertIndex, right-1, arr[right]);
		}
	}
	
	/*
	 * insert: 将元素 key 插入到 arr[0: right+1] 的位置 insertIndex 上，其中
	 */
	private void insert(int[] arr, int insertIndex, int right, int key)
	{
		for (int k = right; k >= insertIndex; k--) {
			arr[k+1] = arr[k];
		}
		arr[insertIndex] = key;
	}

}
