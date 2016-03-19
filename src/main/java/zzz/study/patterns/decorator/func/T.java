package zzz.study.patterns.decorator.func;

public class T extends Function {

    public T() {
        super(new Function[]{});
    }

    public double f(double t) {
        return t;
    }

    public String toString() {
        return "t";
    }

}
