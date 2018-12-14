package com.black.web.services.sync.impl;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;

import com.black.collect.entity.GoodsEntity;

public class TaobaoSyncServiceImpl extends BaseSyncServiceImpl{

	@Override
	public void sync(List<GoodsEntity> data,String key,Integer count) throws Exception {
		System.setProperty("webdriver.chrome.driver", "C:\\Program Files (x86)\\Google\\Chrome\\Application\\chromedriver.exe");
    	ChromeOptions options = new ChromeOptions();

    	//options.addArguments("--proxy-server=127.0.0.1:8001");
    	//options.addArguments("headless");
    	options.addArguments("no-sandbox");
    	options.addArguments("--start-maximized");
    	WebDriver driver = new ChromeDriver(options);
    	driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    	driver.get("https://login.taobao.com/member/login.jhtml?spm=a21bo.2017.754894437.1.5af911d9bFsMVv&f=top&redirectURL=https%3A%2F%2Fwww.taobao.com%2F");
    	//移动滑块
        move(driver);
        
        //登录
        driver.findElement(By.xpath("//*[@id=\"J_SubmitStatic\"]")).click();
        //输入搜索条件
        driver.findElement(By.xpath("//*[@id=\"q\"]")).sendKeys(key);
        Thread.sleep(500);
        //点击搜索按钮
        driver.findElement(By.xpath("//*[@id=\"J_TSearchForm\"]/div[1]/button")).click();
        //获取所有商品节点  解析
        List<WebElement> items = driver.findElement(By.xpath("//*[@id=\"mainsrp-itemlist\"]/div/div/div[1]")).findElements(By.className("item"));
        items.forEach(item->{
        	//如果结束标记未结束  并且当前数据量小于规定数据量
        	if(!shutdown && data.size() < count ) {
        		WebElement ctx = item.findElement(By.className("ctx-box"));
            	String price = ctx.findElement(By.className("price")).getText();
            	WebElement titleTag = ctx.findElement(By.className("title")).findElement(By.tagName("a"));
            	String url = titleTag.getAttribute("href");
            	String title = titleTag.getText();
            	
            	WebElement sub = ctx.findElement(By.className("row-3")).findElement(By.className("shop")).findElement(By.tagName("a"));
            	String subjectName = sub.getText();
            	GoodsEntity entity = new GoodsEntity();
            	entity.setSource("taobao");
            	entity.setPrice(price);
            	entity.setSubjectName(subjectName);
            	entity.setName(title);
            	entity.setUrl(url);
            	System.out.println("已采集:"+entity.toString());
            	data.add(entity);
        	}
        });
        
        driver.close();
        driver.quit();
        //driver.findElement(By.xpath("//*[@id=\"J_Itemlist_TLink_522997440305\"]")).click();
	}
	
	private void move(WebDriver driver) throws InterruptedException {
    	((JavascriptExecutor) driver).executeScript(
    			"    console.log(window.navigator.webdriver);"+
    			"    Object.defineProperties(navigator,{" + 
    			"        webdriver:{" + 
    			"            get:() => false" + 
    			"        }" + 
    			"    });"+
    			"    console.log(window.navigator.webdriver);");
    	driver.findElement(By.xpath("//*[@id=\"J_LoginBox\"]/div[1]/div[1]")).click();
    	
    	WebElement username = driver.findElement(By.xpath("//*[@id=\"TPL_username_1\"]"));
        username.sendKeys("w6180358w");
        Thread.sleep(1000);
        WebElement password = driver.findElement(By.xpath("//*[@id=\"TPL_password_1\"]"));
        password.sendKeys("z5754784");
        /*WebElement form = driver.findElement(By.xpath("//*[@id=\"J_Form\"]"));
        form.click();*/
        
        Thread.sleep(1000);
        
    	WebElement drop = driver.findElement(By.xpath("//*[@id=\"nc_1_n1z\"]"));
    	
        Actions actions = new Actions(driver);
        actions.clickAndHold(drop).perform();
        Thread.sleep(1000);
        actions.moveByOffset(drop.getLocation().getX()+800, drop.getLocation().getY()).perform();
        Thread.sleep(1000);
    }

}
