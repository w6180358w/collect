package com.black.web.base.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 字段关联查询注解(外键关联)
 * 根据当前表中的外键查询对应表的数据
 * 并将查询出来的数据转换成json对象替换原字段
 * 
 * 该注解会拼接成hql
 * from为主表  to数组为关联表 最后一位是要查询的子表数据
 * to数组必须按照顺序写   如果数组中相邻两个数据中的clazz相同则会跳过(否则会造成同一张表不同字段相等的情况,例如:TenantsApp.appId = TenantsApp.tenantsId)
 * select后接查询的数据和from主表的字段  保证主子表数据是对应的
 * @author zxy
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
@Documented//说明该注解将被包含在javadoc中 
public @interface ForeignSearch {
	
	public String code() default "";
	
	public FieldTag from();
	
	public FieldTag[] to();
}
