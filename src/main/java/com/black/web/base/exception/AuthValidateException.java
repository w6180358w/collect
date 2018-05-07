package com.black.web.base.exception;

import com.black.web.base.msg.RaysMessage;

public class AuthValidateException extends RaysException{
	
	public AuthValidateException(String msg) {
		super(msg);
		this.code = 30000;
	}
	public AuthValidateException() {
		super(RaysMessage.AUTH_VALIDATE);
		this.code = 30000;
	}
	
	private static final long serialVersionUID = 1L;

}
