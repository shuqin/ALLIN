package zzz.study.foundations.inheritance;

public class Derived extends Base {

    private String myown;

    public Derived() {
        //! 基类构造器 Base() 被隐式自动调用，因为必须初始化基类成员
        System.out.println("Derived Constructor with no arguments.0");
    }

    public Derived(String myown) {
        //! 基类构造器 Base() 被隐式自动调用，因为必须初始化基类成员
        System.out.println("Derived Constructor with arguments. 1");
        //! 无法直接访问基类私有成员 i = 7;
        // 可以直接访问基类的保护成员；
        name = "derive";
        this.myown = myown;
    }

    public Derived(int i, String name, String myown) {
        super(i, name);   // 显式调用基类构造器，必须在其它语句之前
        System.out.println("Derived Constructor with arguments. 2");
        this.myown = myown;
    }

    public void say() {
        super.say();    // 在子类中调用父类的方法
        System.out.println(" --- this is derived class! --- ");
    }

    public String toString() {
        return super.toString() + " " + myown;
    }

}
