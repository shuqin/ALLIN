package zzz.study.foundations.container.demo;

import zzz.study.foundations.sharingclasses.Apple;
import zzz.study.foundations.sharingclasses.GreenApple;

import java.util.*;


public class CollectionDemo {

    public static void main(String[] args) {

        testCollection(new ArrayList<Apple>());
        testCollection(new LinkedList<Apple>());
        testCollection(new HashSet<Apple>());
        testCollection(new LinkedHashSet<Apple>());
        //！ testCollection(new TreeSet()); 容器元素必须实现 Comparable 接口
    }


    public static void addCollection(Collection<Apple> c) {
        Collections.addAll(c, new Apple(), new Apple(), new GreenApple(), new Apple());
    }


    public static void printCollection(Collection<Apple> c) {
        System.out.println("*****************************************");
        System.out.println("使用容器： " + c.getClass().getSimpleName());
        System.out.println("容器中的元素： " + c);
        System.out.println("容器中元素数目： " + c.size());
    }

    public static void testCollection(Collection<Apple> c) {
        addCollection(c);
        printCollection(c);
    }

}
