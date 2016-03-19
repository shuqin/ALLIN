package zzz.study.foundations.array;

public class ArrayLengthLimition {
	
	public static void main(String[] args)
	{
		//! int[] arr = new int[Integer.MAX_VALUE]; // 超过JVM可分配的最大数组长度限制 Requested array size exceeds VM limit
		//! int[] arr1 = new int[1000000000]; // 超过可分配的堆空间 java.lang.OutOfMemoryError: Java heap space
		 int[] arr2 = new int[100000000]; // 修改 VM 参数 -Xmx 1GB, 可以容纳  2^28 个整型数
	}

}
