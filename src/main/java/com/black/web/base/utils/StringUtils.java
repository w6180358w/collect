package com.black.web.base.utils;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串工具类
 * Nov 13, 2012 11:35:42 AM
 */
public class StringUtils extends org.apache.commons.lang.StringUtils {

	/**
	 * 功能:判断字符串是否为数字
	 * @param srcString  源字符串
	 * @return boolean
	 */
	public static boolean isNumeric(String srcString){
		boolean returnVal = false;
		if(isNotBlank(srcString)){
			Pattern pattern = Pattern.compile("[0-9]*");
			Matcher isNum = pattern.matcher(srcString);
			if( !isNum.matches() ){
				returnVal = false;
			}else{
				returnVal = true;
			}
		}else{
			returnVal = false;
		}
		return returnVal;
	}	
	
	
	/**
	 * Object对象转字符串
	 * @param obj
	 * @param target
	 * @return
	 */
	public static String emptyConvert(Object obj, String target) {
		return isBlank(nvlString(obj)) ? target : nvlString(obj);
	}
	
	/**
	 * 返回不为null的字符串
	 * @param obj
	 * @return
	 */
	public static String nvlString(Object obj) {
		return obj == null ? EMPTY : String.valueOf(obj);
	}

	

	/**
	 * MD5加密方法
	 * @param src 源字符串
	 * @return 加密 后字符串
	 */
	public static String securityMD5(String src) {
		StringBuffer sb = new StringBuffer();
		src = StringUtils.nvlString(src);

		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(src.getBytes());
			return new BigInteger(1, md.digest()).toString(16);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return sb.toString();
	}

	
	/**
	 * substring
	 * @param str
	 * @param start
	 * @param end
	 * @return
	 */
	public static String substring(Object str,int start,int end){
		return substring(nvlString(str), start, end);
	}
	
	/**
	 * 替换 textarea 文本框中的特殊字符
	 * @param str
	 * @return
	 */
	public static String transTextArea(String str){
		str = StringUtils.nvlString(str);
		str = str.replaceAll("\n", "<br>");
		str = str.replaceAll(" ", "&nbsp;");
		return str;
	}
	
	/**
	 * 根据当前时间戳生成任意位随机数（最短14位）
	 * @param size 位数
	 * @return
	 */
	public static String getTimeMillisRandom(int size){
		
		size = size - 13;
		size = size < 1 ? 1 : size;
		
		long currentTimeMillis = System.currentTimeMillis();
		
		return String.valueOf(currentTimeMillis) + getRandom(size);
	}
	
	/**
	 * 根据当前时间生成任意位随机数（最短15位）
	 * @param size 位数
	 * @return
	 */
	public static String getDateTimeRandom(int size){
		size = size - 14;
		size = size < 1 ? 1 : size;
		
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
		
		return format.format(new Date()) + getRandom(size);
	}
	
	/**
	 * 生成指定位数的随机数(最多19位)
	 * @param size 随机数位数
	 * @return
	 */
	public static String getRandom(int size){
		
		size = size < 1 ? 1 : size;
		
		size = size > 19 ? 19 : size;
		
		double pow = Math.pow(10, size);
		
		double random = Math.random();
		
		if (random < 0.1) {
			random = random + 0.1;
		}
		
		long randomLong = (long) (random * pow);
		
		return String.valueOf(randomLong > 0 ? randomLong : (-1) * randomLong);
	}
	
	/**
	 * 字符串转Integer
	 * @param string
	 * @return
	 */
	public static Integer stringToInteger(String string){
		if (string == null) {
			return null;
		}
		
		return Integer.parseInt(string);
	}
	
	/**
	 * 字符串转Double
	 * @param string
	 * @return
	 */
	public static Double stringToDouble(String string){
		if (string == null) {
			return null;
		}
		
		return Double.parseDouble(string);
	}
	
	/**
	 * 字符串按分割符逐项求和(Double)
	 * @param x
	 * @param y
	 * @param regex 分割符
	 * @return z=x+y
	 */
	public static String addStringBySplit(String x,String y,String regex){
		
		String[] listX = x.split(regex);
		String[] listY = y.split(regex);
		String z = "";
		
		for (int i = 0; i < StrictMath.max(listX.length, listY.length); i++) {
			z += regex+String.valueOf((Double.valueOf(ZeroStringL(i, listX))+Double.valueOf(ZeroStringL(i, listY))));
		}
		
		return z.substring(regex.length());
	}
	public static String ZeroStringL(int i,String[] strL) {
		if(i<strL.length){
			return isBlank(strL[i]) ? "0" : strL[i];
		}else{
			return "0";
		}
	}
	
	/**
	 * 并集（set唯一性）
	 * @param arr1
	 * @param arr2
	 * @return
	 */
    public static String[] union (String[] arr1, String[] arr2){
        Set<String> hs = new HashSet<String>();
        for(String str:arr1){
            hs.add(str);
        }
        for(String str:arr2){
            hs.add(str);
        }
        String[] result={};
        return hs.toArray(result);
    }

    /**
     * 交集(注意结果集中若使用LinkedList添加，则需要判断是否包含该元素，否则其中会包含重复的元素)
     * @param arr1
     * @param arr2
     * @return
     */
    public static String[] intersect(String[] arr1, String[] arr2){
        List<String> l = new LinkedList<String>();
        Set<String> common = new HashSet<String>();                  
        for(String str:arr1){
            if(!l.contains(str)){
                l.add(str);
            }
        }
        for(String str:arr2){
            if(l.contains(str)){
                common.add(str);
            }
        }
        String[] result={};
        return common.toArray(result);
    }
    /**
     * 求两个数组的差集   
     * @param arr1
     * @param arr2
     * @return
     */
    public static String[] substract(String[] arr1, String[] arr2) {   
        LinkedList<String> list = new LinkedList<String>();   
        for (String str : arr1) {   
            if(!list.contains(str)) {   
                list.add(str);   
            }   
        }   
        for (String str : arr2) {   
            if (list.contains(str)) {   
                list.remove(str);   
            } 
        }   
        String[] result = {};   
        return list.toArray(result);   
    }
	
	/** 
     * ip校验 
     * @param s 
     * @return Boolean 
     */  
    public static boolean isIpV4Address(String s){  
            String regex = "(((2[0-4]\\d)|(25[0-5]))|(1\\d{2})|([1-9]\\d)|(\\d))[.]"
            		+ "(((2[0-4]\\d)|(25[0-5]))|(1\\d{2})|([1-9]\\d)|(\\d))[.]"
            		+ "(((2[0-4]\\d)|(25[0-5]))|(1\\d{2})|([1-9]\\d)|(\\d))[.]"
            		+ "(((2[0-4]\\d)|(25[0-5]))|(1\\d{2})|([1-9]\\d)|(\\d))";  
            Pattern p = Pattern.compile(regex);  
            Matcher m = p.matcher(s);  
            return m.matches();  
    }
    /**
     * 获取类名   不包括包名
     * @param clazz
     * @return
     */
    public static String getClassName(Class<?> clazz){
    	String clazzStr = clazz.toString();
    	return clazzStr.substring(clazzStr.lastIndexOf(".")+1,clazzStr.length());
    }
	public static void main(String[] args) {
		
//		System.out.println("111111加密后为:"+StringUtils.securityMD5("111111"));
		System.out.println(addStringBySplit("1--3-4-567", "2-1-3-4-9-13-99---", "-"));
		System.out.println(addStringBySplit("", "", "-"));

		for(int i=0;i<10;i++){
			String uuid = UUID.randomUUID().toString();
			System.out.println(uuid);
		}
		
	}
	
}
