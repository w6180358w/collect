package com.black.collect.db.repositor;

import com.black.collect.entity.ProxyEntity;
import com.black.web.base.utils.CommonUtil;
import com.google.common.base.Strings;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.*;

/**
 * Created by hcdeng on 17-7-3.
 */
public  class ProxyRepository {

    private static  ProxyRepository REPOSITORY = null;

    public static ProxyRepository getInstance(){
    	if(REPOSITORY==null) {
    		REPOSITORY = new ProxyRepository();
    	}
    	return REPOSITORY;
    }

    @SuppressWarnings("unchecked")
	private ProxyRepository(){
    	this.redisTemplate = (RedisTemplate<String, ProxyEntity>) CommonUtil.getApplicationContext().getBean("redisTemplate");
        this.deleteAll();
    }

    private RedisTemplate<String, ProxyEntity> redisTemplate;

    public void save(ProxyEntity proxy) {
        proxy.setUsable(true);
        proxy.setLastValidateTime(new Date());
        redisTemplate.opsForValue().set(getKey(proxy), proxy);
    }


    private ProxyEntity getByKey(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    public ProxyEntity get(String ip, int port){
        return getByKey(ip + ":" +port);
    }

    public ProxyEntity getRandomly(){
        String key = redisTemplate.randomKey();
        return Strings.isNullOrEmpty(key) ? null : getByKey(key);
    }

    public List<ProxyEntity> getList(int num){
        List<ProxyEntity> proxys = new ArrayList<>();

        Set<String> keys = redisTemplate.keys("*");
        Iterator<String> it = keys.iterator();

        if(num < 0)num = keys.size();
        while(it.hasNext() && num-- > 0){
            proxys.add(getByKey(it.next()));
        }

        return proxys;
    }

    public List<ProxyEntity> getAll() {
        return  getList(-1);
    }

    public int getCount(){
        return redisTemplate.keys("*").size();
    }

    public void delete(ProxyEntity b) {
        deleteByKey(getKey(b));
    }

    public void delete(String ip, int port) {
        deleteByKey(ip+":"+port);
    }

    public void deleteByKey(String key) {
        if(!Strings.isNullOrEmpty(key))
            redisTemplate.opsForValue().getOperations().delete(key);
    }

    public void deleteAll() {
        Set<String> keys = redisTemplate.keys("*");
        Iterator<String> it = keys.iterator();

        while(it.hasNext()){
            redisTemplate.opsForValue().getOperations().delete(it.next());
        }
    }


    private static String getKey(ProxyEntity proxy){
        return proxy.getIp()+":"+proxy.getPort();
    }

}
