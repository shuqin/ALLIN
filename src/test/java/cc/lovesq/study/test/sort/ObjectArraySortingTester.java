package cc.lovesq.study.test.sort;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import zzz.study.junitest3.sort.ArraySortingUtil;
import zzz.study.junitest3.sort.Point;

public class ObjectArraySortingTester extends TestCase {

    public ObjectArraySortingTester(String name) {
        super(name);
    }

    public static Test suite() {
        TestSuite suite = new TestSuite();
        suite.addTestSuite(ObjectArraySortingTester.class);
        return suite;
    }

    public void testPointArraySort() {
        ArraySortingUtil sortingUtil = new ArraySortingUtil();
        Point[] unsorted = new Point[]{new Point(2, 4), new Point(1, 3), new Point(0, 12), new Point(7, 0)};
        Point[] expected = new Point[]{new Point(1, 3), new Point(2, 4), new Point(7, 0), new Point(0, 12)};
        Point[] result = sortingUtil.sort(unsorted);
        assertEquals(expected.length, result.length);
        for (int i = 0; i < expected.length; i++) {
            assertNotNull(expected[i]);
            assertNotNull(result[i]);
            assertEquals(expected[i], result[i]);

        }
    }

}
