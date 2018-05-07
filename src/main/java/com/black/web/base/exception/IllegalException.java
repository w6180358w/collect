package com.black.web.base.exception;

import com.black.web.base.msg.RaysMessage;

public class IllegalException extends RaysException{
	
	public IllegalException(String msg) {
		super(msg);
		this.code = 10000;
	}

	public IllegalException() {
		super(RaysMessage.ILLEGAL);
		this.code = 10000;
	}
	
	private static final long serialVersionUID = 1L;
}
