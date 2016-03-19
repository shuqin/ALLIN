package zzz.study.junitest3.test.sort;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class WrappedLongArraySortingTester extends TestCase {
	
	public WrappedLongArraySortingTester(String name) {
		super(name);
	}
	
	public static Test suite()
	{
		TestSuite suite = new TestSuite();
		suite.addTestSuite(WrappedLongArraySortingTester.class);
		return suite;
	}

}
