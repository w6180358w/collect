package com.black.web.base.utils;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.apache.log4j.Logger;

/**
 * 日期时间工具类
 * @author mcl
 * Nov 13, 2012 11:36:19 AM
 */
public class DateUtil {

	private static Logger logger = Logger.getLogger (DateUtil.class) ;
	 
	private DateUtil(){
	}
	
	private static final SimpleDateFormat YYYY_MM_DD = new SimpleDateFormat("yyyy-MM-dd");
	private static final SimpleDateFormat YYYY_MM_DD_HH_MM = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	private static final SimpleDateFormat YYYY_MM_DD_HH_MM_SS = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	/**
	 * 时间戳转标准日期格式
	 * @param time 时间戳（10位）单位秒
	 * @return YYYY-MM-DD
	 */
	public static String integer2String10(Integer time){
		if (time == null) {
			return null;
		}
		if (time==0L) {
			return null;
		}
		Date date = new Date(time*1000L);
		return YYYY_MM_DD.format(date);
	}
	
	/**
	 * 时间戳转标准日期时间格式
	 * @param time 时间戳（10位）单位秒
	 * @return YYYY-MM-DD HH:MM:SS
	 */
	public static String integer2String19(Integer time){
		if (time == null) {
			return null;
		}
		if (time==0L) {
			return null;
		}
		Date date = new Date(time*1000L);
		return YYYY_MM_DD_HH_MM_SS.format(date);
	}
	
	/**
	 * 时间戳转标准日期时间格式
	 * @param time 时间戳（10位）单位秒
	 * @return YYYY-MM-DD HH:MM
	 */
	public static String integer2String16(Integer time){
		if (time == null) {
			return null;
		}
		if (time==0L) {
			return null;
		}
		Date date = new Date(time*1000L);
		return YYYY_MM_DD_HH_MM.format(date);
	}
	
	/**
	 * 标准日期格式转时间戳(时间默认 YYYY-MM-DD 00:00:00)
	 * @param str YYYY-MM-DD
	 * @return 时间戳（10位）单位秒
	 */
	public static Integer string10ToIntegerBegin(String str){
		if (StringUtils.nvlString(str).isEmpty()) {
			return null;
		}
		try {
			Date d = YYYY_MM_DD_HH_MM_SS.parse(str+" 00:00:00");
			Timestamp t = new Timestamp(d.getTime());
			return Integer.valueOf(String.valueOf(t.getTime()/1000));
		} catch (ParseException e) {
			e.printStackTrace();
			logger.error("=====string10ToIntegerBegin时间字符串转换异常====",e);
		}
		return null;
	}
	
	/**
	 * 标准日期时间格式转时间戳(时间默认 YYYY-MM-DD 23:59:59)
	 * @param str YYYY-MM-DD
	 * @return 时间戳（10位）单位秒
	 */
	public static Integer string10ToIntegerEnd(String str){
		if (StringUtils.nvlString(str).isEmpty()) {
			return null;
		}
		try {
			Date d = YYYY_MM_DD_HH_MM_SS.parse(str+" 23:59:59");
			Timestamp t = new Timestamp(d.getTime());
			return Integer.valueOf(String.valueOf(t.getTime()/1000));
		} catch (ParseException e) {
			e.printStackTrace();
			logger.error("=====string10ToIntegerEnd时间字符串转换异常====",e);
		}
		return null;
	}
	
	
	/**
	 * 标准日期时间格式转时间戳(时间默认 YYYY-MM-DD HH:MM:00)
	 * @param str YYYY-MM-DD HH:MM
	 * @return 时间戳（10位）单位秒
	 */
	public static Integer string16ToIntegerBegin(String str){
		if (StringUtils.nvlString(str).isEmpty()) {
			return null;
		}
		try {
			Date d = YYYY_MM_DD_HH_MM_SS.parse(str+":00");
			Timestamp t = new Timestamp(d.getTime());
			return Integer.valueOf(String.valueOf(t.getTime()/1000));
		} catch (ParseException e) {
			e.printStackTrace();
			logger.error("=====string16ToIntegerBegin时间字符串转换异常====",e);
		}
		return null;
	}
	
	/**
	 * 标准日期时间格式转时间戳(时间默认 YYYY-MM-DD HH:MM:59)
	 * @param str YYYY-MM-DD HH:MM
	 * @return 时间戳（10位）单位秒
	 */
	public static Integer string16ToIntegerEnd(String str){
		if (StringUtils.nvlString(str).isEmpty()) {
			return null;
		}
		try {
			Date d = YYYY_MM_DD_HH_MM_SS.parse(str+":59");
			Timestamp t = new Timestamp(d.getTime());
			return Integer.valueOf(String.valueOf(t.getTime()/1000));
		} catch (ParseException e) {
			e.printStackTrace();
			logger.error("=====string16ToIntegerEnd时间字符串转换异常====",e);
		}
		return null;
	}
	
