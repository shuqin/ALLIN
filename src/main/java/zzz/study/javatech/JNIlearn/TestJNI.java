package zzz.study.javatech.JNIlearn;

public class TestJNI {
	
	public static void main(String[] args) {
		
		for (int i=1; i <= 5; i++) {
			
			System.out.println( i + "! = " + JNIMath.jiecheng(i));
		}
	}

}
