package cc.lovesq.study.test.sort;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import zzz.study.junitest3.sort.ArraySortingUtil;

public class IntArraySortingTester extends TestCase {

    public IntArraySortingTester(String name) {
        super(name);
    }

    public static Test suite() {
        TestSuite suite = new TestSuite();
        suite.addTestSuite(IntArraySortingTester.class);
        return suite;
    }

    public void testIntArraySort() throws Exception {
        ArraySortingUtil sortingUtil = new ArraySortingUtil();
        int[] unsorted = new int[]{23, 12, 37, 56, 98, 87, 42};
        int[] expected = new int[]{12, 23, 37, 42, 56, 87, 98};
        int[] result = null;
        result = sortingUtil.sort(unsorted);
        assertEquals(expected.length, result.length);
        for (int i = 0; i < expected.length; i++) {
            assertEquals(expected[i], result[i]);
        }
    }

    public void testSideEffect() throws Exception {
        ArraySortingUtil sortingUtil = new ArraySortingUtil();
        int[] origin = new int[]{23, 12, 37, 56, 98, 87, 42};
        int[] unsorted = new int[]{23, 12, 37, 56, 98, 87, 42};
        sortingUtil.sort(unsorted);
        assertEquals(origin.length, unsorted.length);
        for (int i = 0; i < origin.length; i++) {
            assertEquals(origin[i], unsorted[i]);
        }

    }

    public void testRegionOfArraySortGivenStart() throws Exception {
        ArraySortingUtil sortingUtil = new ArraySortingUtil();
        int[] unsorted = new int[]{23, 12, 37, 56, 98, 87, 42};
        int[] expected = new int[]{23, 12, 37, 42, 56, 87, 98};
        int[] result = null;
        result = sortingUtil.sort(unsorted, 3);
        assertEquals(expected.length, result.length);
        for (int i = 0; i < expected.length; i++) {
            assertEquals(expected[i], result[i]);
        }
    }

    public void testRegionOfArraySortGivenTwoEnds() throws Exception {
        ArraySortingUtil sortingUtil = new ArraySortingUtil();
        int[] unsorted = new int[]{23, 12, 37, 56, 98, 87, 42};
        int[] expected = new int[]{23, 12, 37, 56, 87, 98, 42};
        int[] result = null;
        result = sortingUtil.sort(unsorted, 1, 6);
        assertEquals(expected.length, result.length);
        for (int i = 0; i < expected.length; i++) {
            assertEquals(expected[i], result[i]);
        }
    }

    public void testDupIntArraySort() throws Exception {
        ArraySortingUtil sortingUtil = new ArraySortingUtil();
        int[] unsorted = new int[]{23, 42, 37, 12, 98, 87, 12};
        int[] expected = new int[]{12, 12, 23, 37, 42, 87, 98};
        int[] result = null;
        result = sortingUtil.sort(unsorted);
        assertEquals(expected.length, result.length);
        for (int i = 0; i < expected.length; i++) {
            assertEquals(expected[i], result[i]);
        }
    }

    public void testEmptyIntArraySort() {
        ArraySortingUtil sortingUtil = new ArraySortingUtil();
        try {
            int[] unsorted = new int[]{};
            assertNotNull(unsorted);
            assertEquals(0, unsorted.length);
            int[] result = sortingUtil.sort(unsorted);
            fail("It should have thrown an exception");
        } catch (Exception e) {
            // reaching here  means having thrown an exception.
            assertTrue(e.getMessage().equals("Array Null or Empty"));
            assertTrue(true);
        }
    }

    public void testNullIntArraySort() {
        ArraySortingUtil sortingUtil = new ArraySortingUtil();
        try {
            int[] unsorted = null;
            assertNull(unsorted);
            int[] result = sortingUtil.sort(unsorted);
            fail("It should have thrown an exception");
        } catch (Exception e) {
            // reaching here  means having thrown an exception.
            assertTrue(e.getMessage().equals("Array Null or Empty"));
            assertTrue(true);
        }
    }

    public void testParamArraySortGivenStart() {
        ArraySortingUtil sortingUtil = new ArraySortingUtil();
        int[] unsorted = new int[]{23, 12, 37, 56, 98, 87, 42};
        try {
            sortingUtil.sort(unsorted, -1);
        } catch (Exception e) {
            assertTrue(e.getMessage().equals("Passed Parameters Error"));
            assertTrue(true);
        }
    }

