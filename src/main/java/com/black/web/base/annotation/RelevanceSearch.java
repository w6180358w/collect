package com.black.web.base.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 关联查询注解
 * 根据配置的条件查询数据
 * 并将数据保存在额外字段children中
 * children为一个json对象:
 * key为RelevanceSearch中的select值(表名或唯一标识符)
 * value为根据RelevanceSearch查询出来的集合
 * 
 * @author zxy
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Documented//说明该注解将被包含在javadoc中 
public @interface RelevanceSearch {
	ForeignSearch[] childs();
}
