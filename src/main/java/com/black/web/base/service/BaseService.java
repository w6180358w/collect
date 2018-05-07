package com.black.web.base.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.black.web.base.bean.BaseModel;
import com.black.web.base.bean.PageRequest;
import com.black.web.base.bean.QueryParam;

public interface BaseService {
	
	public <A> List<A> load(Class<A> clazz);

    public <A> A findById(Class<A> clazz,Serializable id);

    public <A> List<Map<String, Object>> findListPage(Class<A> clazz,PageRequest<? extends BaseModel> vo) throws Exception ;
    
    public <A> List<Map<String, Object>> selectListPage(Class<A> clazz,PageRequest<? extends BaseModel> vo) throws Exception ;

    public <A> Serializable save(A po) throws Exception;

    public <A> void saveOrUpdate(A po) throws Exception;

    public <A> void update(A po) throws Exception;

    public <A> void delete(Class<A> clazz,Serializable[] ids) throws Exception;
    
    public <A> void executeHql(String hql);

    public <A> List<A> findByProperty(Class<A> clazz,A po) throws Exception;
    
    public <A> List<A> findByProperty(Class<A> clazz,String code,Object value) throws Exception;
    
    public <A> A getByProperty(Class<A> clazz,String code,Object value) throws Exception;

    public <A> Map<String, Object> getTree(List<A> list) throws Exception;

    public <A> Map<String, Object> getTree(Class<A> clazz, List<Map<String, Object>> uL) throws Exception;
   
    public <A> List<QueryParam> before(Class<A> clazz,List<QueryParam> paramList) throws Exception;
    
    public <A> List<Map<String, Object>> after(List<Map<String, Object>> list) throws Exception;
}