    public void testParamArraySortGivenStart2() {
        ArraySortingUtil sortingUtil = new ArraySortingUtil();
        int[] unsorted = new int[]{23, 12, 37, 56, 98, 87, 42};
        try {
            sortingUtil.sort(unsorted, unsorted.length);
        } catch (Exception e) {
            assertTrue(e.getMessage().equals("Passed Parameters Error"));
            assertTrue(true);
        }
    }

    public void testParamRegionOfArraySortGivenTwoEnds() {
        ArraySortingUtil sortingUtil = new ArraySortingUtil();
        int[] unsorted = new int[]{23, 12, 37, 56, 98, 87, 42};
        try {
            sortingUtil.sort(unsorted, -1, 6);
        } catch (Exception e) {
            assertTrue(e.getMessage().equals("Passed Parameters Error"));
            assertTrue(true);
        }

    }

    public void testParamRegionOfArraySortGivenTwoEnds2() {
        ArraySortingUtil sortingUtil = new ArraySortingUtil();
        int[] unsorted = new int[]{23, 12, 37, 56, 98, 87, 42};
        try {
            sortingUtil.sort(unsorted, unsorted.length, 6);
        } catch (Exception e) {
            assertTrue(e.getMessage().equals("Passed Parameters Error"));
            assertTrue(true);
        }

    }

    public void testParamRegionOfArraySortGivenTwoEnds3() {
        ArraySortingUtil sortingUtil = new ArraySortingUtil();
        int[] unsorted = new int[]{23, 12, 37, 56, 98, 87, 42};
        try {
            sortingUtil.sort(unsorted, 1, unsorted.length + 1);
        } catch (Exception e) {
            assertTrue(e.getMessage().equals("Passed Parameters Error"));
            assertTrue(true);
        }

    }

    public void testParamRegionOfArraySortGivenTwoEnds4() {
        ArraySortingUtil sortingUtil = new ArraySortingUtil();
        int[] unsorted = new int[]{23, 12, 37, 56, 98, 87, 42};
        try {
            sortingUtil.sort(unsorted, 1, -1);
        } catch (Exception e) {
            assertTrue(e.getMessage().equals("Passed Parameters Error"));
            assertTrue(true);
        }

    }

    public void testParamRegionOfArraySortGivenTwoEnds5() {
        ArraySortingUtil sortingUtil = new ArraySortingUtil();
        int[] unsorted = new int[]{23, 12, 37, 56, 98, 87, 42};
        try {
            sortingUtil.sort(unsorted, 4, 2);
        } catch (Exception e) {
            assertTrue(e.getMessage().equals("Passed Parameters Error"));
            assertTrue(true);
        }

    }

    public void testParamRegionOfArraySortGivenTwoEnds6() {
        ArraySortingUtil sortingUtil = new ArraySortingUtil();
        int[] unsorted = new int[]{23, 12, 37, 56, 98, 87, 42};
        try {
            sortingUtil.sort(unsorted, 4, 4);
        } catch (Exception e) {
            assertTrue(e.getMessage().equals("Passed Parameters Error"));
            assertTrue(true);
        }

    }

    public void testParamRegionOfArraySortGivenTwoEnds7() {
        ArraySortingUtil sortingUtil = new ArraySortingUtil();
        int[] unsorted = new int[]{23, 12, 37, 56, 98, 87, 42};
        try {
            sortingUtil.sort(unsorted, 0, 0);
        } catch (Exception e) {
            assertTrue(e.getMessage().equals("Passed Parameters Error"));
            assertTrue(true);
        }

    }

    public void testParamRegionOfArraySortGivenTwoEnds8() {
        ArraySortingUtil sortingUtil = new ArraySortingUtil();
        int[] unsorted = new int[]{23, 12, 37, 56, 98, 87, 42};
        try {
            sortingUtil.sort(unsorted, unsorted.length - 1, unsorted.length - 1);
        } catch (Exception e) {
            assertTrue(e.getMessage().equals("Passed Parameters Error"));
            assertTrue(true);
        }

    }

