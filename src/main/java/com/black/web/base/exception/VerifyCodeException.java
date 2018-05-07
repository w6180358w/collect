package com.black.web.base.exception;

import com.black.web.base.msg.RaysMessage;

public class VerifyCodeException extends RaysException{

	public VerifyCodeException(String msg) {
		super(msg);
		this.code = 10032;
	}
	public VerifyCodeException() {
		super(RaysMessage.VERIFY_CODE);
		this.code = 10032;
	}
	private static final long serialVersionUID = 1L;

}
