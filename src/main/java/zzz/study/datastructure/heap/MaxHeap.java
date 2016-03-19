package zzz.study.datastructure.heap;

public class MaxHeap {
	
	private int[] heap;   // 0号单元不用
	private int num;      // 堆中已有的元素数目
	
	public MaxHeap(int size) {
		heap = new int[size+1];
		num = 0;
	}
	
	/**
	 * size : 返回堆可以容纳的最大元素数目
	 */
	public int size()
	{
		return heap.length-1;
	}
	
	/**
	 * insert : 将给定元素插入到堆中
	 * @param data 给定元素
	 * @throws Exception 若堆满，则抛出异常
	 */
	public void insert(int data) throws Exception {
		if (num == heap.length-1) {
			throw new Exception("堆满，无法继续插入！");
		}
		num++;
		int index = num;
		while ( index != 1) {
			if (data > heap[index/2]) {
				heap[index] = heap[index/2];
				index /= 2;
			}
			else {
				break;
			}
		}
		heap[index] = data;
	}
	
	/**
	 * delete: 从堆中删除最大元素并返回
	 * @throws Exception 若堆空，则抛出异常.
	 */
	public int delete() throws Exception
	{
		if (num == 0) {
			throw new Exception("堆空，无法再删除元素!");
		}
		
		int max = heap[1];
		int index = 1;
		int tmp = heap[num];
		while (index < num/2) {	
			if (tmp >= heap[2*index] && tmp >= heap[2*index+1]) {
				break;
			}
			else {
				if (heap[2*index] >= heap[2*index+1]) {
					heap[index] = heap[2*index];
					index = 2 * index;	
				}
				else {
					heap[index] = heap[2*index+1];
					index = 2 * index + 1;			
				}
			}
		}
		if (2*index+1 == num && heap[2*index] > heap[index]) {
			heap[index] = heap[2*index];
			heap[2*index] = tmp;
		}
		heap[index] = tmp;
		num--;
		return max;
	}
	
	/**
	 * heapsort: 对给定数组进行堆排序
	 * @param array 给定的无序数组
	 * @return 有序数组
	 * @throws Exception 若堆排序失败，则抛出异常.
	 */
	public int[] heapsort(int[] array) throws Exception
	{
		for (int data: array) {
			insert(data);
		}
		int[] result = new int[array.length];
		for (int i=0; i < result.length; i++) {
			result[i] = delete();
		}
		return result;
	}
	
	public String toString()
	{
		StringBuilder sb = new StringBuilder(" [ ");
		
		for (int i=1; i <= num; i++) {
			sb.append(heap[i]);
			sb.append(' ');
		}
		sb.append(']');
		return sb.toString();
	}

}
