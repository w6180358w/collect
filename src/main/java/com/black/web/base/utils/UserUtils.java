package com.black.web.base.utils;

import com.black.web.base.enums.RaysdataStatic;
import com.black.web.models.po.User;

public class UserUtils {

	public static User passwordENC(User user) throws Exception{
		user.setPassword(DesUtil.encrypt("ucode="+user.getUcode()+",password="+user.getPassword(),RaysdataStatic.DES_KEY_USER_PASSWORD));
		return user;
	}
	
	public static String passwordDEC(User user) throws Exception{
		String code = DesUtil.decrypt(user.getPassword(),RaysdataStatic.DES_KEY_USER_PASSWORD);
		return code.split(",")[1].split("=")[1];
	}
	
	public static User formatRedisUser(String str){
		User user = new User();
		String[] arr = str.split(",");
		user.setUcode(arr[0]);
		user.setEmail(arr[1]);
		user.setPhone(arr[2]);
		return user;
	}
	
	public static String toRedisUser(User user){
		return user.getUcode()+","+user.getEmail()+","+user.getPhone();
	}
}
