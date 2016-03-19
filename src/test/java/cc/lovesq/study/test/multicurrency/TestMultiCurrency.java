package cc.lovesq.study.test.multicurrency;

import junit.framework.TestCase;
import zzz.study.junitest3.multicurrency.Bank;
import zzz.study.junitest3.multicurrency.Expression;
import zzz.study.junitest3.multicurrency.Money;
import zzz.study.junitest3.multicurrency.Sum;

public class TestMultiCurrency extends TestCase {

    public TestMultiCurrency(String name) {
        super(name);
    }

    public void testEquality() {
        assertTrue(Money.dollar(5).equals(Money.dollar(5)));
        assertFalse(Money.dollar(6).equals(Money.dollar(5)));
        assertFalse(Money.franc(5).equals(Money.dollar(5)));
    }

    public void testDollar() {
        Money five = Money.dollar(5);
        assertEquals(Money.dollar(10), five.times(2));
        assertEquals(Money.dollar(15), five.times(3));
    }

    public void testMultiCurrency() {
        assertEquals("USD", Money.dollar(1).currency());
        assertEquals("CHF", Money.franc(1).currency());
    }

    // ------------------ Money Only can solve tests above. -----------------
    // ------------------ More challenging tests below. ------------------
    // ------------------ More polymiorphism feature used below. -----------------

    public void testSimpleAddition() {
        Money five = Money.dollar(5);
        Expression result = five.plus(five);
        Sum sum = (Sum) result;
        Bank bank = new Bank();
        assertEquals(five, sum.augend);
        assertEquals(five, sum.andend);
        Money reduced = bank.reduce(sum, "USD");
        assertEquals(Money.dollar(10), reduced);
    }

    public void testReduceSum() {
        Expression sum = new Sum(Money.dollar(3), Money.dollar(4));
        Bank bank = new Bank();
        Money result = bank.reduce(sum, "USD");
        assertEquals(Money.dollar(7), result);
    }

    public void testReduceMoney() {
        Bank bank = new Bank();
        Money result = bank.reduce(Money.dollar(1), "USD");
        assertEquals(Money.dollar(1), result);
    }

    public void testIdentityRate() {
        assertEquals((double) 1, new Bank().rate("USD", "USD"));
    }

    public void testRateExchange() {
        Bank bank = new Bank();
        bank.addRate("CHF", "USD", 2);
        Money result = bank.reduce(Money.franc(2), "USD");
        assertEquals(Money.dollar(1), result);
    }

    public void testMixedAddition() {
        Expression fiveDollars = Money.dollar(5);
        Expression tenFrancs = Money.franc(10);
        Bank bank = new Bank();
        bank.addRate("CHF", "USD", 2);
        Money result = bank.reduce(fiveDollars.plus(tenFrancs), "USD");
        assertEquals(Money.dollar(10), result);
    }

    public void testSumPlus() {
        Expression fiveDollars = Money.dollar(5);
        Expression tenFrancs = Money.franc(10);
        Bank bank = new Bank();
        bank.addRate("CHF", "USD", 2);
        Expression sum = new Sum(fiveDollars, tenFrancs).plus(fiveDollars);
        Money result = bank.reduce(sum, "USD");
        assertEquals(Money.dollar(15), result);
    }

    public void testSumTimes() {
        Expression fiveDollars = Money.dollar(5);
        Expression tenFrancs = Money.franc(10);
        Bank bank = new Bank();
        bank.addRate("CHF", "USD", 2);
        Expression sum = new Sum(fiveDollars, tenFrancs).times(2);
        Money result = bank.reduce(sum, "USD");
        assertEquals(Money.dollar(20), result);
    }

    public void testAnotherExchange() {
        Expression fiveDollars = Money.dollar(5);
        Expression fifteenRMB = Money.rmb(15);
        Bank bank = new Bank();
        bank.addRate("RMB", "USD", 3);
        Expression sum = new Sum(fiveDollars, fifteenRMB);
        Money result = bank.reduce(sum, "USD");
        assertEquals(Money.dollar(10), result);
    }

}
