package com.deng.pp;

import java.io.IOException;

import com.black.collect.fetcher.AbstractFetcher;
import com.black.collect.fetcher.GoubanjiaFetcher;

import cz.mallat.uasparser.OnlineUpdater;
import cz.mallat.uasparser.UASparser;
import cz.mallat.uasparser.UserAgentInfo;
import eu.bitwalker.useragentutils.Browser;
import eu.bitwalker.useragentutils.OperatingSystem;
import eu.bitwalker.useragentutils.UserAgent;

/**
 * Created by hcdeng on 2017/6/29.
 */
public class Main {
	 static UASparser uasParser = null;
	 // 初始化uasParser对象
	 static {
	     try {
	        uasParser = new UASparser(OnlineUpdater.getVendoredInputStream());
	    } catch (IOException e) {
	         e.printStackTrace();
	      }
	 }
	 
    public static void main(String[] args) throws IOException {
    	UserAgent userAgent = UserAgent.parseUserAgentString("Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/60.0.3112.101 Safari/537.36 ");  
    	Browser browser = userAgent.getBrowser();  
    	OperatingSystem os = userAgent.getOperatingSystem();
    	System.out.println(browser.getName());
    	System.out.println(browser.getManufacturer().name());
    	System.out.println(browser.getRenderingEngine().name());

    	UserAgentInfo userAgentInfo = uasParser.parse("Mozilla/5.0 (Macintosh; Intel Mac OS X 10.9) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/65.0.3325.162 Safari/537.36 ");
    	System.out.println("操作系统名称："+userAgentInfo.getOsFamily());//
    	System.out.println("操作系统："+userAgentInfo.getOsName());//
    	System.out.println("浏览器名称："+userAgentInfo.getUaFamily());//
    	System.out.println("浏览器版本："+userAgentInfo.getBrowserVersionInfo());//
    	System.out.println("设备类型："+userAgentInfo.getDeviceType());
    	System.out.println("浏览器:"+userAgentInfo.getUaName());
    	System.out.println("类型："+userAgentInfo.getType());
    }
}
