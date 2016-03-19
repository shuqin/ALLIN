package zzz.study.algorithm.rand;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import zzz.study.utils.MapUtil;

public class RandomTester {
	
	private static Random random = new Random(47);
	
	public static void main(String[] args)
	{
		new RandomTester().testRandom();
	}
	
	
	public int[] generate(int size, int maxNum)
	{
		int[] result = new int[size];
		for (int i=0; i < result.length; i++) {
			result[i] = Math.abs(random.nextInt()) % maxNum; 
		}
		return result;
	}
	
	public  void testRandom()
	{
		int[] result = generate(1000, 1000);
		Map<Integer, Integer> map = new HashMap<Integer, Integer>();
		for (int i = 0; i < result.length; i++) {
			Integer count = map.get(result[i]);
			if (count == null) {
			    count = Integer.valueOf(1);
			}
			else {
	            int cnt = count.intValue();
				count = Integer.valueOf(++cnt);
			}
			map.put(result[i], count);
		}
		
		MapUtil.printMap(map);
	}

}
