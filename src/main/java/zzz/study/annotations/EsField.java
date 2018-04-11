package zzz.study.annotations;

/**
 * Created by shuqin on 18/4/11.
 */

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by shuqin on 17/8/29.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Documented
public @interface EsField {

  /**
   * 对应的ES字段的值
   */
  String name();

  /**
   * 对应的ES字段的值类型
   * @return
   */
  String type() default "";

  /**
   * 是否必传
   */
  boolean required() default false;

}
