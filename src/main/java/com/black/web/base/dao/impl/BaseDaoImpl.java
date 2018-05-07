package com.black.web.base.dao.impl;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Id;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Junction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import com.black.web.Logger.Logger;
import com.black.web.base.annotation.Editable;
import com.black.web.base.annotation.FieldTag;
import com.black.web.base.annotation.LogicDelete;
import com.black.web.base.annotation.RaysModel;
import com.black.web.base.annotation.ForeignDelete;
import com.black.web.base.annotation.Unique;
import com.black.web.base.bean.BaseModel;
import com.black.web.base.bean.BasePo;
import com.black.web.base.bean.QueryParam;
import com.black.web.base.dao.BaseDao;
import com.black.web.base.enums.StatusEnum;
import com.black.web.base.exception.FieldValidateException;
import com.black.web.base.exception.IllegalException;
import com.black.web.base.exception.RaysException;
import com.black.web.base.msg.RaysMessage;
import com.black.web.base.utils.CommonUtil;
import com.black.web.base.utils.DateUtil;
import com.black.web.base.utils.StringUtils;
import com.black.web.context.RaysContext;
import com.black.web.models.po.User;

@Repository
public class BaseDaoImpl implements BaseDao{
private Gson gson = new Gson();
	
	@Autowired
	private SessionFactory sessionFactory;
	@Override
	public Session getSession(){
		return sessionFactory.getCurrentSession();
	}
	
