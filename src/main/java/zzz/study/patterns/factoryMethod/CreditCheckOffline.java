package zzz.study.patterns.factoryMethod;

public class CreditCheckOffline implements CreditCheck {

    /* 赊购限额离线审查： 根据客户id号返回其赊购限额  */

    public double creditLimit(int id) {

        System.out.println("Offline Check!");

        if (id > 100000)
            return 100000.00;
        else if (id > 10000)
            return 50000.00;
        else
            return 3000.00;
    }

}
