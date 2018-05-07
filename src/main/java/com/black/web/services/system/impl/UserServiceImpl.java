package com.black.web.services.system.impl;

import com.black.web.base.exception.RaysException;
import com.black.web.base.service.impl.BaseServiceImpl;
import com.black.web.models.po.User;
import com.black.web.models.po.Widget;
import com.black.web.services.system.UserService;
import org.springframework.stereotype.Repository;

@Repository
public class UserServiceImpl extends BaseServiceImpl implements UserService {

	@Override
	public void test() throws Exception {
		User user = new User();
		user.setUcode("a");
		User user1 = new User();
		user1.setUcode("b");
		this.save(user);
		this.save(user1);
		throw new RaysException();
	}

	@Override
	public void test1() throws Exception {
		Widget widget = new Widget();
		widget.setName("aa");
		this.save(widget);
	}
	
}

