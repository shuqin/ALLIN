package zzz.study.junitest3.sort;

public class InsertSortStrategy implements SortingStrategy {
	
	public void sort(double[] list) {
		insertSort(list, list.length);
	}
	
	public <T> void sort(T[] list) {
		insertSort(list, list.length);
	}
	
	/*
	 * sort: 对数组指定区域 [begin: end-1] 进行排序，包括起始下标，不包括终止下标。
	 * @param unsorted 未排序的数组
	 * @param begin 指定的起始下标
	 * @param end   指定的终止下标
	 * @return      返回排序后的数组
	 */
	public int[] sort(int[] unsorted, int begin, int end) {
		int[] subarray = new int[end-begin];
		for (int i=begin; i<end; i++) {
			subarray[i-begin] = unsorted[i];
		}
		insertSort(subarray, subarray.length);
		int[] result = new int[unsorted.length];
		for (int i=0; i<unsorted.length;i++) {
			result[i] = unsorted[i];
		}
		for (int i=begin; i<end; i++) {
			result[i] = subarray[i-begin];
		}
		return result;
	}
	
	/* 
	 * 将list[0:n-1]按非降排序
	 */
	
	private void insertSort(int[] list, int n)
	{  
		  for (int i=1; i<n ; i++) {
		  	int temp = list[i];
		  	insert(temp, list, i); 
		  }  	  
	}
	
	/* 
	 * 将元素e插入到有序表list[0:i-1]中
	 * 使得list[0:i]仍保持有序，list大小至少有(i+1);
	 */
	
	private void insert(int e, int[] list, int i)
	{  
	    while (e < list[i-1] && i>0) {
	  	   list[i] = list[i-1];
	  	   i--;
	  	   if (i==0) break;
	    }
	    list[i] = e;  
	}
	
	
	/* 
	 * 将list[0:n-1]按非降排序
	 */
	
	private void insertSort(double list[], int n)
	{  
		  for (int i=1; i<n ; i++) {
		  	double temp = list[i];
		  	insert(temp, list, i); 
		  }  	  
	}
	
	/* 
	 * 将元素e插入到有序表list[0:i-1]中
	 * 使得list[0:i]仍保持有序，list大小至少有(i+1);
	 */
	
	private void insert(double e, double[] list, int i)
	{  
	    while (e < list[i-1] && i>0) {
	  	   list[i] = list[i-1];
	  	   i--;
	  	   if (i==0) break;
	    }
	    list[i] = e;  
	}
	
	/* 
	 * 将list[0:n-1]按非降排序
	 */
	private <T> void insertSort(T[] list, int n)
	{  
		  for (int i=1; i<n ; i++) {
		  	T temp = list[i];
		  	insert(temp, list, i); 
		  }  	  
	}
	
	/* 
	 * 将元素e插入到有序表list[0:i-1]中
	 * 使得list[0:i]仍保持有序，list大小至少有(i+1);
	 */
	private <T> void insert(T e, T[] list, int i)
	{  
	    while (((Comparable)e).compareTo(list[i-1]) < 0 && i>0) {
	  	   list[i] = list[i-1];
	  	   i--;
	  	   if (i==0) break;
	    }
	    list[i] = e;  
	}	

}
