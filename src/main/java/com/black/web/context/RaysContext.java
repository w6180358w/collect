package com.black.web.context;

import com.black.web.models.po.User;

public class RaysContext {

	public static ThreadLocal<User> user = new ThreadLocal<User>();
	
}
