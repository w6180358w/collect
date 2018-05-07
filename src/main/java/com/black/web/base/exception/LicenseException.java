package com.black.web.base.exception;

import com.black.web.base.msg.RaysMessage;

public class LicenseException extends RaysException{

	public LicenseException(String msg) {
		super(msg);
		this.code = 1;
	}
	public LicenseException() {
		super(RaysMessage.LICENSE);
		this.code = 1;
	}
	private static final long serialVersionUID = 1L;

}
