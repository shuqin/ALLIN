package zzz.study.algorithm.sort;

import java.util.Arrays;

import zzz.study.datastructure.vector.NBitsVector;

/**
 * 位图排序
 *
 */
public class BitsMapSort {
	
	private NBitsVector nBitsVector; 
	
	public BitsMapSort(int n) {
		if (nBitsVector == null) {
			nBitsVector = new NBitsVector(n);
		}
	}
	
	public int[] sort(int[] arr) throws Exception {
		if (arr == null || arr.length == 0) {
			return null;
		}
		nBitsVector.clr();
		int arrLen = arr.length;
		for (int i=0; i < arrLen ; i++) {
			if (arr[i] < 0 || arr[i] > nBitsVector.getBitsLength()-1) {
				throw new Exception("给定整数 " + arr[i] + " 超过范围，请检查输入");
			}
			if (nBitsVector.testBit(arr[i])) {
				throw new Exception("存在重复整数: " + arr[i] + " ，请检查输入！");
			}
			nBitsVector.setBit(arr[i]);		
		}
		long bitsLength = nBitsVector.getBitsLength();
		int count = 0;
		for (int i=0; i < bitsLength; i++) {
			if (nBitsVector.testBit(i)) {			
				arr[count++] = i;
			}
		}
		return arr;
	}
	
	public static int maxOfArray(int[] arr)
	{
		int max = arr[0];
		for (int i=1; i < arr.length; i++) {
			if (arr[i] > max) {
				max = arr[i];
			}
		}
		return max;
	}
	
	public static void test(int[] arr) 
	{
		try {
			// 63 可以改为 数组最大值 maxOfArray(arr)
			BitsMapSort bms = new BitsMapSort(64);
			System.out.println("排序前： " + Arrays.toString(arr));
			int[] sorted = bms.sort(arr);
			System.out.println("排序后： " + Arrays.toString(sorted));
		}
		catch(Exception e) {
			System.out.println(e.getMessage());	
		}
	}
	
	public static void main(String[] args) 
	{
		int[] empty = null;
		test(empty);
		empty = new int[0];
		test(empty);
		
		int[] unsorted = new int[] { 15, 34, 46, 52, 7, 9, 5, 10, 25, 37, 48, 13};
		test(unsorted);
		int[] unsorted2 =  new int[] { 15, 34, 46, 52, 7, 9, 5, 7, 25, 37, 48, 13};
		test(unsorted2);
		int[] unsorted3 =  new int[] { 15, 34, 46, 52, 7, 9, 5, 72, 25, 37, 48, 13};
		test(unsorted3);
	}

}
