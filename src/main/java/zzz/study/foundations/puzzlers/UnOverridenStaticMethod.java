package zzz.study.foundations.puzzlers;

public class UnOverridenStaticMethod {
    public static void main(String args[]) {
        Dogg woofer = new Dogg();
        Dogg nipper = new Basenji();
        Basenji basenji = new Basenji();
        System.out.println("------ ");
        System.out.printf("woofer.bark(): ");
        woofer.bark();
        System.out.println(" \n------ ");
        System.out.printf("nipper.bark(): ");
        nipper.bark();
        System.out.println(" \n------ ");
        System.out.printf("basenji.bark(): ");
        basenji.bark();
        System.out.println(" \n------ ");
        System.out.printf("Dogg.bark(): ");
        Dogg.bark();
        System.out.println(" \n------ ");
        System.out.printf("Basenji.bark(): ");
        Basenji.bark();
        
    }
}

class Dogg {
    public static void bark() {
        System.out.print("woof ");
    }
}

class Basenji extends Dogg {
    public static void bark() { }
}

/*
 * Note:
 * 
 * 对静态方法不存在多态机制，不能被覆写，只能被隐藏。
 * 静态方法的调用是在编译时刻被选定的，
 * 取决于调用时在方法调用.前面的对象名的类型声明。
 * 
 * 对 nipper.bark()调用，由于编译器声明为Dogg类型，故调用 Dogg 的   bark 方法；
 * 对basenji.bark()调用，由于编译器声明为Basenji类型，故调用Basenji的 bark 方法。
 * 
 * 用类名去调用静态方法。
 * 
 * 
 */
