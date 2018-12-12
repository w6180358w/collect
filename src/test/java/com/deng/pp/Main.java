package com.deng.pp;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.logging.LogFactory;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;

import com.black.collect.entity.GoodsEntity;
import com.black.collect.fetcher.AbstractFetcher;
import com.black.collect.fetcher.GoubanjiaFetcher;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebClientOptions;
import com.gargoylesoftware.htmlunit.html.HtmlButton;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlPasswordInput;
import com.gargoylesoftware.htmlunit.html.HtmlSpan;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;
import com.gargoylesoftware.htmlunit.util.Cookie;
import com.google.gson.Gson;

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
	
	private static final String[][] HEADERS = new String[][]{
        {"Connection", "keep-alive"},
        {"Cache-Control", "max-age=0"},
        {"Upgrade-Insecure-Requests", "1"},
        {"User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_3) AppleWebKit/537.36 (KHTML, like Gecko)"},
        {"Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8"},
        {"Accept-Encoding", "gzip, deflate, sdch"},
        {"Accept-Language", "zh-CN,zh;q=0.8"},
	};
	
	 static UASparser uasParser = null;
	 // 初始化uasParser对象
	 static {
	     try {
	        uasParser = new UASparser(OnlineUpdater.getVendoredInputStream());
	    } catch (IOException e) {
	         e.printStackTrace();
	      }
	 }
	 
    public static void main(String[] args) throws IOException, InterruptedException {
    	/*UserAgent userAgent = UserAgent.parseUserAgentString("Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/60.0.3112.101 Safari/537.36 ");  
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
    	System.out.println("类型："+userAgentInfo.getType());*/
    	//System.getProperties().setProperty("http.proxyHost", "222.73.68.144");  
    	//System.getProperties().setProperty("http.proxyPort", "8090");  
    	
    	System.setProperty("webdriver.chrome.driver", "C:\\Program Files (x86)\\Google\\Chrome\\Application\\chromedriver.exe");
    	ChromeOptions options = new ChromeOptions();

    	//options.addArguments("--proxy-server=127.0.0.1:8001");
    	//options.addArguments("headless");
    	options.addArguments("no-sandbox");
    	options.addArguments("--start-maximized");
    	WebDriver driver = new ChromeDriver(options);
    	driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    	driver.get("https://login.taobao.com/member/login.jhtml?spm=a21bo.2017.754894437.1.5af911d9bFsMVv&f=top&redirectURL=https%3A%2F%2Fwww.taobao.com%2F");
    	
        move(driver);
        
        WebElement submit = driver.findElement(By.xpath("//*[@id=\"J_SubmitStatic\"]"));
        submit.click();
        
        driver.findElement(By.xpath("//*[@id=\"q\"]")).sendKeys("帽子");
        driver.findElement(By.xpath("//*[@id=\"J_TSearchForm\"]/div[1]/button")).click();
        
        driver.findElement(By.xpath("//*[@id=\"J_Itemlist_TLink_522997440305\"]")).click();
        
        /*List<String> titleList = new ArrayList<>();
        
        List<WebElement> items = driver.findElement(By.xpath("//*[@id=\"mainsrp-itemlist\"]/div/div/div[1]")).findElements(By.className("item"));
        items.forEach(item->{
        	String title = item.findElement(By.className("title")).findElement(By.tagName("a")).getText();
        	titleList.add(title);
        });
        
        titleList.forEach(title->{
        	 System.out.println(title);
        });*/
    }
    public static void move(WebDriver driver) throws InterruptedException {
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
        WebElement password = driver.findElement(By.xpath("//*[@id=\"TPL_password_1\"]"));
        password.sendKeys("z5754784");
        WebElement form = driver.findElement(By.xpath("//*[@id=\"J_Form\"]"));
        form.click();
        
    	WebElement drop = driver.findElement(By.xpath("//*[@id=\"nc_1_n1z\"]"));
        Actions actions = new Actions(driver);
        actions.clickAndHold(drop).perform();
        Thread.sleep(200);
        actions.moveByOffset(drop.getLocation().getX()+800, drop.getLocation().getY()).perform();
    }
    public static void oldCollect() throws IOException {
    	String url = "https://s.taobao.com/search?q=%E5%B8%BD%E5%AD%90&style=grid";
    	Connection connection = Jsoup.connect(url);
        for (String[] head : HEADERS) {
            connection.header(head[0], head[1]);
        }
        connection.timeout(4000).followRedirects(true);
        Document doc = connection.get();//执行
        String html = doc.html();
        List<String> urlList = new ArrayList<>();
        
     // 使用正则表达式将本页所有商品的id提取出来（JSON数据串）
        Pattern pattern = Pattern.compile("\"auctionNids\":\\[.*?\\]");
        Matcher matcher = pattern.matcher(html);

        if (matcher.find()) {
            // matcher.group()返回匹配到的子字符串
            String string = matcher.group();

            int start = string.indexOf('[');
            String idStr = string.substring(start+1, string.length()-1);

            for (String idStars : idStr.split(",")) {
                String singleId = idStars.substring(1, idStars.length()-1);
                urlList.add("https://item.taobao.com/item.htm?id=" + singleId + "&style=grid");
            }
        }
        System.out.println("url："+urlList.size());
        List<GoodsEntity> goodsList = new ArrayList<>();
        //for (String id : urlList) {
        for (int i=0;i<1;i++) {
        	String id = "566377133352";
    		System.out.println(id);
    		connection = Jsoup.connect(getTBUrl(id));
    		for (String[] head : HEADERS) {
    			connection.header(head[0], head[1]);
    		}
    		connection.timeout(4000).followRedirects(true);
    		Document doc1 = connection.get();//执行
    		System.out.println(doc1.html());
    		Elements els = doc1.getElementById("detail").getElementsByClass("tm-fcs-panel");
    		if(els!=null && !els.isEmpty()) {
    			goodsList.add(tmInit(doc1, id));
    		}else {
    			goodsList.add(tbInit(doc1, id));
    		}
    	}
        System.out.println(new Gson().toJson(goodsList));
        System.out.println("goods："+goodsList.size());
    }
    public static GoodsEntity tmInit(Document doc,String id) {
    	GoodsEntity goods = new GoodsEntity();
    	goods.setId(id);
    	Element el = doc.getElementById("detail").getElementsByTag("h1").get(0);
    	String subjectName = doc.getElementById("shopExtra")
    			.getElementsByTag("a")
    			.get(0).child(0)
    			.html();
        goods.setName(el.html());
        goods.setSubjectName(subjectName);
        goods.setSource("天猫");
        goods.setUrl(getTBUrl(id));
        goods.setDetail(getDetail(doc));
        return goods;
    }
    
    public static String getDetail(Document doc) {
    	Elements attrs = doc.getElementById("attributes").getElementsByTag("ul").get(0).getElementsByTag("li");
    	StringBuffer detail = new StringBuffer("");
        for (Element ele : attrs) {
        	String str = ele.html().replace("&nbsp;", "");
        	detail.append(str+",");
		}
        return detail.toString();
    }
    
    public static GoodsEntity tbInit(Document doc,String id) {
    	GoodsEntity goods = new GoodsEntity();
    	goods.setId(id);
    	Element el = doc.getElementById("J_Title").child(0);
    	String subjectName = null;
    	Element shop = doc.getElementById("J_ShopInfo");
    	if(shop!=null) {
    		subjectName = shop.getElementsByClass("tb-shop-name").get(0).getElementsByTag("a").get(0).html();
    	}
    	
        goods.setName(el.html());
        goods.setSubjectName(subjectName);
        goods.setSource("淘宝");
        goods.setUrl(getTBUrl(id));
        goods.setDetail(getDetail(doc));
        return goods;
    	
    }
    
    public static String getTBUrl(String id) {
    	return "https://item.taobao.com/item.htm?id=" + id + "&style=grid";
    }
    
    public static void htmlUnitTest() throws FailingHttpStatusCodeException, MalformedURLException, IOException {
    	 LogFactory.getFactory().setAttribute("org.apache.commons.logging.Log", "org.apache.commons.logging.impl.NoOpLog");
    	 java.util.logging.Logger.getLogger("com.gargoylesoftware.htmlunit").setLevel(Level.OFF);
    	WebClient wc=new WebClient(BrowserVersion.EDGE);  
        wc.setJavaScriptTimeout(3000);  
        wc.getOptions().setUseInsecureSSL(true);//接受任何主机连接 无论是否有有效证书  
        wc.getOptions().setJavaScriptEnabled(true);//设置支持javascript脚本   
        wc.getOptions().setCssEnabled(false);//禁用css支持  
        wc.getOptions().setThrowExceptionOnScriptError(false);//js运行错误时不抛出异常  
        wc.getOptions().setTimeout(2000);//设置连接超时时间  
        wc.getOptions().setDoNotTrackEnabled(false);   
        
        HtmlPage page=wc.getPage("https://s.taobao.com/search?q=%E5%B8%BD%E5%AD%90&style=grid");  
        wc.setAjaxController(new NicelyResynchronizingAjaxController());//很重要，设置支持AJAX
        wc.waitForBackgroundJavaScript(2000);
        
        HtmlSpan span = (HtmlSpan) page.getElementById("TPL_username_1");
        HtmlTextInput username = (HtmlTextInput) page.getElementById("TPL_username_1");
        HtmlPasswordInput password = (HtmlPasswordInput) page.getElementById("TPL_password_1");
        HtmlButton submit = (HtmlButton) page.getElementById("J_SubmitStatic");
        username.setValueAttribute("w6180358w");
        password.setValueAttribute("z5754784");
        HtmlPage retPage = (HtmlPage) submit.click();
        wc.waitForBackgroundJavaScript(10000);
        String html = retPage.asXml();
        System.out.println(html);
      //获取cookie
		Set<Cookie> cookies = wc.getCookieManager().getCookies();
		Map<String, String> responseCookies = new HashMap<String, String>();
		for (Cookie c : cookies) {
			responseCookies.put(c.getName(), c.getValue());
			System.out.print(c.getName()+":"+c.getValue());
		}
    }
}
