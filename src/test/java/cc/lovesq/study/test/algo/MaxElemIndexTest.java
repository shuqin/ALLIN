package cc.lovesq.study.test.algo;

import junit.framework.TestCase;

import static zzz.study.algorithm.problems.MaxElemIndex.maxElemIndex;

public class MaxElemIndexTest extends TestCase {

    public void testMaxElemIndex() {
        assertEquals(5, maxElemIndex(new int[]{1, 2, 3, 4, 5, 6}));
        assertEquals(0, maxElemIndex(new int[]{6, 2, 1, 4, 5, 3}));
        assertEquals(2, maxElemIndex(new int[]{1, 2, 6, 4, 5, 3}));
        assertEquals(0, maxElemIndex(new int[]{6, 6, 3, 4, 5, 6}));
        assertEquals(2, maxElemIndex(new int[]{1, 2, 6, 6, 5, 6}));
    }

}
