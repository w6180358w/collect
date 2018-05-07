package com.black.web.base.enums;

public enum TagEnum {
	Idc("Idc"), Provider("Provider"),
	Resourcegroup("Resourcegroup"), Host("Host"),
	Vm("Vm"), Vmtemplate("Vmtemplate"),
	Networkdef("Networkdef"), Volume("Volume");
	
	private String value;
	
	private TagEnum(String value){
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}
}
