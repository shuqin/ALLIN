package zzz.study.patterns.flyweight;

public class Substance {

    private ChemicalInterface chemicalInterface;
    private double grams;

    public Substance(ChemicalInterface chemicalInterface, double grams) {
        this.chemicalInterface = chemicalInterface;
        this.grams = grams;
    }

    public double getGrams() {
        return grams;
    }

    public void setGrams(double grams) {
        this.grams = grams;
    }

    public String getName() {
        return chemicalInterface.getName();
    }

    public String getSymbol() {
        return chemicalInterface.getSymbol();
    }

    public double getAtomicWeight() {
        return chemicalInterface.getAtomicWeight();
    }

    public double getMoles() {
        return grams / getAtomicWeight();
    }

    public String toString() {
        return chemicalInterface.getName() + "\n" + "重量: " + grams + "\n";
    }

}
