package zzz.study.foundations.generics;

import zzz.study.foundations.inheritance.Base;
import zzz.study.foundations.sharingclasses.Dog;

/**
 * Holder:
 * 优点： 可持有任何类型的对象；
 * 不足： 在取出持有对象之时要进行类型转换
 */
public class Holder {

    private Object obj;

    public Holder(Object obj) {
        this.obj = obj;
    }

    public static void main(String[] args) {

        Holder h = new Holder(new Dog("GouGou"));
        System.out.println(h.get());

        h.set("Changed to a string.");
        System.out.println(h.get());

        h.set(3.14);
        System.out.println("now is: " + h.get() + " " + h.get().getClass());

        h.set(new Base());
        System.out.println(h.get());

        h.set(new Base(7, "Derived"));
        System.out.println(h.get());
    }

    public void set(Object obj) {
        this.obj = obj;
    }

    public Object get() {
        return this.obj;
    }

}