	@Override
	public <A> List<A> load(Class<A> clazz) {
	    return findAllInOrder(clazz,null);
	}
	@Override
	public <A> long getCount(Class<A> clazz) {
		String hql = "SELECT COUNT(*) FROM " + clazz.getName();
		return ((Long)hql_List(hql).get(0)).longValue();
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public <A> List<A> findAllInOrder(Class<?> clazz,String orderHql){
		if (clazz == null)
			return new ArrayList();
	    String hql = "FROM " + clazz.getName();
	    if(orderHql!=null && !"".equals(orderHql))
	    	hql += " ORDER BY "+orderHql;
	    return (List<A>) getSession().createQuery(hql).list();
	}

	@Override
	public <A> A findById(Class<A> clazz,Serializable id) {
		return id == null ? null : (A)getSession().get(clazz, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <A> List<A> hql_List(String hql) {
		return getSession().createQuery(hql).list();
	}
	@SuppressWarnings("unchecked")
	@Override
	public <A> List<A> hql_List(String hql, Object param) {
		Query query = getSession().createQuery(hql);
		query.setParameter(0, param);
		return  query.list();
	}
	@SuppressWarnings("unchecked")
	@Override
	public <A> List<A> hql_List(String hql, Object[] params) {
		return getParamsQuery(hql, params).list();
	}
	@SuppressWarnings("unchecked")
	@Override
	public <A> List<A> hql_List(String hql, Map<String, Object> param) {
		return getParamsQuery(hql, param).list();
	}
	@SuppressWarnings("unchecked")
	@Override
	public <A> List<A> hql_ListPage(String hql, int pageNumber,int pageSize) {
		Query query = getSession().createQuery(hql);
        query.setFirstResult(pageSize * (pageNumber - 1));
        query.setMaxResults(pageSize);
		return query.list();
	}
	@SuppressWarnings("unchecked")
	@Override
	public <A> List<A> hql_ListPage(String hql,Object param, int pageNumber,int pageSize) {
		Query query = getSession().createQuery(hql);
		query.setParameter(0, param);
        query.setFirstResult(pageSize * (pageNumber - 1));
        query.setMaxResults(pageSize);
		return query.list();
	}
	@SuppressWarnings("unchecked")
	@Override
	public <A> List<A> hql_ListPage(String hql,Object[] params, int pageNumber,int pageSize) {
		Query query = getParamsQuery(hql, params);
        query.setFirstResult(pageSize * (pageNumber - 1));
        query.setMaxResults(pageSize);
		return query.list();
	}
	@SuppressWarnings("unchecked")
	@Override
	public <A> List<A> hql_ListPage(String hql, Map<String, Object> params, int pageNumber, int pageSize) {
		Query query = getParamsQuery(hql, params);
        query.setFirstResult(pageSize * (pageNumber - 1));
        query.setMaxResults(pageSize);
		return query.list();
	}
	@Override
	public List<?> hql_ListObject(String hql) {
		return getSession().createQuery(hql).list();
	}
	@Override
	public List<?> hql_ListObject(String hql, Object param) {
		Query query = getSession().createQuery(hql);
		query.setParameter(0, param);
		return  query.list();
	}
	@Override
	public List<?> hql_ListObject(String hql, Object[] params) {
		return getParamsQuery(hql, params).list();
	}
	@Override
	public List<?> hql_ListObject(String hql, Map<String, Object> param) {
		return getParamsQuery(hql, param).list();
	}
	@Override
	public List<?> hql_ListObjectPage(String hql, int pageNumber,int pageSize) {
		Query query = getSession().createQuery(hql);
        query.setFirstResult(pageSize * (pageNumber - 1));
        query.setMaxResults(pageSize);
		return query.list();
	}
	@Override
	public List<?> hql_ListObjectPage(String hql,Object param, int pageNumber,int pageSize) {
		Query query = getSession().createQuery(hql);
		query.setParameter(0, param);
        query.setFirstResult(pageSize * (pageNumber - 1));
        query.setMaxResults(pageSize);
		return query.list();
	}
	@Override
	public List<?> hql_ListObjectPage(String hql,Object[] params, int pageNumber,int pageSize) {
		Query query = getParamsQuery(hql, params);
        query.setFirstResult(pageSize * (pageNumber - 1));
        query.setMaxResults(pageSize);
		return query.list();
	}
	@Override
	public List<?> hql_ListObjectPage(String hql, Map<String, Object> params, int pageNumber, int pageSize) {
		Query query = getParamsQuery(hql, params);
        query.setFirstResult(pageSize * (pageNumber - 1));
        query.setMaxResults(pageSize);
		return query.list();
	}
	@Override
	public long hql_Count(String hql) {
	    return hql_List(hql).size();
	}
	
	@Override
	public long hql_Count(String hql, Object param) {
		Query query = getSession().createQuery(hql);
		query.setParameter(0, param);
		List<?> list = query.list();
		if(CommonUtil.listNotNull(list)) {
			return list.size();
		}
		return 0;
	}

	@Override
	public long hql_Count(String hql, Object[] params) {
		List<?> list = getParamsQuery(hql, params).list();
		if(CommonUtil.listNotNull(list)) {
			return list.size();
		}
		return 0;
	}
	
	@Override
	public Double hql_Calculate(String hql) {
		List<?> list = hql_List(hql);
		if(list.size()!=1){
			return null;
		}
	    return Double.valueOf(list.get(0).toString());
	}
	@SuppressWarnings({ "rawtypes"})
	@Override
	public List<?> sql_List(String sql,String[] strings,Class clazz) {
		SQLQuery sqlQuery = getSession().createSQLQuery(sql); 
        sqlQuery.setResultTransformer(Transformers.aliasToBean(clazz));
        for (String a : strings) {
        	sqlQuery.addScalar(a);
		}
        List<?> list = sqlQuery.list();
		return list;
	}
	private Query getParamsQuery(String hql,Object[] params){
		Query query = getSession().createQuery(hql);
		for (int i=0;i<params.length;i++) {
			query.setParameter(i, params[i]);
		}
		return query;
	}
	private Query getParamsQuery(String hql,Map<String,Object> param){
		Query query = getSession().createQuery(hql);
		if(param != null && param.size() >0) {
			for(String property : param.keySet()){
			     Object obj = param.get(property);  
                //这里考虑传入的参数是什么类型，不同类型使用的方法不同  
                if(obj instanceof Collection<?>){  
                	query.setParameterList(property, (Collection<?>)obj);  
                }else if(obj instanceof Object[]){  
                	query.setParameterList(property, (Object[])obj);  
                }else{  
                	query.setParameter(property, obj);  
                }  
			}
		}
		return query;
	}
	/**
	 * 字段验证
	 * @param t
	 * @throws Exception
	 */
	private <A> void fieldValid(A t,Boolean isEdit) throws Exception{
		Field[] fields = t.getClass().getDeclaredFields();
		//首先清除t在hibernate中的缓存   否则验证可能会失效(验证时会查询数据库,如果主键相同hibernate会从缓存中取数据,验证会失效)
		this.getSession().evict(t);
		for (Field field : fields) {
			field.setAccessible(true);  
			if(isEdit){
				if(field.getAnnotation(Id.class)!=null && field.get(t)==null){
					throw new RaysException(RaysMessage.ILLEGAL_ID_NOT_NULL);
				}
				editableValid(field, t);
			}
			uniqueValid(field,t);
			field.setAccessible(false);  
		}
	}
	/**
	 * 字段唯一性验证
	 * @param field
	 * @param t
	 * @throws Exception
	 */
	private <A> void uniqueValid(Field field,A t) throws Exception{
		Unique unique = field.getAnnotation(Unique.class);
		//判断是否启用字段唯一性验证
		if(unique!=null && unique.value()){
			Class<? extends Object> clazz = t.getClass();
			String className = StringUtils.getClassName(clazz);
			//更新后的字段值
			Object value = field.get(t);
			//如果当前值为忽略值  退出不验证
			String[] ignore = unique.ignore();
			for (String str : ignore) {
				if((value+"").equals(str+"")){
					return;
				}
			}
			
			//根据更新后的值封装查询条件查询数据库
			List<QueryParam> qpList = new ArrayList<QueryParam>();
			qpList.add(new QueryParam(field.getName(),value,"="));
			
			Map<String,Serializable> map = getId(t);
			
			if(map.get("value")!=null){
				qpList.add(new QueryParam(map.get("field").toString(),map.get("value"),"!="));
			}
			
			String[] fields = unique.fields();
			Map<String,String> fieldsMap = null;
			//添加筛选条件
			if(fields.length>0){
				fieldsMap = new HashMap<String,String>();
				for (String fieldStr : fields) {
					Field f = null;
					try {
						f = t.getClass().getDeclaredField(fieldStr);
					} catch (Exception e) {
						e.printStackTrace();
						throw new FieldValidateException(RaysMessage.getUnKonwFieldMsg(className, fieldStr));
					}
					f.setAccessible(true);
					Object v= f.get(t);
					qpList.add(new QueryParam(f.getName(),v,"="));
					fieldsMap.put(f.getName(), v==null?null:v.toString());
					f.setAccessible(false);
				}
			}
			//如果存在数据抛异常
			List<? extends Object> list = this.findList(clazz, false, qpList);
			//清空session中刚查出来的数据缓存  否则更新时会有冲突
			for (Object obj : list) {
				this.getSession().evict(obj);
			}
			if(list.size()>0){
				throw new FieldValidateException(RaysMessage.getUniqueMsg(unique, value.toString(), className, field.getName(), fieldsMap));
			}
		}
	}
	/**
	 * 是否可修改验证
	 * @param field
	 * @param t
	 * @throws Exception
	 */
	private <A> void editableValid(Field field,A t) throws Exception{
		Editable editable = field.getAnnotation(Editable.class);
		//判断是否要添加是否可修改验证
		if(editable!=null && !editable.editable()){
			//获取主键的字段名和值
			Map<String,Serializable> map = getId(t);
			//判断如果主键为空则退出(表示该记录为新增 不需要进行是否可修改验证)
			if(map.get("value")==null){
				return;
			}
			//获取数据库中该实体
			Object ob = this.findById(t.getClass(), map.get("value"));
			//清空session中刚查出来的数据缓存  否则更新时会有冲突
			this.getSession().evict(ob);
			Object nv = field.get(t),ov = field.get(ob);
			//判断数据库中字段和更新后的字段是否相同  相同则return  不相同抛出异常
			if((nv==null && ov==null) || (nv!=null && nv.equals(ov)) || (ov!=null && ov.equals(nv))){
				return;
			}
			throw new FieldValidateException(RaysMessage.getEditableFieldMsg(StringUtils.getClassName(t.getClass()), field.getName()));
		}
	}
	/**
	 * 根据实体获取ID的map   field为变量名  value为变量值
	 * @param t
	 * @return
	 * @throws Exception
	 */
	private <A> Map<String,Serializable> getId(A t) throws Exception{
		Map<String,Serializable> map = new HashMap<String,Serializable>();
		Field[] fields = t.getClass().getDeclaredFields();
		for (Field field : fields) {
			Id ida = field.getAnnotation(Id.class);
			if(ida!=null){
				map.put("field", field.getName());
				field.setAccessible(true);
				map.put("value", field.get(t)==null?null:(Serializable)(field.get(t)));
				field.setAccessible(false);
			}
		}
		return map;
	}
	
	private <A> Field getIdField(Class<A> clazz){
		Field result = null;
		Field[] fields = clazz.getDeclaredFields();
		for (Field field : fields) {
			Id ida = field.getAnnotation(Id.class);
			if(ida!=null){
				result = field;
				break;
			}
		}
		return result;
	}
	
	@Override
	public <A> Serializable save(A t) throws Exception {
		RaysModel ra = t.getClass().getAnnotation(RaysModel.class);
		if(ra!=null){
			//判断是否为实体类
			if(ra.entity()){
				User user = RaysContext.user.get();
				BasePo po = BasePo.class.cast(t);
				if(StringUtils.isBlank(po.getStatus())){
					po.setStatus(StatusEnum.ENABLED.name());
				}
				po.setIsDelete(false);
				po.setCreattime(DateUtil.getCurrentTime());
				po.setUpdatetime(DateUtil.getCurrentTime());
				po.setCreateUser(user==null?null:user.getUcode());
				po.setUpdateUser(user==null?null:user.getUcode());
			}
			fieldValid(t,false);
		}
		return getSession().save(t);
	}
	@Override
	public <A> void save(Collection<A> collections) throws Exception {
		if(collections==null || collections.size()<1)
			return;
		for (A a : collections) {
			this.save(a);
		}
	}

	@Override
	public <A> void update(A t) throws Exception {
		if (t == null)
		      return;
		RaysModel ra = t.getClass().getAnnotation(RaysModel.class);
		if(ra!=null){
			//判断是否为实体  更新时间,用户等字段
			if(ra.entity()){
				User user = RaysContext.user.get();
				BasePo po = BasePo.class.cast(t);
				po.setUpdatetime(DateUtil.getCurrentTime());
				po.setUpdateUser(user==null?null:user.getUcode());
			}
			//判断是否可修改
			if(!ra.editable()){
				throw new FieldValidateException(RaysMessage.getEditableModelMsg(StringUtils.getClassName(t.getClass())));
			}
			//字段验证: 唯一性验证,字段是否可修改验证
			fieldValid(t,true);
		}
		getSession().update(t);
	}
	
	@Override
	public <A> void update(Collection<A> collections) throws Exception {
		if(collections==null || collections.size()<1)
			return;
		for (A a : collections) {
			this.update(a);
		}
	}
	
	@Override
	public <A> void saveOrUpdate(A t) throws Exception {
		if (t == null)
		      return;
		//获取主键的字段名和值
		Map<String,Serializable> map = getId(t);
		//判断如果主键为空则退出(表示该记录为新增 不需要进行是否可修改验证)
		if(map.get("value")==null){
			this.save(t);
		}else{
			this.update(t);
		}
	}

	@Override
	public <A> void saveOrUpdateAll(Collection<A> collections) throws Exception {
		if(collections==null || collections.size()<1)
			return;
		for (A t : collections) {
			saveOrUpdate(t);
		}
	}
	
	@Override
	public <A> void delete(Class<A> clazz,Serializable[] t) throws Exception {
		for (Serializable l : t) {
			delete(findById(clazz,l));
		}
	}
	
	@Override
	public <A> void delete(Collection<A> collections) throws Exception {
		if(collections==null || collections.size()<1)
			return;
		for (A t : collections) {
			delete(t);
		}
	}

	@Override
	public <A> void delete(A t) throws Exception {
		if(t==null)
			return;
		RaysModel ra = t.getClass().getAnnotation(RaysModel.class);
		if(ra!=null){
			//级联删除
			if(ra.relevanceDelete()){
				Field[] fields = t.getClass().getDeclaredFields();
				for (Field field : fields) {
					//根据注解定义的类  字段  查询数据  调用delete方法删除(递归)
					ForeignDelete rel = field.getAnnotation(ForeignDelete.class);
					if(rel!=null && rel.fieldTag()!=null && rel.fieldTag().length>0){
						field.setAccessible(true);
						FieldTag[] ft = rel.fieldTag();
 						for (int i=0;i<ft.length;i++) {
 							Class<? extends BaseModel> class1 = ft[i].clazz();
 							String f = ft[i].field();
							//组装查询条件  查询被base查询过滤后的数据(例如不包括已被逻辑删除的数据)
							List<QueryParam> qpList = new ArrayList<QueryParam>();
							qpList.add(new QueryParam(f,field.get(t),"="));
							List<? extends BaseModel> list = this.findList(class1, false, qpList);
							Logger.info("级联删除:"+class1+"."+f+"="+field.get(t)+",个数:"+(list==null?0:list.size()));
							if(list!=null && list.size()>0){
								this.delete(list);
							}
						}
						field.setAccessible(false);
					}
				}
			}
			//如果为实体类  判断是否为逻辑删除
			if(ra.entity()){
				if(ra.deleteLogic()){
					User user = RaysContext.user.get();
					BasePo po = BasePo.class.cast(t);
					po.setIsDelete(true);
					po.setUpdateUser(user==null?null:user.getUcode());
					po.setUpdatetime(DateUtil.getCurrentTime());
					getSession().update(t);
					return;
				}
			}
		}
		//使用hql进行删除
		//在使用hibernate原生this.getSession().delete(t)时必须保证t对象为持久化对象
		//而使用级联删除时会递归调用改方法,t对象有可能会被提前删除,即变成脱管状态
		//从而会报HibernateOptimisticLockingFailureException错误
		deleteHql(t);
	}
	/**
	 * 真正的删除方法  使用hql删除
	 * @param t
	 * @throws Exception
	 */
	@Override
	public <A> void deleteHql(A t) throws Exception{
		Field field = getIdField(t.getClass());
		field.setAccessible(true);
		String hql = "delete from "+
				StringUtils.getClassName(t.getClass())+
				" where "+field.getName()+" = ?";
		this.getSession().createQuery(hql).
			setParameter(0, field.get(t)).executeUpdate();
		field.setAccessible(false);
	}
	
	@Override
	public <A> int deleteAll(Class<A> clazz) {
		String hql = "DELETE FROM " + clazz.getName();
	    return getSession().createQuery(hql).executeUpdate();
	}

	@Override
	public int hql_Execute(String hql) {
		return getSession().createQuery(hql).executeUpdate();
	}

	@SuppressWarnings("unchecked")
	@Override
	public <A> List<A> findByProperty(Class<A> clazz,String code,Object value) {
		String hql = "FROM " + clazz.getName() + " WHERE "+code+"=?";
		return (List<A>) getSession().createQuery(hql).setParameter(0, value).list();
	}
	
	@SuppressWarnings("unchecked")
	public <A> List<A> findList(Class<A> clazz,boolean isLike,List<QueryParam> params) throws Exception {
		if (params == null)
			params = new ArrayList<QueryParam>();
		DetachedCriteria criteria = getCriteria(isLike,clazz, params);
		return (List<A>) criteria.getExecutableCriteria(getSession()).list();
	}
	
	public <A> long findSizePage(Class<A> clazz,boolean isLike,List<QueryParam> params) throws Exception {
		if (params == null)
			params = new ArrayList<QueryParam>();
		DetachedCriteria criteria = getCriteria(isLike,clazz, params);
		ProjectionList projections = Projections.projectionList();
		projections.add(Projections.rowCount());
		criteria.setProjection(projections);
		return (Long) criteria.getExecutableCriteria(getSession()).list().get(0);
	}
	
	@SuppressWarnings("unchecked")
	public <A> List<A> findListPage(Class<A> clazz,boolean isLike,List<QueryParam> params, int pageNumber,int pageSize) throws Exception {
		final int firstResult = pageSize * (pageNumber - 1);
		if (params == null)
			params = new ArrayList<QueryParam>();
		DetachedCriteria criteria = getCriteria(isLike,clazz, params);
		return (List<A>)criteria.getExecutableCriteria(getSession())
				.setFirstResult(firstResult)
				.setMaxResults(pageSize).list();

	}
	/**
	 * 获取查询条件(包括通用查询条件和前台传来的查询条件)
	 * @param isLike	是否为or查询
	 * @param clazz		实体类
	 * @param params	查询参数
	 * @return
	 * @throws Exception
	 */
	private <A> DetachedCriteria getCriteria(boolean isLike, Class<A> clazz, List<QueryParam> params) throws Exception {
		return getBaseCriteria(clazz,params, getModelCriteria(isLike,clazz, params));
	}	
	/**
	 * 根据注解类获取表字段
	 * @param annotation	注解类
	 * @return
	 */
	private Field getBasePoField(Class<? extends Annotation> annotation){
		Field[] fields = BasePo.class.getDeclaredFields();
		for (Field field : fields) {
			if(field.isAnnotationPresent(annotation)){
				return field;
			}
		}
		return null;
	}
	/**
	 * 获取通用查询条件
	 * @param params	查询参数
	 * @param criteria	根据查询参数封装好的查询条件
	 * @return
	 * @throws Exception
	 */
	private <A> DetachedCriteria getBaseCriteria(Class<A> clazz,List<QueryParam> params,DetachedCriteria criteria) throws Exception {
		Boolean searchDelete = true;
		Field deleteField = getBasePoField(LogicDelete.class);
		for (QueryParam queryParam : params) {
			if(deleteField!=null && queryParam.getFieldName().equals(deleteField.getName())){
				searchDelete = false;
			}
		}
		if(searchDelete){
			//添加逻辑删除位判断
			RaysModel ra = clazz.getAnnotation(RaysModel.class);
			if(ra!=null && ra.entity() && ra.deleteLogic()){
				Conjunction logic = Restrictions.conjunction();
				logic.add(Restrictions.eq(deleteField.getName(), false));
				criteria.add(logic);
			}
		}
		return criteria;
	}	
	/**
	 * 根据传来的查询参数值和实体类方法进行类型转换
	 * @param method
	 * @param param
	 * @return
	 * @throws Exception
	 */
	private <A> Object valueConversion(Class<A> clazz,Method method,QueryParam param) throws Exception{
		String operator = param.getOperator();
		Object fieldValue_withType = null;
		try {
			Map<String,Object> data = new HashMap<String,Object>();
			data.put(param.getFieldName(), param.getFieldValue());
			fieldValue_withType = method.invoke(gson.fromJson(gson.toJson(data), clazz));
		} catch (Exception e) {
			e.printStackTrace();
			if(!(operator.equals("<>")||operator.equals("|")||operator.equals("order"))){
				Logger.error("Can't common Type: ["+method.getReturnType()+"] <-- "+param.getFieldValue());
				throw new IllegalException();
			}
		}
		return fieldValue_withType;
	}
	/**
	 * 根据传来的查询对象获取查询条件
	 * @param isLike
	 * @param clazz
	 * @param params
	 * @return
	 * @throws Exception
	 */
	private <A> DetachedCriteria getModelCriteria(boolean isLike, Class<A> clazz, List<QueryParam> params) throws Exception {
		DetachedCriteria criteria = DetachedCriteria.forClass(clazz);
		Junction junc = null;
		if(isLike){
			junc = Restrictions.disjunction();
		}else{
			junc = Restrictions.conjunction();
		}
		
		for (QueryParam param : params) {
			
			if (param.getOperator() == null){throw new IllegalException();}
			String operator = param.getOperator();
			
			if (operator.equalsIgnoreCase("order")) {
				if (param.getFieldValue().toString().equalsIgnoreCase("asc")) {
					criteria.addOrder(Order.asc(param.getFieldName()));
				} else if (param.getFieldValue().toString().equalsIgnoreCase("desc")) {
					criteria.addOrder(Order.desc(param.getFieldName())); 
				}
				continue;
			}
			
			if (operator.equals("~~")) {
				Disjunction keyword = Restrictions.disjunction();
				
				List<String> keyWordNameList = gson.fromJson(param.getFieldName(), new TypeToken<List<String>>(){}.getType());
				if(keyWordNameList==null){
					Logger.error("Unsupporteds Type: (~~)[keyWordNameList]:null");
					throw new IllegalException(RaysMessage.ILLEGAL_KEY_COLUMN_NULL);
				}
				for (String name : keyWordNameList) {
					keyword.add(Restrictions.ilike(name,"%"+param.getFieldValue()+"%"));
				}
				
				criteria.add(keyword);//keyWord
				continue;
			}
			
			//类型转换
			Method method = null;
			try {
				method = clazz.getMethod("get" + StringUtils.capitalize(param.getFieldName()));
			} catch (Exception e) {
				Logger.error("No Method: get" + StringUtils.capitalize(param.getFieldName()));
				throw new IllegalException();
			}
			
			//多元操作处理(大于小于,in,notin)
			if (operator.equals("<>") || operator.equals("|") || operator.equals("!|")) {
				if(param.getFieldValue()==null || "".equals(param.getFieldValue())){
					Logger.error("Unsupported Type: (<"+param.getFieldName()+">)["+method.getReturnType()+"]:"+param.getFieldValue());
					throw new IllegalException();
				}
				
				Object[] data;
				try {
					data = toArray(method, param);
				} catch (Exception e) {
					throw new IllegalException();
				}
				
				if(operator.equals("<>")){
					Disjunction bet = Restrictions.disjunction();
					if(data.length!=2){
						Logger.error("Unsupported Type: (<"+param.getFieldName()+">)["+method.getReturnType()+"]:"+param.getFieldValue());
						throw new IllegalException();
					}
					junc.add(bet.add(Restrictions.between(param.getFieldName(),data[0],data[1])));
					continue;
				}//大于小于
				else{
					//in或者notin   如果数组长度小于1 抛错
					if(data.length<1){
						throw new IllegalException();
					}
					//添加查询条件
					if(operator.equals("|")){
						junc.add(Restrictions.in(param.getFieldName(),data));
					}else{
						junc.add(Restrictions.not(Restrictions.in(param.getFieldName(),data)));
					}
				}
				
				continue;
			}
			
			Object fieldValue_withType = valueConversion(clazz,method, param);
			
			//一元操作处理
			if (operator.equals("=")) {
				if(fieldValue_withType==null)
					junc.add(Restrictions.isNull(param.getFieldName()));
				else
					junc.add(Restrictions.eq(param.getFieldName(), fieldValue_withType));
				continue;
			}
			if (operator.equals(">")) {
				junc.add(Restrictions.gt(param.getFieldName(), fieldValue_withType));
				continue;
			}
			if (operator.equals(">=")) {
				junc.add(Restrictions.ge(param.getFieldName(), fieldValue_withType));
				continue;
			}
			if (operator.equals("<")) {
				junc.add(Restrictions.lt(param.getFieldName(), fieldValue_withType));
				continue;
			}
			if (operator.equals("<=")) {
				junc.add(Restrictions.le(param.getFieldName(), fieldValue_withType));
				continue;
			}
			if (operator.equals("!=")) {
				if(fieldValue_withType==null)
					junc.add(Restrictions.isNotNull(param.getFieldName()));
				else
					junc.add(Restrictions.ne(param.getFieldName(), fieldValue_withType));
				continue;
			}
			if (operator.equals("~")) {
				if(!method.getReturnType().equals(String.class)){
					Logger.error("Unsupporteds Type: (~)["+method.getReturnType()+"]:"+param.getFieldValue());
					throw new IllegalException();
				}
				junc.add(Restrictions.ilike(param.getFieldName(),"%"+fieldValue_withType+"%"));
				continue;
			}//like
			
		}
		if(junc.conditions().iterator().hasNext()){
			criteria.add(junc);//keyWord
		}
		return criteria; 
	}

	private Object[] toArray(Method method,QueryParam param) throws Exception{
		Object[] in = null;
		if(method.getReturnType().equals(String.class)){
			in = gson.fromJson(gson.toJson(param.getFieldValue()), String[].class);
		} else if(method.getReturnType().equals(Long.class)){
			in = gson.fromJson(gson.toJson(param.getFieldValue()), Long[].class);
		} else if(method.getReturnType().equals(Integer.class)){
			in = gson.fromJson(gson.toJson(param.getFieldValue()), Integer[].class);
		} else if(method.getReturnType().equals(Double.class)){
			in = gson.fromJson(gson.toJson(param.getFieldValue()), Double[].class);
		} else {
			Logger.error("Unsupporteds Type: (|)["+method.getReturnType()+"]:"+param.getFieldValue());
			throw new IllegalException();
		}
		return in;
	}
	@Override
	public <A> A getByProperty(Class<A> clazz, String code, Object value) throws Exception {
		List<A> list = this.findByProperty(clazz, code, value);
		if(list==null || list.size()<1){
			return null;
		}
		if(list.size()>1){
			throw new RaysException(RaysMessage.getDataRepeatMsg(StringUtils.getClassName(clazz), code, value.toString()));
		}
		return list.get(0);
	}

	
}
