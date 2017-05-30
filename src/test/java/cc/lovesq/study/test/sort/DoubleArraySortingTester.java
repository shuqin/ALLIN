package cc.lovesq.study.test.sort;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import zzz.study.junitest3.sort.ArraySortingUtil;

public class DoubleArraySortingTester extends TestCase {

	public DoubleArraySortingTester(String name) {
		super(name);
	}
	
	public void testDoubleArraySort()
	{
		ArraySortingUtil sortingUtil = new ArraySortingUtil();
		double[] unsorted = new double[] {23.4, 12.5, 37.7, 56.6, 98.8, 23.3, 23.1};
		double[] expected = new double[] {12.5, 23.1, 23.3, 23.4, 37.7, 56.6, 98.8};
		double[] result = sortingUtil.sort(unsorted);
		assertEquals(expected.length, result.length);
		for (int i=0; i<expected.length;i++) {
			assertEquals(expected[i], result[i]);
		}
	}
	
	public static Test suite()
	{
		TestSuite suite = new TestSuite();
		suite.addTestSuite(DoubleArraySortingTester.class);
		return suite;
	}
	
}
