package zzz.study.patterns.decorator.func;

public class Sqrt extends Function {
	
	public Sqrt() {
		super(new Function[] {});
	}
	
	public Sqrt(Function f) {
		super(new Function[] {f});
	}
	
	public double f(double t) {
		return Math.sqrt(sources[0].f(t));
	}	 
	
    public String toString() {
		
		StringBuffer buf = new StringBuffer("");
		if (sources.length > 0) {
			buf.append('(');
			buf.append(sources[0]);
			buf.append('^');
			buf.append('(');
			buf.append(0.5);
			buf.append(')');
			buf.append(')');
		}
		return buf.toString();
	}
    
    public static void main(String[] args) {
		Function a = new Arithmetic('/', new T(), new Constant(0.6));
		Sqrt e = new Sqrt(a);
		System.out.println(e);
	}
    
}
