package zzz.study.junitest3.multicurrency;

public class Sum implements Expression {
	
	public Expression  augend;
	public Expression  andend;
	
	public Sum(Expression augend, Expression andend) {
		this.andend = andend;
		this.augend = augend;
	}

	public Money reduce(Bank bank, String to) {
		
		double amount = augend.reduce(bank, to).amount + andend.reduce(bank, to).amount;
		return new Money(amount, to);
	}

	public Expression plus(Expression addend) {
		// TODO Auto-generated method stub
		return new Sum(this, addend);
	}
	
	public Expression times(double times) {
		return new Sum(augend.times(times), andend.times(times));
	}
	

}
