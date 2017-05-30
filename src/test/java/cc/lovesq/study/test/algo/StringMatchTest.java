package cc.lovesq.study.test.algo;

import junit.framework.TestCase;

import static zzz.study.algorithm.problems.StringMatcher.simpleMatch;

public class StringMatchTest extends TestCase {
	
	public void testSimple()
	{
		assertEquals(0, simpleMatch("employ", "emp"));
		assertEquals(-1, simpleMatch("employ", "emp "));
		assertEquals(-1, simpleMatch("employ", " emp"));
		assertEquals(0, simpleMatch("employ", "employ"));
		assertEquals(-1, simpleMatch("employ", " employ"));
		assertEquals(-1, simpleMatch("employ", "employ "));
		assertEquals(2, simpleMatch("employ", "plo"));
		assertEquals(0, simpleMatch(" ", ""));
		assertEquals(0, simpleMatch(" ", " "));
		assertEquals(-1, simpleMatch(" ", "  "));
	}

}
