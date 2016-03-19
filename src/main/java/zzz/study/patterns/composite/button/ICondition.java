package zzz.study.patterns.composite.button;

import java.util.Map;

/**
 * 条件测试接口
 */
public interface ICondition {

    /**
     * 传入的 valueMap 是否满足条件对象
     *
     * @param valueMap 值对象
     *                 若 valueMap 满足条件对象，返回 true , 否则返回 false .
     */
    boolean satisfiedBy(Map<String, Object> valueMap);

    /**
     * 获取满足条件时要返回的值
     */
    Boolean getResult();

}
