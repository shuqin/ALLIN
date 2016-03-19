package zzz.study.algorithm.search;

import java.util.Arrays;

import junit.framework.TestCase;

import static zzz.study.algorithm.search.BinarySearch.*;

public class BinarySearchTest extends TestCase {
	
	public void testNonRecBinarySearch()
	{
		int[] arr = new int[] {3, 14, 27, 31, 39, 42};
		assertEquals(0, search(arr, 3));
		assertEquals(1, search(arr, 14));
		assertEquals(2, search(arr, 27));
		assertEquals(3, search(arr, 31));
		assertEquals(4, search(arr, 39));
		assertEquals(5, search(arr, 42));
		assertEquals(-1, search(arr, 2));
		assertEquals(-1, search(arr, 30));
		assertEquals(-1, search(arr, 43));
	}
	
	public void testRecBinarySearch()
	{
		int[] arr = new int[] {3, 14, 27, 31, 39, 42};
		assertEquals(0, recSearch(arr, 3));
		assertEquals(1, recSearch(arr, 14));
		assertEquals(2, recSearch(arr, 27));
		assertEquals(3, recSearch(arr, 31));
		assertEquals(4, recSearch(arr, 39));
		assertEquals(5, recSearch(arr, 42));
		assertEquals(-1, recSearch(arr, 2));
		assertEquals(-1, recSearch(arr, 30));
		assertEquals(-1, recSearch(arr, 43));
	}
	
	public void testBinarySearchForInsert()
	{
		int[] arr = new int[] {3, 14, 27, 31, 39, 42};
		assertEquals(0, indexOfInserting(arr, 3));
		assertEquals(1, indexOfInserting(arr, 14));
		assertEquals(2, indexOfInserting(arr, 27));
		assertEquals(3, indexOfInserting(arr, 31));
		assertEquals(4, indexOfInserting(arr, 39));
		assertEquals(5, indexOfInserting(arr, 42));
		assertEquals(0, indexOfInserting(arr, 2));
		assertEquals(1, indexOfInserting(arr, 12));
		assertEquals(2, indexOfInserting(arr, 16));
		assertEquals(3, indexOfInserting(arr, 30));
		assertEquals(4, indexOfInserting(arr, 32));
		assertEquals(5, indexOfInserting(arr, 40));
		assertEquals(6, indexOfInserting(arr, 43));
	}
	
	public void testRangeSearch()
	{
		int[] arr = new int[] {3, 14, 27, 31, 39, 42};
		assertTrue(Arrays.equals(new int[] {0,5}, rangeSearch(arr, 3, 42)));
		assertTrue(Arrays.equals(new int[] {0,1}, rangeSearch(arr, 3, 14)));
		assertTrue(Arrays.equals(new int[] {0,3}, rangeSearch(arr, 3, 31)));
		assertTrue(Arrays.equals(new int[] {2,5}, rangeSearch(arr, 27, 42)));
		assertTrue(Arrays.equals(new int[] {4,5}, rangeSearch(arr, 39, 42)));
		
		assertTrue(Arrays.equals(new int[] {0,5}, rangeSearch(arr, 2, 43)));
		assertTrue(Arrays.equals(new int[] {0,0}, rangeSearch(arr, 2, 3)));
		assertTrue(Arrays.equals(new int[] {5,5}, rangeSearch(arr, 42, 43)));
		assertTrue(Arrays.equals(new int[] {0,2}, rangeSearch(arr, 1, 30)));
		assertTrue(Arrays.equals(new int[] {0,3}, rangeSearch(arr, 1, 31)));
		assertTrue(Arrays.equals(new int[] {3,5}, rangeSearch(arr, 31, 50)));
		assertTrue(Arrays.equals(new int[] {4,5}, rangeSearch(arr, 32, 50)));
		assertTrue(Arrays.equals(new int[] {2,3}, rangeSearch(arr, 15, 38)));
		assertTrue(Arrays.equals(new int[] {}, rangeSearch(arr, 0, 2)));
		assertTrue(Arrays.equals(new int[] {}, rangeSearch(arr, 45, 48)));
	}

}
