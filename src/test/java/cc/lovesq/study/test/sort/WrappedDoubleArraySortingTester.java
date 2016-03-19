package cc.lovesq.study.test.sort;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import zzz.study.junitest3.sort.ArraySortingUtil;

public class WrappedDoubleArraySortingTester extends TestCase {

    public WrappedDoubleArraySortingTester(String name) {
        super(name);
    }

    public static Test suite() {
        TestSuite suite = new TestSuite();
        suite.addTestSuite(WrappedDoubleArraySortingTester.class);
        return suite;
    }

    public void testWrappedDoubleArraySort() {
        ArraySortingUtil sortingUtil = new ArraySortingUtil();
        Double[] unsorted = new Double[]{23.4, 12.5, 37.7, 56.6, 98.8, 23.3, 23.1};
        Double[] expected = new Double[]{12.5, 23.1, 23.3, 23.4, 37.7, 56.6, 98.8};
        Double[] result = sortingUtil.sort(unsorted);
        assertEquals(expected.length, result.length);
        for (int i = 0; i < expected.length; i++) {
            assertEquals(expected[i], result[i]);
        }
    }

}
