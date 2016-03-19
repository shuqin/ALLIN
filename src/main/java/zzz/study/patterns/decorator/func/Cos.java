package zzz.study.patterns.decorator.func;

public class Cos extends Function {
	
	public Cos() {
		super(new Function[] {});
	}
	
	public Cos(Function f) {
		super(new Function[] {f});
	}
	
	public double f(double t) {
		return Math.cos(sources[0].f(t));
	}
	
	public String toString() {
		
		String cos = "cos";
		StringBuffer buf = new StringBuffer(cos);
		if (sources.length > 0) {
			buf.append('(');
			buf.append(sources[0]);
			buf.append(')');
		}
		return buf.toString();
	}


}
