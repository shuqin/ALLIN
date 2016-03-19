package zzz.study.patterns.wrapper.rules.constants;

/**
 * @Description 操作符
 * @Date 2021/5/8 4:25 下午
 * @Created by qinshu
 */
public enum Op {

    /******* 函数运算符 ******/
    EQ,

    NEQ,

    IN,

    REGEX,

    RANGE,

    CUSTOMIZE,  //自定义函数

    /******* 逻辑运算符 ******/
    AND,

    OR;

    public static Op getOp(String op) {
        return Op.valueOf(op);
    }
}
