package zzz.study.algorithm.select;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;

public class RandomSelector {
	
	private RandomSelector() {
		
	}
	
	private static Random rand = new Random(47);
	
	/**
	 * bigRandInt: 返回一个非常大的随机整数，该整数的二进制位数不小于 bits
	 */
	public static int bigRandInt(int bits)
	{ 
		 if (bits >= 32 || bits <= 0) {
			 throw new IllegalArgumentException("参数 " + bits + " 错误，必须为小于 32 的正整数！");
		 }
		 int baseNum = 1 << (bits - 1);
		 return rand.nextInt(Integer.MAX_VALUE - baseNum) + baseNum;
	}
	
	/**
	 * randRange: 生成给定范围的随机整数
	 * @param low  范围下限
	 * @param high 范围上限(不包含)
	 * @return 给定范围的随机整数
	 */
	public static int randRange(int low, int high)
	{
		if (high <= low) {
			throw new IllegalArgumentException("参数 [" + low + "," + high + "] 错误，第一个参数必须小于第二个参数！");
		}
		return bigRandInt(30) % (high-low) + low;	
	}
	
	/**
	 * selectMOrderedRandInts : 从指定集合中随机选择指定数目的整数，并以有序输出
	 * @param m 需要选取的整数数目
	 * @param n 指定整数集合 [0:n-1]
	 * @return 随机选取的有序整数列表
	 */
	public static int[] selectMOrderedRandInts(int m, int n)
	{
		checkParams(m, n);
		int[] result = new int[m];
		int remaining = n;
		int selector = m;	
		for (int k=0, i=0; i < n; i++) {
			if ((bigRandInt(30) % remaining) < selector) {
				result[k++] = i;
				selector--;
			}
			remaining--;	
		}
		return result;	
	}
	
	/**
	 * selectMOrderedRandInts2 : 从指定集合中随机选择指定数目的整数，并以有序输出
	 * @param m 需要选取的整数数目
	 * @param n 指定整数集合 [0:n-1]
	 * @return 随机选取的有序整数列表
	 */
	public static int[] selectMOrderedRandInts2(int m, int n)
	{
		checkParams(m, n);
		Set<Integer> holder = new TreeSet<Integer>();
		while (holder.size() < m) {
			holder.add(bigRandInt(30) % n);
		}
		return collectionToArray(holder);
	}
	
	/**
	 * selectMOrderedRandInts3 : 从指定集合中随机选择指定数目的整数，并以有序输出
	 * @param m 需要选取的整数数目
	 * @param n 指定整数集合 [0:n-1]
	 * @return 随机选取的有序整数列表
	 */
	public static int[] selectMOrderedRandInts3(int m, int n)
	{
		checkParams(m, n);
		int[] arr = selectMDisorderedRandInts3(m, n);
		Arrays.sort(arr);
		return arr;
	}
	
	/**
	 * selectMDisorderedRandInts2: 从指定整数集合中随机选择指定数目的整数，并以无序输出
	 * @param m 需要选取的整数数目
	 * @param n 指定整数集合 [0:n-1]
	 * @return 随机选取的无序整数列表
	 */
	public static int[] selectMDisorderedRandInts2(int m, int n)
	{
		checkParams(m, n);
		Set<Integer> intSet = new HashSet<Integer>();
		while (intSet.size() < m) {
			intSet.add(bigRandInt(30) % n);
		}
		return collectionToArray(intSet);
	}
	
	/**
	 * selectMDisorderedRandInts3: 从指定整数集合中随机选择指定数目的整数，并以无序输出
	 * @param m 需要选取的整数数目
	 * @param n 指定整数集合 [0:n-1]
	 * @return 随机选取的无序整数列表
	 */
	public static int[] selectMDisorderedRandInts3(int m, int n)
	{
		checkParams(m, n);
		int[] arr = new int[n];
		for (int i=0; i < n; i++) {
			arr[i] = i;
		}
		for (int k=0; k < m; k++) {
			int j = randRange(k, n);
			int tmp = arr[k];
			arr[k] = arr[j];
			arr[j] = tmp;
		}
		return Arrays.copyOf(arr, m);
	}
	
	public static void checkParams(int m, int n)
	{
		if (m > n || m <= 0 || n <= 0 ) {
			throw new IllegalArgumentException("参数 [" + m + "," + n + "] 错误，必须均为正整数，且第一个参数必须小于或等于第二个参数！");
		}
	}
	
	/**
	 * collectionToArray : 将指定整数集合转化为整型数组列表
	 * @param collection 指定整数集合
	 * @return 要返回的整型数组列表，若给定集合为空，则返回 null
	 */
	public static int[] collectionToArray(Collection<Integer> collection)
	{
		if (collection == null || collection.size() == 0) {
			return null;
		}
		int[] result = new int[collection.size()];
		int k = 0;
		for (Integer integer : collection) {
			result[k] = integer;
			k++;
		}
		return result;
	}
	
	/**
	 * printArray: 打印数组的便利方法，每打印十个数换行 
	 * @param arr 指定要打印的数组
	 */
	public static void printArray(int[] arr)
	{
		for (int i=0; i < arr.length; i++) {
			System.out.printf("%d%c", arr[i], i%10==9 ? '\n' : ' ');
		}
	}
	
}
