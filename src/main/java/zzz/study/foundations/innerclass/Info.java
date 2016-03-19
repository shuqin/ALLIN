package zzz.study.foundations.innerclass;

import zzz.study.foundations.enums.Fruit;

public class Info {

    public static void main(String[] args) {

        ShipTask p1 = new ShipTask();

        // 某个类的内部类声明为默认访问权限时，该内部类的对象对外部其它类的对象是可见的。
        ShipTask.StartPlace sp = p1.getStartPlace("Beijing, China");
        ShipTask.Destination dest = p1.getDest("Berlin, German");

        System.out.println("Information obtained: ");
        System.out.println(sp.getWhereFrom() + " ---- " + dest.getWhereTo());


        ShipTask2 p2 = new ShipTask2();
        ShipTask2.GetOuterThis ot = p2.new GetOuterThis();
        System.out.println(ot);

        // 某个类的内部类【eg. ShipTask2.StartPlace】声明为私有时，
        // 该内部类对象对外部其它类【Info】对象是不可见的。
        // 这样，Info对象无法使用ShipTask2的私有内部类的对象。
        // ! ShipTask2.StartPlace sp2 = p2.getStartPlace("Beijing, China");
        // ! ShipTask2.Destination dest2 = p2.getDest("Berlin, German");

        // 通过实现了接口的私有内部类， 非其外部类对象的Info对象可以间接使用完全不可见的内部类所提供的服务。
        ShipTask3 p3 = new ShipTask3();
        IStartOut start = p3.getStartPlace("Shenzhen, China");
        IDestination destination = p3.getDest("Secret");
        IFreight freights = p3.getFreight();
        freights.addFreight(Fruit.BANANA, "500K");
        freights.addFreight(Fruit.PEAR, "100K");
        freights.addFreight(Fruit.GRAPE, "1000K");
        freights.addFreight(Fruit.APPLE, "300K");
        freights.addFreight(Fruit.GRAPE, "700K");
        System.out.println(start.getWhereFrom() + " ---- " + destination.getWhereTo());
        System.out.println(freights);

    }

}
