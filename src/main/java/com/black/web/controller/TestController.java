package com.black.web.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.black.web.base.bean.PageResponse;
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
	@GetMapping("/load")
	@ResponseBody
	public String test() throws Exception {
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
        return new Gson().toJson(
        		new PageResponse<>(true, urlList));
    }
	
}
