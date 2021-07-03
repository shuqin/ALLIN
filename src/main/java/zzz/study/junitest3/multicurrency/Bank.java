package zzz.study.junitest3.multicurrency;

import java.util.Hashtable;

public class Bank {

    private Hashtable rates = new Hashtable();

    public Money reduce(Expression source, String to) {
        return source.reduce(this, to);
    }

    public void addRate(String from, String to, double rate) {
        rates.put(new Pair(from, to), new Double(rate));
    }

    public double rate(String from, String to) {
        if (from.equals(to)) {
            return 1;
        }
        Double rate = (Double) rates.get(new Pair(from, to));
        return rate.doubleValue();
    }


}
