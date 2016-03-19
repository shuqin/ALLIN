package zzz.study.patterns.composite;

import java.util.ArrayList;
import java.util.List;

public class Client {

    public static void main(String[] args) {

        Element single = new Single("P.");
        List elements = new ArrayList();
        elements.add(new Single("0001"));
        elements.add(new Single("0002"));
        elements.add(single);
        ElementGroup eg = new ElementGroup(elements, "G1000");
        ElementGroup bigger = new ElementGroup("G2000");
        bigger.addElement(eg);
        bigger.addElement(single);
        Element md = bigger;
        System.out.println("Machine count: " + md.getCount());
    }
}
