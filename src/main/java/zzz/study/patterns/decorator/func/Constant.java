package zzz.study.patterns.decorator.func;

public class Constant extends Function {

    private double constant;

    public Constant() {
        super(new Function[]{});
    }

    public Constant(double constant) {
        super(new Function[]{});
        this.constant = constant;
    }

    public double f(double t) {
        return constant;
    }

    public String toString() {
        return Double.toString(constant);
    }

}
