package com.black.web.base.msg;

import java.lang.reflect.Method;
import java.util.Map;

import com.black.web.base.annotation.Message;
import com.black.web.base.annotation.Unique;

public class RaysMessage {

	public static final String AUTH_VALIDATE = "权限验证错误，请联系管理员!";
	public static final String NEWWORK_ERROR = "网络错误，请稍后重试!";
	public static final String FIELD_VALIDATE = "字段唯一性验证错误!";
	public static final String ILLEGAL = "非法参数";
	public static final String ILLEGAL_KEY_COLUMN_NULL = "关键字列不能为空";
	public static final String ILLEGAL_ID_NOT_NULL = "主键不能为空";
	public static final String ILLEGAL_TREE_CONFIG_NULL = "未配置树结构关联字段!";
	public static final String LICENSE = "证书不存在或已失效";
	public static final String UNKONW_ERROR = "未知错误";
	public static final String TOKEN = "Token解析错误，请联系管理员!";
	public static final String VERIFY_CODE = "验证码错误!";
	public static final String ACTION_SUCCESS = "操作成功!";
	
	public static String getUniqueMsg(Unique unique,String value,String className,String fieldName,Map<String,String> fieldsMap) throws Exception{
		String msg = "";
		Message message = unique.msg();
		if(message!=null){
			switch (message.type()) {
			case "method":
				Method method = message.clazz().getDeclaredMethod(message.method(),Unique.class,String.class,String.class,String.class,Map.class);
				msg = method.invoke(null, unique, value,className,fieldName,fieldsMap).toString();
				break;
			case "default":
				StringBuffer sb = new StringBuffer("字段值重复["+className+"."+fieldName+"='"+value+"']");
				if(unique.fields().length>0){
					sb.append(",过滤条件[");
					for (String key : fieldsMap.keySet()) {
						sb.append("("+key+"="+fieldsMap.get(key)+")");
					}
					sb.append("]");
				}
				msg = sb.toString();
				break;
			case "value":
				msg = message.value();
				break;
			default:
				break;
			}
		}
		return msg;
	}
	
	public static String getEditableFieldMsg(String className,String filedName){
		return "字段["+className+"."+filedName+"]不允许修改";
	}
	public static String getEditableModelMsg(String className){
		return "当前实体["+className+"]不允许修改";
	}
	public static String getDataRepeatMsg(String className,String fieldName,String value){
		return "数据重复:"+className+"."+fieldName+"="+value;
	}
	public static String getUnKonwFieldMsg(String className,String fieldName){
		return "["+className+"]找不到字段["+fieldName+"]";
	}
}
