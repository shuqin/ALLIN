package zzz.study.patterns.abstractfactory;

/**
 * 具体产品： 实现  IPad 接口【针对美国市场】
 */
public class USIPad implements IPad {

    public String price() {
        return "$500";
    }

    public String policy() {
        return "2年保修";
    }

}
