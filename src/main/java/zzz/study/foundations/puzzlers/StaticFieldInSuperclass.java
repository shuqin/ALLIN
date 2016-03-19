package zzz.study.foundations.puzzlers;

class Counter {
    private static int count = 0;
    public static final synchronized void increment() {
        count++;
    }
    public static final synchronized int getCount() {
        return count; 
    } 
}

class Dog extends Counter {
    public Dog() { }
    public void woof() { increment(); }
} 

class Cat extends Counter {
    public Cat() { } 
    public void meow() { increment(); }
}

public class StaticFieldInSuperclass {
    public static void main(String[] args) { 
        Dog dogs[] = { new Dog(), new Dog() };
        for (int i = 0; i < dogs.length; i++)
            dogs[i].woof();
        Cat cats[] = { new Cat(), new Cat(), new Cat() };
        for (int i = 0; i < cats.length; i++)
            cats[i].meow();
        System.out.print(Dog.getCount() + " woofs and ");
        System.out.println(Cat.getCount() + " meows");
    }
}

/*
 * Note:
 * 
 * 每一个静态域在声明它的类及其所有子类中共享一份单一的拷贝。
 * 避免在超类中声明静态域。
 *
 * 当一个类的行为构建于另一个类的行为的基础上时，优先考虑组合而不是继承。
 * 要使用继承，必须精确掌握继承的语义，才不会被尚不知道的继承语义所困扰。
 *
 * 这也说明了，使用所熟悉的方式。
 * 
 */
