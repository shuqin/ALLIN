package zzz.study.junitest3.multicurrency;

public interface Expression {
	
	Money reduce(Bank bank, String to);
    Expression plus(Expression addend);
	Expression times(double times);
}
