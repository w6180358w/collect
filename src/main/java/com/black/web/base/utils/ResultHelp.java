package com.black.web.base.utils;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 
 * @author wangshuo
 *
 */
public class ResultHelp {
	private final static String code_mark="code";
	private final static String content_mark="content";
	private final static String result_mark="result";
	public static String result(int code,String content,String result){
		Map<String,Object> map=new HashMap<String,Object>(); 
		map.put(code_mark, code);
		map.put(content_mark, content);
		map.put(result_mark, result);
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			return objectMapper.writeValueAsString(map);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return null;
	}
}
