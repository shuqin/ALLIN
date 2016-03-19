package zzz.study.testdata.generator;

import java.util.Random;
import zzz.study.algorithm.select.RandomSelector;

public class ArrayGeneratorFactory {

	private static Random random = new Random(47);
	
	public class ProbablyDupArray implements ArrayGenerator {
		
		public int[] geneArray(int size)
		{
			int[] arr = new int[size];
			for (int i = 0; i < size; i++) {
				arr[i] = random.nextInt(size);
			}
			return arr;
		}
	}
	
    public class NoDupArray implements ArrayGenerator {
		
		public int[] geneArray(int size)
		{
			return RandomSelector.selectMDisorderedRandInts2(size, Integer.MAX_VALUE);
		}
	}
	
	public ProbablyDupArray newProbablyDupArray() {
		return new ProbablyDupArray();
	}
    
    public NoDupArray newNoDupArray() {
    	return new NoDupArray();
    }
}
