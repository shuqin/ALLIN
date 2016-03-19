package zzz.study.algorithm.search;

public class BinarySearch {	
	
	/**
	 * 二分搜索的非递归版本： 在给定有序数组中查找给定的键值
	 * 前提条件： 数组必须有序， 即满足： A[0] <= A[1] <= ... <= A[n-1]
	 * @param arr 给定有序数组
	 * @param key 给定键值
	 * @return 如果查找成功，则返回键值在数组中的下标位置，否则，返回 -1.
	 */
	public static int search(int[] arr, int key) {
		
		int low = 0;
		int high = arr.length-1;
		while (low <= high) {
			int mid = (low + high) / 2;
			if (arr[mid] > key) {
				high = mid - 1;
			}
			else if (arr[mid] == key) {
				return mid;
			}
			else {
				low = mid + 1;
			}
		}
		return -1;
	}
	
	/**
	 * 二分搜索的递归版本： 在给定有序数组中查找给定的键值
	 * 前提条件： 数组必须有序， 即满足： A[0] <= A[1] <= ... <= A[n-1]
	 * @param arr 给定有序数组
	 * @param key 给定键值
	 * @return 如果查找成功，则返回键值在数组中的下标位置，否则，返回 -1.
	 */
	public static int recSearch(int[] arr, int key) {
		return recSearch(arr, 0, arr.length-1, key);
	}
	
	/**
	 * 在给定有序数组的给定范围内查找给定的键值
	 * 前提条件： 数组必须有序， 即满足： A[0] <= A[1] <= ... <= A[n-1]
	 * @param arr 给定有序数组
	 * @param left 给定范围的起始点
	 * @param right 给定范围的终止点（包括）
	 * @param key 给定键值
	 * @return 如果查找成功，则返回键值在数组中的下标位置，否则，返回 -1.
	 */
	private static int recSearch(int[] arr, int left, int right, int key) {
		
		if (left <= right) {
			int mid = (left + right) / 2;
			if (arr[mid] > key) {
				return recSearch(arr, left, mid-1, key);
			} else if (arr[mid] == key) {
				return mid;
			} else {
				return recSearch(arr, mid+1, right, key);
			}
		}
		return -1;	
	}
	
	/**
	 * 二分搜索的非递归版本： 在给定有序数组 arr 中查找给定的键值
	 * 前提条件： 数组必须有序， 即满足： A[0] <= A[1] <= ... <= A[n-1]
	 * @param arr 给定有序数组
	 * @param key 给定键值
	 * @return 如果查找成功，则返回键值在数组中的下标位置;
	 *         如果查找失败，则返回键值在数组中应该插入的下标位置 insertIndex。
	 *         
	 * NOTE:        
	 * 二分搜索不成功时: 
	 * 1. Key < arr[0] 时 , insertIndex = 0; 2. Key > arr[n-1] 时 , insertIndex = n; 
	 * 3. arr[0] < Key < arr[n-1] 时， arr[insertIndex-1] < Key < arr[insertIndex]   
	 * 二分搜索成功时： arr[InsertIndex] = Key.     
	 */
	public static int indexOfInserting(int[] arr, int key) {
		
		return indexOfInserting(arr, 0, arr.length-1, key);
	}
	
	/**
	 * 二分搜索的非递归版本： 在给定有序数组区域 arr[left:right] 中查找给定的键值
	 * 前提条件： 数组必须有序， 即满足： A[left] <= A[left+1] <= ... <= A[right]
	 * @param arr 给定有序数组
	 * @param key 给定键值
	 * @return 如果查找成功，则返回键值在数组中的下标位置;
	 *         如果查找失败，则返回键值在数组中应该插入的下标位置 insertIndex。
	 */         
	public static int indexOfInserting(int[] arr, int left, int right, int key) {
		
		int low = left;
		int high = right;
		while (low <= high) {
			int mid = (low + high) / 2;
			if (arr[mid] > key) {
				high = mid - 1;
			}
			else if (arr[mid] == key) {
				return mid;
			}
			else {
				low = mid + 1;
			}
		}
		return low;
	}
	
	/**
	 * 范围查找： 对于给定有序数组 arr， 找出位于给定键值 lKey, uKey 之间的所有元素
	 * 前提条件： 数组必须有序， 即满足： A[0] <= A[1] <= ... <= A[n-1] ; 且 lkey <= uKey; 
	 * @param arr 给定有序数组
	 * @param lKey 给定下限键值
	 * @param uKey 给定上限键值
	 * @return 所查找范围的下标范围（包括端点）
	 * 
	 */
	public static int[] rangeSearch(int[] arr, int lKey, int uKey)
	{	
		int downLimit = indexOfInserting(arr, lKey);
		if (downLimit == arr.length) { // lKey > arr[n-1]	
			return new int[] { };
		} 
		if (arr[downLimit] != lKey) {
			
			if (arr[downLimit] < lKey) {
				downLimit++;
			}
		}
		int upLimit = indexOfInserting(arr, uKey);
		if (upLimit == arr.length) { // uKey > arr[n-1]
			upLimit = arr.length-1;
		}
		if (arr[upLimit] != uKey) {
			if (upLimit == 0) {
				// uKey < arr[0]
				return new int[] { };
			}
			if (arr[upLimit] > uKey) {
				upLimit--;
			}
		}
		return new int[] { downLimit, upLimit };
	}
	

}
