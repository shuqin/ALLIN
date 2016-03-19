package zzz.study.patterns.composite;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ElementGroup implements Element {

    private String groupId;

    // subelements 既可能包含 Single, 也可能包含 ElementGroup
    private List subelements = new ArrayList<Element>();

    public ElementGroup(String groupId) {
        if (subelements == null) {
            subelements = new ArrayList<Element>();
        }
        this.groupId = groupId;
    }

    public ElementGroup(List elements, String groupId) {
        subelements = elements;
        this.groupId = groupId;
    }

    public void addElement(Element e) {
        subelements.add(e);
    }

    public void addElement(ElementGroup elements) {
        subelements.add(elements);
    }

    public int getCount() {

        int count = 0;
        Iterator iter = subelements.iterator();
        while (iter.hasNext()) {
            Element e = (Element) iter.next();
            System.out.println("所含元素： ");
            System.out.println(e.getClass().getSimpleName());
            System.out.println(" id: " + e.getElementId());
            count += e.getCount();
        }
        return count;
    }

    public String getElementId() {
        return this.groupId;
    }

}
