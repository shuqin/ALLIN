package zzz.study.foundations.basic;

public class InheritanceTrap {

    public static void main(String[] args) {
        Child c = new Child(2, 3);
        System.out.println(c);
    }

}

class Child extends Parent {

    private int i;
    private int j;

    public Child(int i, int j) {
        super(i);
        this.j = j;
    }

    public String toString() {
        return "i = " + i + ", j = " + j;
    }
}

class Parent {

    private int i;

    public Parent(int i) {
        this.i = i;
    }

    public String toString() {
        return "i = " + i;
    }
}

/*
 * 注意到, child.toString() 只能访问到 child 的  i , 而在child 的构造器中只对父类的 i 进行了初始化
 */
