package com.black.web.base.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 关联注解
 * @author zxy
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
@Documented//说明该注解将被包含在javadoc中 
public @interface ForeignDelete {
	public FieldTag[] fieldTag();
}
