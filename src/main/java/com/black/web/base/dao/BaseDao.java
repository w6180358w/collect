package com.black.web.base.dao;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;

import com.black.web.base.bean.QueryParam;

public interface BaseDao {
	public Session getSession();
	/**
	 * 查询所有
	 * @return
	 */
	public <A> List<A> load(Class<A> clazz);
	public <A> long getCount(Class<A> clazz);
	
	/**
	 * 根据ID查询
	 * @param id
	 * @return
	 */
	public <A> A findById(Class<A> clazz,Serializable id);
	/* 条件查询 */
	public <A> List<A> findByProperty(Class<A> clazz,String code,Object value);
	public <A> List<A> findList(Class<A> clazz,boolean isLike,List<QueryParam> params) throws Exception;
	public <A> A getByProperty(Class<A> clazz, String code, Object value) throws Exception ;
	/**
	 * 分页查询
	 * @param isLike
	 * @param params
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	public <A> List<A> findListPage(Class<A> clazz,boolean isLike,List<QueryParam> params, int pageNumber,int pageSize) throws Exception;
	public <A> long findSizePage(Class<A> clazz,boolean isLike,List<QueryParam> params) throws Exception;

	/**
	 * 保存
	 * @param t
	 * @return
	 */
	public <A> Serializable save(A t) throws Exception ;
	
	/**
	 * 保存
	 * @param t
	 * @return
	 */
	public <A> void save(Collection<A> collections) throws Exception ;

	/**
	 * 更新
	 * @param t
	 */
	public <A> void update(A t) throws Exception ;
	/**
	 * 保存
	 * @param t
	 * @return
	 */
	public <A> void update(Collection<A> collections) throws Exception ;
	
	/**
	 * 保存或更新
	 * @param 
	 */
	public <A> void saveOrUpdate(A t) throws Exception ;
	public <A> void saveOrUpdateAll(Collection<A> collections) throws Exception ;
	
	/**
	 * 删除
	 * @param 
	 */
	public <A> void delete(Class<A> clazz,Serializable[] t) throws Exception ;
	public <A> void delete(A t) throws Exception ;
	public <A> void delete(Collection<A> collections) throws Exception ;
	public <A> int deleteAll(Class<A> clazz) throws Exception ;
	public <A> void deleteHql(A t) throws Exception ;
	
	/*
	 * 根据SQL查询
	 */
	public <A> List<?> sql_List(String sql, String[] strings, Class<?> clazz);
	
	/*
	 * 执行hql
	 */
	public int hql_Execute(String hql);
	public <A> List<A> hql_List(String hql);
	public <A> List<A> hql_List(String hql,Object param);
	public <A> List<A> hql_List(String hql,Object[] params);
	public <A> List<A> hql_List(String hql,Map<String,Object> param);
	public <A> List<A> hql_ListPage(String hql, int pageNumber,int pageSize);
	public <A> List<A> hql_ListPage(String hql,Object param, int pageNumber,int pageSize);
	public <A> List<A> hql_ListPage(String hql,Object[] params, int pageNumber,int pageSize);
	public <A> List<A> hql_ListPage(String hql,Map<String,Object> params, int pageNumber,int pageSize);
	
	public List<?> hql_ListObject(String hql);
	public List<?> hql_ListObject(String hql,Object param);
	public List<?> hql_ListObject(String hql,Object[] params);
	public List<?> hql_ListObject(String hql,Map<String,Object> param);
	public List<?> hql_ListObjectPage(String hql, int pageNumber,int pageSize);
	public List<?> hql_ListObjectPage(String hql,Object param, int pageNumber,int pageSize);
	public List<?> hql_ListObjectPage(String hql,Object[] params, int pageNumber,int pageSize);
	public List<?> hql_ListObjectPage(String hql,Map<String,Object> params, int pageNumber,int pageSize);
	
	public long hql_Count(String hql);
	public long hql_Count(String hql,Object param);
	public long hql_Count(String hql,Object[] params);
	public Double hql_Calculate(String hql);
	
}
