package zzz.study.patterns.decorator;

import zzz.study.patterns.decorator.func.*;

public class ShowFunction {

    public static void main(String[] args) {
        Function complexFunc = new Arithmetic('+', new Square(new Sin(new T())), new Square(new Cos(new T())));
        System.out.println(complexFunc + " = " + complexFunc.f(100.0));

    }


}
