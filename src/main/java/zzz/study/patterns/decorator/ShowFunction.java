package zzz.study.patterns.decorator;

import zzz.study.patterns.decorator.func.Arithmetic;
import zzz.study.patterns.decorator.func.Cos;
import zzz.study.patterns.decorator.func.Function;
import zzz.study.patterns.decorator.func.Sin;
import zzz.study.patterns.decorator.func.Square;
import zzz.study.patterns.decorator.func.T;

public class ShowFunction {
	
	public static void main(String[] args) {
		Function complexFunc = new Arithmetic('+', new Square(new Sin(new T())), new Square(new Cos(new T())));
		System.out.println(complexFunc + " = " + complexFunc.f(100.0));
		
	}
	

}
