package com.black.web.base.enums;
/*
 * 数据通用状态 [有效/删除]
 * 
 */
public enum StatusEnum {
	DELETE("删除"),
	ENABLED("启用"),DISABLED("禁用");
	
	private String display;
	private StatusEnum(String display) {  
        this.display = display;  
    }
	public String getDisplay() {
		return display;
	}
}
