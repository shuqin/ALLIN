package zzz.study.patterns.decorator.func;

public class Sin extends Function {

    public Sin() {
        super(new Function[]{});
    }

    public Sin(Function f) {
        super(new Function[]{f});
    }

    public double f(double t) {
        return Math.sin(sources[0].f(t));
    }

    public String toString() {

        String sin = "sin";
        StringBuffer buf = new StringBuffer(sin);
        if (sources.length > 0) {
            buf.append('(');
            buf.append(sources[0]);
            buf.append(')');
        }
        return buf.toString();
    }

}
