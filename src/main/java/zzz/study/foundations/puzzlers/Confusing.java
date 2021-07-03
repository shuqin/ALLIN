package zzz.study.foundations.puzzlers;

public class Confusing {
    private Confusing(Object o) {
        System.out.println("Object");
    }

    private Confusing(double[] dArray) {
        System.out.println("double array");
    }

    public static void main(String[] args) {
        new Confusing(null);
        new Confusing((double[]) null);
        new Confusing((Object) null);
    }
}

/*
 * Note: 关于函数重载
 *
 * Java的重载解析过程：
 * ① 选取所有可获得并且可应用的方法或构造器；
 * ② 选取在①中最精确地匹配传入参数的那个；
 *   如果一个方法或构造器能够接受传递给另一个方法或构造器的任何参数，则这个方法就缺乏精确性。
 *   即，如果方法能够接受“更宽”的参数类型，则方法就缺乏相对精确性。
 *
 * 要想强制要求编译器选取一个精确的重载版本，需要将实参转型为形参所声明的类型。
 *
 * 避免使用重载；为不同的方法取不同的名称。
 *
 * 使用重载时，确保所有重载的方法或构造器的参数类型都互不兼容。
 *
 *
 */
