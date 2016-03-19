package zzz.study.patterns.wrapper.rules.condition;

import com.alibaba.fastjson.JSON;
import shared.utils.JsonPathUtil;
import zzz.study.patterns.wrapper.rules.constants.Op;
import zzz.study.patterns.wrapper.rules.matcher.RuleMatcher;

import java.util.List;

/**
 * wenjin.xu 2021/6/7
 * 白名单规则条件基类(抽象类)
 */
public abstract class AbstractRuleCondition<T> implements RuleCondition<T> {

    protected String field;
    protected Op op;

    protected RuleMatcher ruleMatcher;

    public AbstractRuleCondition(String field, Op op) {
        this.field = field;
        this.op = op;
    }

    public AbstractRuleCondition(String field, Op op, RuleMatcher ruleMatcher) {
        this.field = field;
        this.op = op;
        this.ruleMatcher = ruleMatcher;
    }

    /**
     * 条件匹配
     *
     * @param obj 上下文数据对象
     */
    @Override
    public boolean match(T obj) {

        // 2、从obj中解析出对应field的值
        Object fv = JsonPathUtil.readVal(JSON.toJSONString(obj), field);
        if (fv == null) {
            return false;
        }

        // 3、根据解析出值的类型，做不同的处理
        if (fv instanceof List) {
            // 3.1、fv为集合
            List<Object> list = (List<Object>) fv;
            for (Object subObj : list) {
                if (!check(subObj)) {
                    return false;
                }
            }
            return true;
        }

        // 3.2、fv为非集合
        return check(fv);
    }

    /**
     * 条件匹配(子类可以覆写自定义)
     *
     * @param obj 属性值
     */
    public abstract boolean check(Object obj);
}
