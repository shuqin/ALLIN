package zzz.study.patterns.flyweight;


public class ShowMixture {

    public static void main(String[] args) {

        Mixture blackPowder = new Mixture();
        Substance subs1 = new Substance(ChemicalFactory.getChemical("cabon"), 15);
        Substance subs2 = new Substance(ChemicalFactory.getChemical("sulfur"), 10);
        Substance subs3 = new Substance(ChemicalFactory.getChemical("saltpeter"), 75);
        blackPowder.addSubstance(subs1);
        blackPowder.addSubstance(subs2);
        blackPowder.addSubstance(subs3);
        System.out.println("The mixture of blackPower: ");
        System.out.println(blackPowder);
    }

}
