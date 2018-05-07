package com.black.web.base.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 树结构注解(表示该字段为树结构关联字段(例:parentId))
 * @author zxy
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
@Documented//说明该注解将被包含在javadoc中 
public @interface TreeField {
	//表示关联的哪一个字段
	public String field() default "id";
	//表示标记的字段的哪些值为根节点
	public String value() default "0";
}
