package zzz.study.foundations.basic;

public class StringEquality {
	
	public static void main(String[] args) {
		
		// s1 and s2 point to the same string object.
		String s1 = "good";
		String s2 = "good";
		System.out.println("s1 == s2 ? " + (s1 == s2 ? "yes" : "no"));
		System.out.println("s1.equals(s2) ? " + (s1.equals(s2) ? "yes" : "no"));
		
		// s3 and s4 point to different string objects.
		String s3 = "good";
		String s4 = new String("good");
		System.out.println("s3 == s4 ? " + (s3 == s4 ? "yes" : "no"));
		System.out.println("s3.equals(s4) ? " + (s3.equals(s4) ? "yes" : "no"));

	}

}
