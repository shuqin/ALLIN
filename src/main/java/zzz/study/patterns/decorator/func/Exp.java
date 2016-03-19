package zzz.study.patterns.decorator.func;

public class Exp extends Function {
	
	public Exp() {
		super(new Function[] {});
	}
	
	public Exp(Function f) {
		super(new Function[] {f});
	}
	
	public double f(double t) {
		return Math.exp(sources[0].f(t));
	}

    public String toString() {
		
		StringBuffer buf = new StringBuffer("");
		if (sources.length > 0) {
			buf.append('(');
			buf.append('e');
			buf.append('^');
			buf.append(sources[0]);
			buf.append(')');
		}
		return buf.toString();
	}
    
}
