package com.black.web.models.bean;

import java.util.ArrayList;
import java.util.List;

import com.black.collect.entity.GoodsEntity;
import com.black.web.base.enums.SyncEnum;
import com.black.web.base.exception.RaysException;
import com.black.web.services.sync.SyncService;

public class ThreadBean extends Thread{
	
	private String s;				//搜索关键字
	private Integer count = 100;	//采集数量
	private Integer time = 10;		//采集时间(分钟)
	private Long startTime = 0l;	//采集开始时间
	private String mail;			//采集成功后要发送的邮箱
	private SyncService service;			//service类型
	private Long endTime = 0l;		//结束时间
	
	private List<GoodsEntity> data = new ArrayList<>();
	
	public ThreadBean(String s,Integer count,Integer time,String mail,SyncEnum type) throws RaysException {
		this.s = s;
		this.count = count;
		this.time = time;
		this.mail = mail;
		try {
			this.service = (SyncService) Class.forName(type.getClassName()).newInstance();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RaysException("同步类型不存在!");
		}
	}
	//采集数据线程
	@Override
	public void run() {
		endTime = startTime+time*60*1000;
		ThreadBean that = this;
		//开启时间监听线程
		new Thread(()->{
			//如果当前时间大于停止时间  调用shutdown方法
			while(System.currentTimeMillis() <= endTime) {
				System.out.println(endTime);
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			System.out.println("----------------关闭采集线程------------------");
			//采集服务的shutdown方法  shutdown逻辑自己实现
			that.service.shutdown();
		}).start();
		
		//主线程开始采集程序
		try {
			this.service.sync(data);
		} catch (Exception e) {
			//结束时间控制为0  为了停止监听线程
			System.out.println("采集报错,设置监听时间为0");
			endTime = 0l;
			e.printStackTrace();
		}
		
		//发送邮件（如果采集过程报错则添加内容  不报错带附件）
		data.forEach(d->{
			System.out.println(d.toString());
		});
	}

	@Override
	public synchronized void start() {
		this.startTime = System.currentTimeMillis();
		super.start();
	}
	
	public String getS() {
		return s;
	}

	public void setS(String s) {
		this.s = s;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public Integer getTime() {
		return time;
	}

	public void setTime(Integer time) {
		this.time = time;
	}

	public List<GoodsEntity> getData() {
		return data;
	}

	public void setData(List<GoodsEntity> data) {
		this.data = data;
	}
	
}
