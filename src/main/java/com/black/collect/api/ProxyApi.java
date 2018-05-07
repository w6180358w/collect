package com.black.collect.api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.black.collect.db.repositor.ProxyRepository;
import com.black.collect.entity.ProxyEntity;
import com.black.collect.utils.ProxyUtil;

/**
 * Created by hcdeng on 2017/6/29.
 */
@RestController
@EnableAutoConfiguration
@RequestMapping("/api")
public class ProxyApi {

    private static final String API_LIST =
            "get: get an usable proxy  "+
            "get_list: get proxy list  "+
            "delete?proxy=127.0.0.1:8080': delete an unable proxy " +
            "verify: verify the status of a proxy  ";

    @GetMapping("/")
    public Response<Object>index(){
        return new Response<>(true, "", API_LIST);
    }

    @GetMapping("/get")
    public Response<ProxyEntity> getProxy() {
        ProxyEntity proxy = ProxyRepository.getInstance().getRandomly();
        return new Response<>(proxy != null , proxy!=null ? "获取成功" : "获取失败", proxy);
    }


    @GetMapping("/get_list")
    public Response<List<ProxyEntity>>getProxys(@RequestParam(value="num", defaultValue="1") int num){
       List<ProxyEntity> list = ProxyRepository.getInstance().getList(num);
        return new Response<>(true, "获取成功", list);
    }

    @GetMapping("/count")
    public Response<Integer>getCount(){
        int count = ProxyRepository.getInstance().getCount();
        return new Response<>(true, "获取成功", count);
    }


    @GetMapping("verify")
    public Response<Map<String, Object>>verifyProxy(@RequestParam String proxy){
        if(proxy == null || !proxy.matches("\\d+\\.\\d+\\.\\d+\\.\\d+:\\d+"))
            return new Response<>(false, "输入参数不合法",null);

        boolean useful = ProxyUtil.verifyProxy(proxy);
        if(!useful)ProxyRepository.getInstance().deleteByKey(proxy);
		Map<String, Object> map = new HashMap<>();
        map.put("proxy", proxy);
        map.put("useful", useful);

        return new Response<>(true, "验证成功", map);
    }

    @GetMapping("/delete")
    public Response<String>deleteProxy(@RequestParam String proxy){
        if(proxy == null || !proxy.matches("\\d+\\.\\d+\\.\\d+\\.\\d+:\\d+"))
            return new Response<>(false, "输入参数不合法",null);

        ProxyRepository.getInstance().deleteByKey(proxy);
        return new Response<>(true, "删除成功", proxy);
    }
}
