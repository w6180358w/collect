package com.black.web.services.sync;

import java.util.List;

import com.black.collect.entity.GoodsEntity;

public interface SyncService {
	/**
	 * 开始同步数据
	 */
	public void sync(List<GoodsEntity> data,String key,Integer count) throws Exception;
	/**
	 * 停止同步方法
	 */
	public void shutdown();
	
}
