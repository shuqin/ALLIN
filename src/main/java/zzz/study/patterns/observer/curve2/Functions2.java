package zzz.study.patterns.observer.curve2;

import zzz.study.patterns.decorator.func.*;

public class Functions2 {

    private static Function t;
    private static Function rate;
    private static Function thrust;

    public static Function getT2() {

        if (t == null)
            t = new T();
        return t;
    }

    public static Function getRate2() {

        rate = new Arithmetic('*', new Constant(0.5),
                new ExpDouble(25,
                        new Arithmetic('*', new Constant(-1), new Square(new T()))));

        return rate;
    }

    public static Function getThrust2() {

        rate = new Arithmetic('*', new Constant(0.5),
                new ExpDouble(25,
                        new Arithmetic('*', new Constant(-1), new Square(new T()))));

        thrust = new Arithmetic('*', new Constant(1.7),
                new Pow(new Arithmetic('/', rate, new Constant(0.6)), (double) 1 / 0.3));

        return thrust;
    }

}
