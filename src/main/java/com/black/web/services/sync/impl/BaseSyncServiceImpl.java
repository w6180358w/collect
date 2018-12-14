package com.black.web.services.sync.impl;

import java.util.List;

import com.black.collect.entity.GoodsEntity;
import com.black.web.services.sync.SyncService;

public abstract class BaseSyncServiceImpl implements SyncService{

	protected boolean shutdown;
	
	@Override
	public abstract void sync(List<GoodsEntity> data,String key,Integer count) throws Exception ;

	@Override
	public void shutdown() {
		this.shutdown = true;
	}

}
