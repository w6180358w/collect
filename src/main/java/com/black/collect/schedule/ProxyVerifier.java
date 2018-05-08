package com.black.collect.schedule;

import java.util.Collection;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.black.collect.db.repositor.ProxyRepository;
import com.black.collect.entity.GoodsEntity;
import com.black.collect.entity.ProxyEntity;
import com.black.collect.utils.ProxyUtil;

/**
 * Created by hcdeng on 17-7-3.
 */
public class ProxyVerifier {

    private static final int THREAD_NUMS = 7;

    private static final ExecutorService EXEC = Executors.newFixedThreadPool(THREAD_NUMS);
    //新拉取代理IP队列
    private static final BlockingQueue<ProxyEntity> FETCHED_PROXYS = new LinkedBlockingQueue<>(100000);
    //已缓存代理IP队列
    private static final BlockingQueue<ProxyEntity> CACHED_PROXYS = new LinkedBlockingQueue<>(10000);
    //获取到的数据队列
    private static final BlockingQueue<GoodsEntity> DATA = new LinkedBlockingQueue<>(10000);
    //获取数据的url队列
    private static final BlockingQueue<ProxyEntity> DATA_PROXYS = new LinkedBlockingQueue<>(10000);

    private static final Logger logger = LoggerFactory.getLogger(ProxyVerifier.class);

    private static boolean running = true;
    
    private static boolean complete = false;

    static {start();}
    //更新新拉取代理IP队列
    public static void verify(ProxyEntity proxy) {
        try {
            FETCHED_PROXYS.put(proxy);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    //更新新拉取代理IP队列
    public static void verifyAll(Collection<ProxyEntity> proxys) {
        for (ProxyEntity p : proxys)
            verify(p);
    }
    //更新已缓存代理IP队列
    public static void refresh(ProxyEntity proxy) {
        try {
            CACHED_PROXYS.put(proxy);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    //更新已缓存代理IP队列
    public static void refreshAll(Collection<ProxyEntity> proxys) {
        for (ProxyEntity p : proxys)
            refresh(p);
    }
    //更新拉取到的数据队列
    public static void data(GoodsEntity goods) {
        try {
        	DATA.put(goods);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    //更新拉取到的数据队列
    public static void dataAll(Collection<GoodsEntity> goods) {
        for (GoodsEntity p : goods)
        	data(p);
    }
    //更新获取数据的url队列
    public static void url(ProxyEntity proxy) {
        try {
        	DATA_PROXYS.put(proxy);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    //更新获取数据的url队列
    public static void urlAll(Collection<ProxyEntity> proxys) {
        for (ProxyEntity p : proxys)
        	url(p);
    }
    /**
     * 启动两个任务,一个验证redis已缓存的代理可用性,一个验证新拉取的代理可用性
     */
    private static void start() {
        startVerify(CACHED_PROXYS, "CACHED_PROXYS");
        startVerify(FETCHED_PROXYS, "FETCHED_PROXYS");
    }

    public static void stop(){running = false;}

    private static void startVerify(final BlockingQueue<ProxyEntity> proxys, final String pName) {
        EXEC.execute(() -> {
            while (running) {
                try {
                    ProxyEntity proxy = proxys.take();
                    logger.info("verifying : " + proxy.getIp()+":"+proxy.getPort()+" in "+pName+", remaining: "+proxys.size());

                    boolean useful = ProxyUtil.verifyProxy(proxy);
                    if (useful)
                        ProxyRepository.getInstance().save(proxy);
                    else ProxyRepository.getInstance().delete(proxy);
                } catch (InterruptedException e) {
                    logger.warn("exception when verifying proxy: " + e.getMessage());
                }
            }
        });
    }
    //监听队列发送邮件
    private static void sendMail(final BlockingQueue<ProxyEntity> proxys, final String pName) {
        EXEC.execute(() -> {
            while (running) {
                try {
                    if(complete){
                    	logger.info("send mail");
                    }
                } catch (Exception e) {
                    logger.warn("exception when verifying proxy: " + e.getMessage());
                }
            }
        });
    }
}
