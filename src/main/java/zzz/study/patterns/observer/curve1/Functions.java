package zzz.study.patterns.observer.curve1;

import zzz.study.patterns.decorator.func.Arithmetic;
import zzz.study.patterns.decorator.func.Constant;
import zzz.study.patterns.decorator.func.ExpDouble;
import zzz.study.patterns.decorator.func.Function;
import zzz.study.patterns.decorator.func.Pow;
import zzz.study.patterns.decorator.func.Square;
import zzz.study.patterns.decorator.func.T;

public class Functions  {
	
	private static Function t;
	private static Function rate;
	private static Function thrust;

	
    public static Function getT() {
		
    	if (t == null)
    	    t = new T();
		return t;
	}
	
	public static Function getRate() {
		
		  rate = new Arithmetic('*', new Constant(0.5), 
				    new ExpDouble(25,
					    new Arithmetic('*', new Constant(-1), new Square(new T()))));
		  
		   return rate;
	}
	
	public static Function getThrust() {
		
		   rate = new Arithmetic('*', new Constant(0.5), 
				 new ExpDouble(25,
					new Arithmetic('*', new Constant(-1), new Square(new T()))));
		
		   thrust = new Arithmetic('*', new Constant(1.7),
		           new Pow(new Arithmetic('/', rate, new Constant(0.6)), (double)1/0.3));
		      
		   return thrust;	
	}

}
