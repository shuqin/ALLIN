package zzz.study.junitest3.multicurrency;

public class Money implements Expression {

    protected String currency;
    protected double amount;

    public Money(double amount, String currency) {
        this.amount = amount;
        this.currency = currency;
    }

    /**
     * Using USD for identifying Dollar.
     */
    public static Money dollar(double amount) {
        return new Money(amount, "USD");
    }

    /**
     * Using CHF for identifying Franc.
     */
    public static Money franc(double amount) {
        return new Money(amount, "CHF");
    }

    public static Expression rmb(double amount) {
        // TODO Auto-generated method stub
        return new Money(amount, "RMB");
    }

    public boolean equals(Object obj) {
        Money money = (Money) obj;
        return amount == money.amount && currency().equals(money.currency());
    }

    public String currency() {
        return currency;
    }

    public Expression times(double times) {
        return new Money(amount * times, currency);
    }

    public String toString() {
        return amount + " " + currency;
    }


    public Expression plus(Expression andend) {

        return new Sum(this, andend);
    }

    public Money reduce(Bank bank, String to) {
        double rate = bank.rate(currency, to);
        return new Money(amount / rate, to);
    }


}
