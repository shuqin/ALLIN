package zzz.study.patterns.templateMethod;

import java.util.Comparator;

public class GradeComparator implements Comparator {
	
	public int compare(Object o1, Object o2) {
		
		Student s1 = (Student)o1;
		Student s2 = (Student)o2;
		
		return -Double.compare(s1.getGrade(), s2.getGrade());
	}

}
