package zzz.study.foundations.container;

import zzz.study.foundations.sharingclasses.Apple;
import zzz.study.foundations.sharingclasses.EvilCode;
import zzz.study.foundations.sharingclasses.GreenApple;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


public class GenericContainer {

    public static void main(String[] args) {
        System.out.println("****************** 类型不安全的容器 *******************");
        List fruits = new ArrayList();
        try {
            fruits.add(new Apple());
            fruits.add(new Apple());
            fruits.add(new EvilCode()); //！ 任意对象都可加入到容器，使容器不安全
            fruits.add(new Apple());
            for (int i = 0; i < fruits.size(); i++) {
                Apple apple = (Apple) fruits.get(i); // 当 Orange 或 Exception 转换为 Apple 时产生运行时异常
                System.out.println(apple);
            }
        } catch (Exception e) {
            System.out.println("插入了非法对象:\t" + e.getMessage());
        } finally {
            System.out.println("没有指定插入对象类型的容器容易插入非法对象，造成不安全的隐患。");
        }

        System.out.println("****************** 类型安全的容器 *******************");
        List<Apple> fruits2 = new ArrayList<Apple>();
        fruits2.add(new Apple());
        fruits2.add(new Apple());
        fruits2.add(new GreenApple()); // 可以将 Apple 的子类型的对象插入到 Apple 容器中
        //! 非 Apple 对象无法插入到该容器中，在编译期提供类型检查 fruits2.add(new Orange()); 
        //! 非 Apple 对象无法插入到该容器中，在编译期提供类型检查 fruits2.add(new Exception());
        fruits2.add(new Apple());
        for (int i = 0; i < fruits2.size(); i++) {
            Apple apple = fruits2.get(i); // 不再需要类型转换
            System.out.println(apple);
        }

        System.out.println("****************** 采用容器的多种实现方式 *******************");
        List<Apple> fruits3 = new LinkedList<Apple>(); // 使用接口编程，可以改用多种实现
        fruits3.addAll(fruits2);
        for (Apple apple : fruits3) {  // 使用 Foreach 语句
            System.out.println(apple);
        }
    }


}
