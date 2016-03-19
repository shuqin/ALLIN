package cc.lovesq.flows.definitions;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ComponentProperties {

    /**
     * 组件的意图或功能
     */
    String purpose();

    /**
     * 组件的业务类型选择
     */
    String biz() default "common";

}
