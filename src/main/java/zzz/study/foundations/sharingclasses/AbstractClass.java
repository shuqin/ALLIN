package zzz.study.foundations.sharingclasses;

public class AbstractClass {

    private static int counter;
    private final int id = counter++;

    public String toString() {
        return getClass().getSimpleName() + "[" + id + "]";
    }

}
