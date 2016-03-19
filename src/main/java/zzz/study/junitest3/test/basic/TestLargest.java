package zzz.study.junitest3.test.basic;

import junit.framework.TestCase;
import junit.framework.TestSuite;
import junit.textui.TestRunner;
import zzz.study.junitest3.basic.Largest;

public class TestLargest extends TestCase {
	
	public TestLargest(String name) {
		super(name);
	}
	
	public void testSuite() {
		System.out.println("test suite.");
		try {
			TestSuite.getTestConstructor(getClass());
			System.out.println(TestRunner.EXCEPTION_EXIT);
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void testLargest() {
		
		System.out.println("test largest.");
		
		assertEquals(9, Largest.largest(new int[] {7,8,9}));
		assertEquals(9, Largest.largest(new int[] {9,8,7}));
		assertEquals(9, Largest.largest(new int[] {7,9,8}));
		assertEquals(9, Largest.largest(new int[] {7,9,8,9}));
		assertEquals(1, Largest.largest(new int[] {1}));	
		assertEquals(-7, Largest.largest(new int[] {-7,-8,-9}));
	}
	
	public void testEmpty() {
		System.out.println("test empty.");
		try {
			Largest.largest(new int[]{});
			fail("列表为空，抛出异常");
		} catch (RuntimeException e) {
			assertTrue(true);
		}
	}
	
	public void setUp() {
		System.out.println("Set up.");
	}
	
	public void tearDown() {
		System.out.println("stop.");
	}

}
