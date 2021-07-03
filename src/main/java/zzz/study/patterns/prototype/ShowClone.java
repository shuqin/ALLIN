package zzz.study.patterns.prototype;

import zzz.study.patterns.adapter.byobject.Rocket;

public class ShowClone {

    public static void main(String[] args) {

        // 克隆对象的第一种方法： 实例化一个新对象，并将已存在对象的相应字段赋值给新对象的相应字段
        Rocket r = new Rocket("长城七号", 120.0, 50);
        Rocket r1 = r.copy(r);
        System.out.println(r == r1);
        System.out.println(r.equals(r1));
        System.out.println(r.getClass() == r1.getClass());

        r1.setApogee(75);
        System.out.println(r1);

        System.out.println(" ---------------- ");

        // 克隆对象的第二种方法： 实现 Cloneable接口，调用或覆写clone()方法，  使该类具备可克隆特性。
        CloneableRocket rocket = new CloneableRocket("挑战者号", 120.0, 103.4);
        CloneableRocket clonedRocket = null;
        try {
            clonedRocket = (CloneableRocket) rocket.clone();
        } catch (CloneNotSupportedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


        System.out.println("克隆de新对象： ");
        System.out.println(clonedRocket);
        System.out.println(clonedRocket == rocket);
        System.out.println(clonedRocket.equals(rocket));
        System.out.println(clonedRocket.getClass() == rocket.getClass());

        System.out.println("rocket: " + rocket.hashCode());
        System.out.println("clonedRocket: " + clonedRocket.hashCode());

    }

}
