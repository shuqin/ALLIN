package zzz.study.patterns.decorator.func;

public class Arithmetic extends  Function {
	
	protected char op;
	
	public Arithmetic(char op, Function f1, Function f2) {
		super(new Function[] {f1, f2});
		this.op = op;
	}
	
	public double f(double t) {
		switch(op) {
			case '+':
				return sources[0].f(t) + sources[1].f(t);
			case '-':
				return sources[0].f(t) - sources[1].f(t);
			case '*':
				return sources[0].f(t) * sources[1].f(t);
			case '/':
				return sources[0].f(t) / sources[1].f(t);
			default:
				return 0;
		}
	}
	
	public String toString() {
		
		StringBuffer buf = new StringBuffer("");
		if (sources.length > 0) {
			buf.append('(');
			buf.append(sources[0]);
			buf.append(Character.toString(op));
			buf.append(sources[1]);
			buf.append(')');
		}
		return buf.toString();

	}

}
