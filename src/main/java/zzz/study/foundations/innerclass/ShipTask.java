package zzz.study.foundations.innerclass;

import zzz.study.foundations.enums.Fruit;

import java.util.Iterator;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

public class ShipTask {

    // 创建内部类，就像创建任何一个普通的类一样。

    public static void main(String[] args) {
        ShipTask p = new ShipTask();
        p.shipTask();

        Destination dest = p.getDest("Los Angeles, USA");
        System.out.println("目标改变： " + dest.getWhereTo());

    }

    // 通常创建一些外部类方法来返回对内部类对象的引用
    public StartPlace getStartPlace(String whereFrom) {
        return new StartPlace(whereFrom);
    }

    public Destination getDest(String whereTo) {
        return new Destination(whereTo);
    }

    // 在外部类的非静态方法中，像使用普通类一样使用其内部类。
    public void shipTask() {
        Freight freights = new Freight();
        freights.addFreight(Fruit.BANANA, "500K");
        freights.addFreight(Fruit.APPLE, "300K");
        freights.addFreight(Fruit.PEAR, "100K");
        freights.addFreight(Fruit.GRAPE, "1000K");
        StartPlace startPlace = getStartPlace("Shanghai, China");
        Destination dest = getDest("Washington, USA");
        System.out.println("运输任务：");
        System.out.println(startPlace.getWhereFrom() + " ------ " + dest.getWhereTo());
        System.out.println(freights);
    }

    class StartPlace {

        private String startPlace;

        StartPlace(String whereFrom) {
            startPlace = whereFrom;
        }

        String getWhereFrom() {
            return startPlace;
        }

    }

    class Destination {

        private String dest;

        Destination(String whereTo) {
            dest = whereTo;
        }

        String getWhereTo() {
            return dest;
        }
    }

    class Freight {

        private SortedMap<Fruit, String> freights;

        Freight() {
            if (freights == null) {
                freights = new TreeMap<Fruit, String>();
            }
        }

        void addFreight(Fruit fruit, String desc) {
            freights.put(fruit, desc);
        }

        public String toString() {
            String s = "货物 --------- ";
            Set keyset = freights.keySet();
            Iterator iter = keyset.iterator();
            while (iter.hasNext()) {
                Fruit f = (Fruit) iter.next();
                String desc = freights.get(f);
                s += "\n" + f.name() + " " + desc;
            }
            return s;
        }
    }
}
