package zzz.study.foundations.inheritance;

/**
 * the constructor details of inheritance
 * 1. automatically call the most possible constructor of the base class
 * before calling the constructor of the derived class.
 * 2. the derived class can't access the private member of the base class
 * while it can directly access the protected member of the base class.
 */
public class ConstructorBasicsOfInheritance {

    public static void main(String[] args) {
        Derived d1 = new Derived();
        System.out.println(d1);
        Derived d2 = new Derived("myown");
        System.out.println(d2);
        Derived d3 = new Derived(7, "derived", "myown");
        System.out.println(d3);
    }

}