	/**
	 * 标准日期时间格式转时间戳
	 * @param str YYYY-MM-DD HH:MM:SS
	 * @return 时间戳（10位）单位秒
	 */
	public static Integer string19ToInteger(String str){
		if (StringUtils.nvlString(str).isEmpty()) {
			return null;
		}
		try {
			Date d = YYYY_MM_DD_HH_MM_SS.parse(str);
			Timestamp t = new Timestamp(d.getTime());
			return Integer.valueOf(String.valueOf(t.getTime()/1000));
		} catch (ParseException e) {
			e.printStackTrace();
			logger.error("=====string19ToInteger时间字符串转换异常====",e);
		}
		return null;
	}
	
	/**
	 * 时间戳转任意日期格式
	 * @param time 时间戳（10位）单位秒
	 * @param format 时间格式
 	 * @return YYYY-MM-DD
	 */
	public static String integerToString(Integer time,String format){
		if (time == null) {
			return null;
		}
		if (time==0L) {
			return null;
		}
		Date date = new Date(time*1000L);
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
		return simpleDateFormat.format(date);
	}
	
	/**
	 * 任意日期时间格式转时间戳
	 * @param str YYYY-MM-DD HH:MM:SS
	 * @return 时间戳（10位）单位秒
	 */
	public static Integer stringToInteger(String string,String format){
		if (StringUtils.nvlString(string).isEmpty()) {
			return null;
		}
		try {
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
			Date d = simpleDateFormat.parse(string);
			Timestamp t = new Timestamp(d.getTime());
			return Integer.valueOf(String.valueOf(t.getTime()/1000));
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("=====stringToInteger时间字符串转换异常====",e);
		}
		return null;
	}
	
	/**
	 * 得到当前系统时间戳
	 * @return 时间戳（10位）单位秒
	 */
	public static Long getCurrentTime(){
		long currentTimeMillis = System.currentTimeMillis();
		return Long.valueOf(String.valueOf(currentTimeMillis));
	}
	
	/**
	 * 获取当前时间
	 * @return
	 */
	public static String getCurrentTimeStr(String dateFormat)
	{
		SimpleDateFormat format = new SimpleDateFormat(dateFormat);
		String date = format.format(new Date());
		return date;
	}
	/**
	 * 获取当前月份的时间戳（单位秒）
	 * @return
	 */
	public static Integer getCurrentMonth(){
		return stringToInteger(getCurrentTimeStr("yyyy-MM"), "yyyy-MM");
	}
	
	
	/**
	 * 从日期对象得到该日期零点的时间戳(单位秒)
	 * @param date 当前日期对象
	 * @return 该日期零点的时间戳(单位秒)
	 */
	public static Integer getZeroTimeByDate(Date day) {
		long  timeInMillis =0;
		Calendar calendar = null;
		try {
			
			calendar = GregorianCalendar.getInstance();
			calendar.setTime(day);
			calendar.set(Calendar.MILLISECOND, 0);
			calendar.set(Calendar.SECOND, 0);
			calendar.set(Calendar.MINUTE, 0);
			calendar.set(Calendar.HOUR_OF_DAY, 0);
			
			timeInMillis = calendar.getTimeInMillis()/1000;
		}
		catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
		return Integer.valueOf(String.valueOf(timeInMillis));
	}
	
	/**
	 * 从日期对象得到该月某日期零点的时间戳(单位秒)
	 * @param date 当前日期对象
	 * @param day 某月的第几天
	 * @return 该该月某日期零点的时间戳(单位秒)
	 */
	public static Integer getMonthDayZeroTimeByDate(Date date,int day) {
		long  timeInMillis =0;
		Calendar calendar = null;
		try {
			calendar = (GregorianCalendar) Calendar.getInstance();
			calendar.setTime(date);
			calendar.set(Calendar.DAY_OF_MONTH, day);
			calendar.set(Calendar.MILLISECOND, 0);
			calendar.set(Calendar.SECOND, 0);
			calendar.set(Calendar.MINUTE, 0);
			calendar.set(Calendar.HOUR_OF_DAY, 0);
			
			timeInMillis = calendar.getTimeInMillis();
			timeInMillis = timeInMillis / 1000;
		}catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
	    return Integer.valueOf(String.valueOf(timeInMillis));
	}	
	
	/**
	 * 日期比对
	 * 
	 * @param smdate
	 * @param bdate
	 * @return
	 */
	public static int daysBetween(String smdate, String bdate) {
		long between_days = 0;
		if(null == smdate || null == bdate){
			return Integer.parseInt(String.valueOf(between_days));
		}
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Calendar cal = Calendar.getInstance();
			cal.setTime(sdf.parse(smdate));
			long time1 = cal.getTimeInMillis();
			cal.setTime(sdf.parse(bdate));
			long time2 = cal.getTimeInMillis();
			between_days = (time1 - time2) / (1000 * 3600 * 24);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return Integer.parseInt(String.valueOf(between_days));
	}
	
	public static void main(String[] args) {
		System.out.println(getCurrentMonth()-60*60*24*30);
		System.out.println(getCurrentMonth()-60*60*24*30+3600*1);
		System.out.println(getCurrentMonth()-60*60*24*30+3600*2);
		System.out.println(getCurrentMonth()-60*60*24*30+3600*3);
		
		System.out.println(getCurrentMonth());
		System.out.println(getCurrentMonth()+3600*1);
		System.out.println(getCurrentMonth()+3600*2);
		System.out.println(getCurrentMonth()+3600*3);
		System.out.println(integer2String10(1));
	}
}
