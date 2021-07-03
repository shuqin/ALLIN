package zzz.study.patterns.flyweight;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class Mixture {

    private List<Substance> substances;

    public Mixture() {
        if (substances == null) {
            substances = new ArrayList<Substance>();
        }
    }

    public Mixture(List substances) {
        this.substances = substances;
    }

    public List getSubstances() {
        return substances;
    }

    public void setSubstances(List substances) {
        this.substances = substances;
    }

    public void addSubstance(Substance substance) {
        substances.add(substance);
    }

    public String toString() {

        String s = "";

        Iterator it = substances.iterator();
        while (it.hasNext()) {
            Substance substance = (Substance) it.next();
            s += substance.getName() + ": ";
            s += substance.getGrams() + "\n";
        }
        return s;
    }

}
