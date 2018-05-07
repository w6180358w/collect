package com.black.web.base.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Documented//说明该注解将被包含在javadoc中 
public @interface RaysModel {
	/**
	 * 是否为逻辑删除  默认为是
	 * @return
	 */
	boolean deleteLogic() default true;
	/**
	 * 是否为实体表   默认为是  false为关系表
	 * @return
	 */
	boolean entity() default true;
	/**
	 * 关联删除(需要关联)
	 * @return
	 */
	boolean relevanceDelete() default true;
	/**
	 * 是否可编辑
	 * @return
	 */
	boolean editable() default true;
	/**
	 * 关联查询子节点(非外键关联)
	 * @return
	 */
	ForeignSearch[] children() default {};
	
}
