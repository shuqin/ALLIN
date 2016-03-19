package zzz.study.foundations.annotations.demo;

import java.lang.annotation.*;


@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Test {

	int id() default 0;

	String desc() default "default";	

}
