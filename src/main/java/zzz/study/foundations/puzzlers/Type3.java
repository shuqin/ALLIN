package zzz.study.foundations.puzzlers;

public class Type3 {
    public static void main(String args[]) {
        Type3 t2 = (Type3) new Object();
        // 可以通过编译，运行时抛出 ClassCastException 异常。
    }
}
