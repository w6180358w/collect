package com.black.web.base.enums;

public enum ProcessEnum {
	APPROVING("审批中"),APPLYFAIL("提交失败"),
	APPROVE("审批通过"),REJECT("审批退回"),
	COMPLETED("已完成"),OPERATEFAIL("操作失败");
	
	private String display;
	private ProcessEnum(String display) {  
        this.display = display;  
    }
	public String getDisplay() {
		return display;
	}
}
