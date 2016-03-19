package zzz.study.patterns.abstractfactory;

public class AbstractFactoryTester {

    public static void check(AbstractFactory af) {

        IPad cc = af.createIPad();

        System.out.println(cc.price());
        System.out.println(cc.policy());

        IMac bc = af.createIMac();

        System.out.println(bc.price());
        System.out.println(bc.policy());
    }

    public static void main(String[] args) {

        System.out.println("*** In US Market: ***");
        check(USFactory.getUSFactory());
        System.out.println("*** In CN Market: ***");
        check(CNFactory.getCNFactory());

    }

}