    public void testBoundIntArraySort() throws Exception {
        ArraySortingUtil sortingUtil = new ArraySortingUtil();
        int[] unsorted = new int[]{Integer.MAX_VALUE, 12, 37, Integer.MIN_VALUE, 98, 87, 42};
        int[] expected = new int[]{Integer.MIN_VALUE, 12, 37, 42, 87, 98, Integer.MAX_VALUE};
        int[] result = null;
        result = sortingUtil.sort(unsorted);
        assertEquals(expected.length, result.length);
        for (int i = 0; i < expected.length; i++) {
            assertEquals(expected[i], result[i]);
        }
    }

    public void testBoundIntArraySort2() throws Exception {
        ArraySortingUtil sortingUtil = new ArraySortingUtil();
        int[] unsorted = new int[]{23, 12, 37, 56, 98, 87, 42};
        int[] expected = new int[]{12, 23, 37, 42, 56, 87, 98};
        int[] result = null;
        result = sortingUtil.sort(unsorted, 0);
        assertEquals(expected.length, result.length);
        for (int i = 0; i < expected.length; i++) {
            assertEquals(expected[i], result[i]);
        }
    }

    public void testBoundIntArraySort3() throws Exception {
        ArraySortingUtil sortingUtil = new ArraySortingUtil();
        int[] unsorted = new int[]{23, 12, 37, 56, 98, 87, 42};
        int[] expected = new int[]{23, 12, 37, 56, 98, 87, 42};
        int[] result = null;
        result = sortingUtil.sort(unsorted, unsorted.length - 1);
        assertEquals(expected.length, result.length);
        for (int i = 0; i < expected.length; i++) {
            assertEquals(expected[i], result[i]);
        }
    }

    public void testBoundIntArraySort4() throws Exception {
        ArraySortingUtil sortingUtil = new ArraySortingUtil();
        int[] unsorted = new int[]{23, 12, 37, 56, 98, 87, 42};
        int[] expected = new int[]{23, 12, 37, 56, 98, 87, 42};
        int[] result = null;
        result = sortingUtil.sort(unsorted, 0, 1);
        assertEquals(expected.length, result.length);
        for (int i = 0; i < expected.length; i++) {
            assertEquals(expected[i], result[i]);
        }
    }

    public void testBoundIntArraySort5() throws Exception {
        ArraySortingUtil sortingUtil = new ArraySortingUtil();
        int[] unsorted = new int[]{23, 12, 37, 56, 98, 87, 42};
        int[] expected = new int[]{23, 12, 37, 56, 98, 87, 42};
        int[] result = null;
        result = sortingUtil.sort(unsorted, unsorted.length - 1, unsorted.length);
        assertEquals(expected.length, result.length);
        for (int i = 0; i < expected.length; i++) {
            assertEquals(expected[i], result[i]);
        }
    }

    public void testBoundIntArraySort6() throws Exception {
        ArraySortingUtil sortingUtil = new ArraySortingUtil();
        int[] unsorted = new int[]{23, 12, 37, 56, 98, 87, 42};
        int[] expected = new int[]{12, 23, 37, 56, 98, 87, 42};
        int[] result = null;
        result = sortingUtil.sort(unsorted, 0, 2);
        assertEquals(expected.length, result.length);
        for (int i = 0; i < expected.length; i++) {
            assertEquals(expected[i], result[i]);
        }
    }

    public void testBoundIntArraySort7() throws Exception {
        ArraySortingUtil sortingUtil = new ArraySortingUtil();
        int[] unsorted = new int[]{23, 12, 37, 56, 98, 87, 42};
        int[] expected = new int[]{23, 12, 37, 56, 98, 42, 87};
        int[] result = null;
        result = sortingUtil.sort(unsorted, unsorted.length - 2, unsorted.length);
        assertEquals(expected.length, result.length);
        for (int i = 0; i < expected.length; i++) {
            assertEquals(expected[i], result[i]);
        }
    }

    public void testBoundIntArraySort8() throws Exception {
        ArraySortingUtil sortingUtil = new ArraySortingUtil();
        int[] unsorted = new int[]{23};
        int[] expected = new int[]{23};
        int[] result = null;
        result = sortingUtil.sort(unsorted);
        assertEquals(expected.length, result.length);
        for (int i = 0; i < expected.length; i++) {
            assertEquals(expected[i], result[i]);
        }
    }


}
