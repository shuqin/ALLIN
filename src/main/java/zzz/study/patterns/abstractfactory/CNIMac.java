package zzz.study.patterns.abstractfactory;

/**
 * 具体产品： 实现  IMac 接口【针对中国市场】
 */
public class CNIMac implements IMac {

    public String price() {
        return "5000RMB";
    }

    public String policy() {
        return "3年保修";
    }

}
