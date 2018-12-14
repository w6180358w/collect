package com.black.web.controller;

import java.io.IOException;
import java.lang.Thread.State;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.formula.functions.T;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.black.collect.entity.GoodsEntity;
import com.black.web.base.bean.PageResponse;
import com.black.web.base.enums.SyncEnum;
import com.black.web.base.service.POIService;
import com.black.web.models.bean.CollectBean;
import com.black.web.models.bean.ThreadBean;
import com.black.web.models.bean.ThreadProcessPool;
import com.black.web.models.po.User;
import com.google.gson.Gson;

@RestController
@RequestMapping("/test")
public class TestController{
	private static final String[][] HEADERS = new String[][]{
        {"Connection", "keep-alive"},
        {"Cache-Control", "max-age=0"},
        {"Upgrade-Insecure-Requests", "1"},
        {"User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_3) AppleWebKit/537.36 (KHTML, like Gecko)"},
        {"Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8"},
        {"Accept-Encoding", "gzip, deflate, sdch"},
        {"Accept-Language", "zh-CN,zh;q=0.8"},
	};
	@Autowired
	private POIService<T> poiService;
	
	@GetMapping("/load")
	public String test(HttpServletResponse response,String name) throws Exception {
    	System.getProperties().setProperty("http.proxyHost", "222.73.68.144");  
    	System.getProperties().setProperty("http.proxyPort", "8090");  
		String url = "https://s.taobao.com/search?q="+name+"&style=grid";
		System.out.println("搜索商品："+name);
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
                urlList.add(singleId);
            }
        }
        System.out.println("搜索到商品地址数量："+urlList.size());
        List goodsList = new ArrayList();
        for (String id : urlList) {
    		connection = Jsoup.connect(getTBUrl(id));
    		for (String[] head : HEADERS) {
    			connection.header(head[0], head[1]);
    		}
    		connection.timeout(4000).followRedirects(true);
    		Document doc1 = connection.get();//执行
    		Elements els = doc1.getElementById("detail").getElementsByClass("tm-fcs-panel");
    		if(els!=null && !els.isEmpty()) {
    			goodsList.add(tmInit(doc1, id));
    		}else {
    			goodsList.add(tbInit(doc1, id));
    		}
    	}
        System.out.println("处理后数据："+new Gson().toJson(goodsList));
        System.out.println("处理后商品数量："+goodsList.size());
        response.setContentType("application/x-download");
        response.setHeader("Content-Disposition", "attachment; filename="+name+".xls");  
        String[] dataFields = new String[]{"id","name","source","detail","url"};
		String[] columns = new String[]{"商品ID","商品名称","来源","商品详情","商品url"};
        try {
			this.poiService.exportExcel(goodsList, dataFields, response.getOutputStream(), columns, "yyyy-MM-dd");
			response.flushBuffer();
        } catch (IOException e) {
			e.printStackTrace();
		}
		return null;
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
    	
        goods.setName(el.ownText());
        goods.setSubjectName(subjectName);
        goods.setSource("淘宝");
        goods.setUrl(getTBUrl(id));
        goods.setDetail(getDetail(doc));
        return goods;
    	
    }
    
    public static String getTBUrl(String id) {
    	return "https://item.taobao.com/item.htm?id=" + id + "&style=grid";
    }
    
    @PostMapping("/collect")
	@ResponseBody
	public String collect(@RequestBody CollectBean bean) throws Exception {
    	if(bean.getCount()==null && bean.getTime()==null) {
    		bean.setCount(1);
    	}
    	ThreadBean tbean = new ThreadBean(bean);
    	ThreadProcessPool.process.put(tbean.getName(), tbean);
    	tbean.start();
        return new Gson().toJson(
				new PageResponse<Object>(true,"任务已创建",tbean.getName()));
    }
    
    @GetMapping("/process/{id}")
   	@ResponseBody
   	public String process(@PathVariable("id") String id) throws Exception {
    	ThreadBean bean = ThreadProcessPool.process.get(id);
    	if(bean!=null) {
    		if(bean.getState().compareTo(State.TERMINATED)==0) {
    			return new Gson().toJson(new PageResponse<Object>(true,"","100.00"));
    		}
    		System.out.println(bean.getState().name());
    		return bean.getProcess();
    	}
    	return new Gson().toJson(new PageResponse<Object>(false,"编号为【"+id+"】的任务未创建或已结束"));
    }
}
