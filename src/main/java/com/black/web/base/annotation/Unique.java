package com.black.web.base.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 字段重复校验注解
 * @author zxy
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
@Documented//说明该注解将被包含在javadoc中 
public @interface Unique {
	
	//是否开启
	boolean value() default true;
	
	//过滤条件(本类的某些字段数组)
	String[] fields() default {};
	
	//忽略值
	String[] ignore() default {"null"};
	
	//提示信息
	Message msg() default @Message;
}
