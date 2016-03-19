package zzz.study.junitest3.test.basic;

import junit.framework.TestCase;

public class TestString extends TestCase {

	public TestString(String name) {
		super(name);
	}

	public void testEquals() {
		
		String s = "abc";
		assertNotNull("对象为空！", s);
		assertEquals("字符串不相等！", "abc", s);
		assertSame("两个引用指向不同的对象", "abc", s);
		
		String t = "abc";
		assertEquals("字符串不相等！", s, t);
		assertSame("两个引用指向不同的对象！", s, t);
		
		String t2 = new String("abc");
		assertEquals("字符串不相等！", s, t2);
		assertNotSame("两个引用指向不同的对象！", s, t2);
		
		String t3 = s;
		assertEquals("字符串不相等！", s, t3);
		assertSame("两个引用指向不同的对象！", s, t3);
		
		String t4 = "abc";
		assertTrue("字符串不相等！", s.equals(t4));
		assertTrue("两个引用指向不同的对象！", s == t4);
		
		String t5 = new String("abc");
		assertTrue("字符串不相等！", s.equals(t5));
		assertFalse("两个引用指向不同的对象！", s == t5);
       
	}
}
