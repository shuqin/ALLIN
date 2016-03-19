package zzz.study.algorithm.sort;

public class StringCmp implements Cmp {
	
	public int cmp(Object o1, Object o2)
	{
		String s1 = (String) o1;
		String s2 = (String) o2;
		return s1.compareTo(s2);
	}

}
