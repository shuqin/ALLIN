package zzz.study.algorithm.problems;

public class MaxElemIndex {
	
	private MaxElemIndex() {
		
	}
	
	private static int maxElemIndex(int[] arr, int left, int right)
	{
		if (right > left) {
			int maxIndex1 = maxElemIndex(arr, left, left + (right-left)/2);
			int maxIndex2 = maxElemIndex(arr, left + (right-left)/2 + 1, right);
			if (arr[maxIndex1] >= arr[maxIndex2])
				return maxIndex1;
			else 
				return maxIndex2;
		}
		return left;
	}
	
	public static int maxElemIndex(int[] arr) 
	{
		return maxElemIndex(arr, 0, arr.length-1);
	}

}
