package com.black.web.base.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.black.web.base.msg.RaysMessage;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
@Documented//说明该注解将被包含在javadoc中 
public @interface Message {

	String type() default "default";
	
	Class<? extends RaysMessage> clazz() default RaysMessage.class;
	
	String method() default "";
	
	String value() default "default";
}
