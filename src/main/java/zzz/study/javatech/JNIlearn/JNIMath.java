package zzz.study.javatech.JNIlearn;

public class JNIMath {

    static {
        System.loadLibrary("JNIMath");
    }

    public native static int jiecheng(int n);

}
