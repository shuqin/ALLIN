package zzz.study.junitest3.basic;

public class Largest {
	
	/**
	 * Return the largest element in a list.
	 * 
	 * @param list  a list of integers
	 * @return The largest number in the given list
	 */
	
	public static int largest(int[] list) {
		int index, max = Integer.MIN_VALUE;
		if (list.length == 0) {
			throw new RuntimeException("列表为空");
		}
		for (index = 0; index < list.length; index++) {
			if (list[index] > max) {
				max = list[index];
			}
		}
		return max;
	}

}
