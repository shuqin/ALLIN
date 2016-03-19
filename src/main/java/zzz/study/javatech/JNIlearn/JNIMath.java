package zzz.study.javatech.JNIlearn;

public class JNIMath {
	
	public native static int jiecheng(int n);
	
	static {
		System.loadLibrary("JNIMath");
	}

}
