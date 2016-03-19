package zzz.study.foundations.generics;

public class Tuple<A, B> {

    public final A a;
    public final B b;

    public Tuple(A a, B b) {
        this.a = a;
        this.b = b;
    }

    public String toString() {
        return "[" + a + " " + b + "]";
    }

    public A getA() {
        return this.a;
    }

    public B getB() {
        return this.b;
    }

}
