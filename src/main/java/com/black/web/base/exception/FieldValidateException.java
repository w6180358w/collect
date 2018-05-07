package com.black.web.base.exception;

import com.black.web.base.msg.RaysMessage;

public class FieldValidateException extends RaysException{
	
	public FieldValidateException(String msg) {
		super(msg);
		this.code = 40000;
	}
	public FieldValidateException() {
		super(RaysMessage.FIELD_VALIDATE);
		this.code = 40000;
	}
	
	private static final long serialVersionUID = 1L;
}
