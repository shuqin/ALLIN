package zzz.study.junitest3.test.sort;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import zzz.study.junitest3.sort.ArraySortingUtil;

public class IntegerArraySortingTester extends TestCase {
	
	public IntegerArraySortingTester(String name) {
		super(name);
	}
	
	public void testWrappedIntegerArraySort()
	{
		ArraySortingUtil sortingUtil = new ArraySortingUtil();
		Integer[] unsorted = new Integer[] {23, 12, 37, 56, 98, 87, 42};
		Integer[] expected = new Integer[] {12, 23, 37, 42, 56, 87, 98};
		Integer[] result = sortingUtil.sort(unsorted);
		assertEquals(expected.length, result.length);
		for (int i=0; i<expected.length;i++) {
			assertEquals(expected[i], result[i]);
		}
	}
	
	public void testWrappedSideEffect()
	{
		ArraySortingUtil sortingUtil = new ArraySortingUtil();
		Integer[] origin = new Integer[] {23, 12, 37, 56, 98, 87, 42};
		Integer[] unsorted = new Integer[] {23, 12, 37, 56, 98, 87, 42};
		sortingUtil.sort(unsorted);
		assertEquals(origin.length, unsorted.length);
		for (int i=0; i<origin.length;i++) {
			assertEquals(origin[i], unsorted[i]);
		}
		
	}
	
	public static Test suite()
	{
		TestSuite suite = new TestSuite();
		suite.addTestSuite(IntegerArraySortingTester.class);
		return suite;
	}

}
