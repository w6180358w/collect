package com.black.web.base.utils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;

public class ObjUtil {

	public static Object copyObj(Object o1,Object o2,String[] ignores) throws Exception{
		Class<? extends Object> cls2 = o2.getClass();
		
		Field[] fileds2 = cls2.getDeclaredFields(); 
		
		List<String> ig = new ArrayList<String>();
		for (String str : ignores) {
			ig.add(str);
		}
		for (Field field : fileds2) {
			String name = field.getName();
			if(ig.contains(name)){
				continue;
			}
            Column col = field.getAnnotation(Column.class);
            if (col != null) {
            	field.setAccessible(true);
                field.set(o1, field.get(o2));
                field.setAccessible(true);
            }
		}
		return o1;
	}
}
