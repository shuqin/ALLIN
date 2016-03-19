package zzz.study.algorithm.select;

import zzz.study.algorithm.dividing.Partition;

public class ElementSelection {
	
	private ElementSelection() {
		
	}
	
	/**
	 * kthMinSelect: 在给定数组 arr[left: right] 中选出第 k 小的元素， 即有：
	 * ( k-1 个 arr 元素 ) <= P <= (n-k 个 arr 元素).
	 * @param arr 给定数组
	 * @param left 给定数组的起始下标
	 * @param right 给定数组的终止下标(包含)
	 * @return 第 k 小的元素 
	 */
	public static int kthMinSelect(int[] arr, int left, int right, int k) 
	{	
		if (k <= 0)
			throw new IllegalArgumentException("参数错误！指定第 " + k + " 小元素， " + k + " 必须为正整数!");
		if (left < 0 || right >= arr.length || left > right) {
			throw new IllegalArgumentException("参数错误！指定数组范围 [" + left + "," + right + "] 不合法！");
		}
		if (right-left+1 < k)
			throw new IllegalArgumentException("参数错误！指定 第 " + k + " 小元素， 但数组只有 " + (right-left+1) + " 个元素.");
		int partition = Partition.partition(arr, left, right);
		int s = partition - left + 1;  // 处于分区位置的元素是第 (partition-left+1) 小元素
		if (s == k) {
			return arr[partition];
		} else if (s > k) {
			return kthMinSelect(arr, left, partition-1, k);
		} else {
			return kthMinSelect(arr, partition+1, right, k-s);
		}
		
	}
	
	/**
	 * kthMinSelect: 在给定数组 arr 中找到第 k 小元素， 返回其下标
	 * @param arr 给定数组、
	 * @param k  第 k 小元素
	 */
	public static int kthMinSelect(int[] arr, int k)  {
		return kthMinSelect(arr, 0, arr.length-1, k);
	}
	

	/**
	 * 求解 key 在 arr 中从小到大顺序排列的位置
	 */
	public static int getPosition(int[] arr, int key)
	{
		int count = 1;
		for (int i : arr) {
			if (i < key) {
				count++;
			}
		}
		return count;
	}

}
