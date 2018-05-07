package com.black.web.base.exception;

import com.black.web.base.msg.RaysMessage;

public class TokenException extends RaysException{

	public TokenException(String msg) {
		super(msg);
		this.code = 20000;
	}
	public TokenException() {
		super(RaysMessage.TOKEN);
		this.code = 20000;
	}
	private static final long serialVersionUID = 1L;

}
