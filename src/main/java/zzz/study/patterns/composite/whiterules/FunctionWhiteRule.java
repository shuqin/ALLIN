package zzz.study.patterns.composite.whiterules;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import shared.utils.ReflectionUtil;

import java.lang.reflect.Method;

/**
 * @Description 支持函数调用
 * @Date 2021/5/18 12:02 下午
 * @Created by qinshu
 */
@Getter
@Setter
public class FunctionWhiteRule implements SingleWhiteRule {

    private Boolean single = Boolean.TRUE;
    private Op op = Op.func;
    private String func;   // 类的全限定性名.方法名

    private SingleWhiteRule whiteRule;

    public FunctionWhiteRule(String func, SingleWhiteRule whiteRule) {
        this.func = func;
        this.whiteRule = whiteRule;
    }

    @Override
    public boolean test(Object obj) {
        Object fv = ReflectionUtil.getValue(obj, getField());
        Object calcatedFv = call(fv);
        return whiteRule.getCondition().test(calcatedFv);
    }

    private Object call(Object fv) {
        if (StringUtils.isEmpty(func)) {
            return fv;
        }
        String[] clsAndMethod = func.split("#");
        if (clsAndMethod.length != 2) {
            return fv;
        }
        String className = clsAndMethod[0];
        String method = clsAndMethod[1];

        try {
            Class c = Class.forName(className);
            Method m = c.getMethod(method, Holder.class);
            return m.invoke(null, new Holder<>(fv));
        } catch (Exception ex) {
            // log the exception
            return fv;
        }
    }

    @Override
    public String getField() {
        return whiteRule.getField();
    }

    @Override
    public Condition getCondition() {
        return whiteRule.getCondition();
    }
}
