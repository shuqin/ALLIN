package zzz.study.patterns.abstractfactory;

/**
 * 具体产品： 实现  IMac 接口【针对美国市场】
 */
public class USIMac implements IMac {

    public String price() {
        return "$800";
    }

    public String policy() {
        return "5年保修";
    }

}

