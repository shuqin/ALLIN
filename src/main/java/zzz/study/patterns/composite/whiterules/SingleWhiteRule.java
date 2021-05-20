package zzz.study.patterns.composite.whiterules;

/**
 * @Description 原子规则
 * @Date 2021/5/18 6:56 下午
 * @Created by qinshu
 */
public interface SingleWhiteRule extends WhiteRule {

    String getField();
    Condition getCondition();
}
