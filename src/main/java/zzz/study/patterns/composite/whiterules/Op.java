package zzz.study.patterns.composite.whiterules;

/**
 * @Description 操作符
 * @Date 2021/5/8 4:25 下午
 * @Created by qinshu
 */
public enum Op {

    eq, neq, in, range, match, func,

    and, or,
    ;

    public static Op getOp(String name) {
        for (Op op : Op.values()) {
            if (op.name().equals(name)) {
                return op;
            }
        }
        return null;
    }
}
