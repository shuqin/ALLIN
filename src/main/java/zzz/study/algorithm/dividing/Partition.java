package zzz.study.algorithm.dividing;

import java.util.Random;

import zzz.study.algorithm.sort.Cmp;

/**
 * Partition: 实现分区技术，在一个范围内选定一个元素，并将其放在某个位置上， 
 * 使该位置之前的元素都不大于该分区元素，该位置之后的元素都不小于该分区元素
 *
 */
public class Partition {
	
	private static Random random = new Random(47);
	
	private Partition() { }
	
	/**
	 * partition: 在 arr[left:right]中选定一个元素P，放在位置 pIndex 上，使：
	 * [left, pIndex-1] 范围的元素都小于或等于P， [pIndex+1,right] 范围的元素都大于或等于P，
	 * 返回位置 pIndex.
	 * @param arr 给定数组
	 * @param left 数组的给定范围下限
	 * @param right 数组的给定范围上限
	 * @return 分区元素的位置，使该位置之前的元素都不大于该分区元素，该位置之后的元素都不小于该分区元素
	 */
	public static int partition(int[] arr, int left, int right)
	{
		// 随机选择某个元素作为分区元素
		int selectIndex = Math.abs(random.nextInt()) % (right-left+1) + left;
		int last = left;
		swap(arr, left, selectIndex);
		for (int i = left+1; i <= right; i++) {
			if (arr[i] < arr[left]) {
				swap(arr, ++last, i);
			}
		}
		swap(arr, left, last);
		return last;
	}
	
	public static int partition(Object[] arr, int left, int right, Cmp cmp)
	{
		// 随机选择某个元素作为分区元素
		int selectIndex = Math.abs(random.nextInt()) % (right-left+1) + left;
		int last = left;
		swap(arr, left, selectIndex);
		for (int i = left+1; i <= right; i++) {
			if (cmp.cmp(arr[i], arr[left]) < 0) {
				swap(arr, ++last, i);
			}
		}
		swap(arr, left, last);
		return last;
	}
	
	/*
	 * swap: 交换数组元素 arr[i] 和 arr[j] 
	 */
	private static void swap(int[] arr, int i, int j)
	{
		int temp = arr[i];
		arr[i] = arr[j];
		arr[j] = temp;
	}
	
	private static void swap(Object[] arr, int i, int j)
	{
		Object temp = arr[i];
		arr[i] = arr[j];
		arr[j] = temp;
	}
	
}
