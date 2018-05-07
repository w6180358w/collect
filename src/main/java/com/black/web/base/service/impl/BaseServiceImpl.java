package com.black.web.base.service.impl;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.Column;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import com.black.web.base.bean.PageRequest;
import com.black.web.base.bean.QueryParam;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.black.web.base.annotation.FieldTag;
import com.black.web.base.annotation.RaysModel;
import com.black.web.base.annotation.TreeField;
import com.black.web.base.annotation.ForeignSearch;
import com.black.web.base.bean.BaseModel;
import com.black.web.base.bean.PagePo.PageLimit;
import com.black.web.base.dao.BaseDao;
import com.black.web.base.exception.RaysException;
import com.black.web.base.msg.RaysMessage;
import com.black.web.base.service.BaseService;
import com.black.web.base.utils.GsonUtil;
import com.black.web.base.utils.StringUtils;

@Transactional(rollbackFor=Exception.class)
public class BaseServiceImpl implements BaseService{

	@Autowired
	protected BaseDao dao;
	
	protected Gson gson = GsonUtil.getNoDoubleGson();
	@Override
    public <A> List<A> load(Class<A> clazz) {
        return dao.load(clazz);
    }
    @Override
    public <A> A findById(Class<A> clazz,Serializable id) {
        return (A) dao.findById(clazz,id);
    }
    @Override
    public <A> List<Map<String, Object>> findListPage(Class<A> clazz,PageRequest<? extends BaseModel> vo) throws Exception {
    	return search(true, clazz, vo);
    }
    @Override
    public <A> List<Map<String, Object>> selectListPage(Class<A> clazz,PageRequest<? extends BaseModel> vo) throws Exception {
        return search(false, clazz, vo);
    }
    
    private <A> List<Map<String, Object>> search(Boolean query,Class<A> clazz,PageRequest<? extends BaseModel> vo) throws Exception {
    	List<QueryParam> qpList = before(clazz,vo.getQueryParamList()==null?new ArrayList<QueryParam>():vo.getQueryParamList());
    	List<A> result = new ArrayList<A>();
    	//keyWord
    	if(vo.getKeyWordParam()!=null){
    		if(StringUtils.isNotBlank(vo.getKeyWord()) && vo.getKeyWordParam().length>0){
    			qpList.add(new QueryParam(gson.toJson(vo.getKeyWordParam()),vo.getKeyWord(),"~~"));
    		}
    	}
        if(vo.isNotPage){
        	result = dao.findList(clazz,query, qpList);
        }else{
        	PageLimit page = vo.getPage();
            page.setTotalResult(dao.findSizePage(clazz,query, qpList));
            vo.setPage(page);
            result = dao.findListPage(clazz,query, qpList, page.getIndex(), page.getSize());
        }

        return after(formatData(clazz, result, vo));
    }
    /**
     * 添加关联数据(需要PO配置)
     * @param clazz
     * @param list
     * @return
     * @throws Exception
     */
    private <A> List<Map<String, Object>> formatData(Class<A> clazz,List<A> list,PageRequest<? extends BaseModel> vo) throws Exception{
    	List<Map<String,Object>> result = addRelevanceData(clazz, list,vo);
    	if(vo.foreign){
    		return addForeignData(clazz, result);
    	}
    	return result;
    }
    /**
     * 添加非外键关联数据(需要PO配置)
     * @param clazz
     * @param list
     * @param vo
     * @return
     * @throws Exception
     */
    private <A> List<Map<String,Object>> addRelevanceData(Class<A> clazz,List<A> list,PageRequest<? extends BaseModel> vo) throws Exception{
    	//将实体转换成map
    	List<Map<String,Object>> result = gson.fromJson(gson.toJson(list), new TypeToken<List<Map<String, Object>>>(){}.getType());
    	
        //判断要查询的关联类是否存在
        if(vo.relevance==null || vo.relevance.size()<1){
			return result;
		}
    	Map<Object,Map<String,List<A>>> map = new HashMap<Object,Map<String,List<A>>>();
    	RaysModel rm = clazz.getAnnotation(RaysModel.class);
    	if(rm!=null && rm.children().length>0){
    		//循环判断要查询哪些关联类
    		ForeignSearch[] rsArray = rm.children();
    		for (ForeignSearch rs : rsArray) {
    			if(!vo.relevance.contains(rs.code())){
    				continue;
    			}
    			FieldTag fromft = rs.from();
    			Field field = fromft.clazz().getDeclaredField(fromft.field());
    			//将查询出来的数据(当前实体from关联后的map)存进临时map中
    			map.put(rs.code(), searchRelevanceData(clazz, field, rs, result));
			}
    		//循环集合根据字段取出数据  替换原来数据
            for (Map<String, Object> a : result) {
    			Map<String, Object> childs = new HashMap<String,Object>();
    			//循环判断组装数据
    			for (ForeignSearch rs : rsArray) {
    				if(!vo.relevance.contains(rs.code())){
        				continue;
        			}
            		FieldTag fromft = rs.from();
            		Field field = fromft.clazz().getDeclaredField(fromft.field());
            		List<A> dataList = map.get(rs.code()).get(field.getName()+"_"+a.get(field.getName()));
            		childs.put(rs.code(), dataList==null?new ArrayList<A>():dataList);
        		}
            	a.put("relevance", childs);
            }
    	}
    	return result;
    }
    /**
     * 字段关联查询注解(外键关联)
     * 根据当前表中的外键查询对应表的数据
     * 并将查询出来的数据转换成json对象替换原字段
     * @param clazz
     * @param data
     * @return
     * @throws Exception
     */
    private <A> List<Map<String,Object>> addForeignData(Class<A> clazz,List<Map<String,Object>> data) throws Exception{
    	Map<Object,List<A>> map = new HashMap<Object,List<A>>();
    	List<Field> relevanceField = new ArrayList<Field>(); 
    	Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
        	ForeignSearch rs = field.getAnnotation(ForeignSearch.class);
        	if(rs!=null){
        		relevanceField.add(field);
        		//将查询出来的数据(当前实体from关联后的map)存进临时map中
        		map.putAll(searchRelevanceData(clazz, field, rs, data));
        	}
        }
        //循环集合根据字段取出数据  替换原来数据
        for (Map<String, Object> a : data) {
        	for (Field field : relevanceField) {
        		List<A> dataList = map.get(field.getName()+"_"+a.get(field.getName()));
        		a.put(field.getName(), dataList==null?new ArrayList<A>():dataList);
    		}
        }
    	return data;
    }
    /**
     * 根据查询出来的数据结构组装map
     * key为from表的id  value为对应的数据集合
     * @param clazz
     * @param field
     * @param rs
     * @param list
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
	private <A> Map<String,List<A>> searchRelevanceData(Class<A> clazz,Field field,ForeignSearch rs,List<Map<String, Object>> list) throws Exception{
    	Map<String,List<A>> map = new HashMap<String,List<A>>();
    	
    	//获取hql 查询数据  hql为in 
    	String hql = getRelevanceSearchHql(clazz,rs, field, list);
    	List<Object[]> data = (List<Object[]>) this.dao.hql_ListObject(hql);
    	if(data!=null && data.size()>0){
    		//为字段绑定数据
        	for (Object[] obj : data) {
        		Object id = obj[1];
        		A model = (A) obj[0];
        		List<A> dataList = map.get(field.getName()+"_"+id);
        		if(dataList == null){
        			dataList = new ArrayList<A>();
        		}
        		dataList.add(model);
        		map.put(field.getName()+"_"+id, dataList);
			}
    	}
    	System.out.println("级联查询:"+hql+";个数"+data.size());
    	return map;
    	
    }
    /**
     * 获取hql
     * from为主表  to数组为关联表 最后一位是要查询的子表数据
     * to数组必须按照顺序写   如果数组中相邻两个数据中的clazz相同则会跳过(否则会造成同一张表不同字段相等的情况,例如:TenantsApp.appId = TenantsApp.tenantsId)
     * select后接查询的数据和from主表的字段  保证主子表数据是对应的
     * @param clazz
     * @param sa
     * @param field
     * @param list
     * @return
     * @throws Exception
     */
    private <A> String getRelevanceSearchHql(Class<A> clazz,ForeignSearch sa,Field field,List<Map<String,Object>> list) throws Exception{
    	StringBuffer table = new StringBuffer();
        StringBuffer param = new StringBuffer();
        Set<String> tableSet = new HashSet<String>();
        tableSet.add(StringUtils.getClassName(sa.from().clazz()));

        FieldTag lastTo = sa.to()[sa.to().length-1];
        String baseParam = toParam(sa.from());
        //要查询的表  如果和from表名相同则别名为两个下划线
        String selectTable = StringUtils.getClassName(lastTo.clazz())+"_";
    	String hql = "select DISTINCT("+validateTable(sa.from(), lastTo, selectTable)+"), "+baseParam+" from ";
       	
    	table.append(toTable(sa.from()));
        
        param.append(baseParam+" in (-1");
        Set<Object> baseParamSet = new HashSet<Object>();
        for (Map<String, Object> a2 : list) {
        	if(baseParamSet.add(a2.get(field.getName()))){
        		param.append(",'"+a2.get(field.getName())+"'");
        	}
		}
        param.append(")");
        
        for(int i=0;i<sa.to().length;i++){
        	FieldTag[] temp = sa.to();
        	String name = StringUtils.getClassName(temp[i].clazz());
        	if(tableSet.add(name)){
        		table.append(","+toTable(temp[i]));
        	}
        	//如果to最后一个(即要查询的表)和from表相同说明查询的是本身  需要加不同的别名
        	if(i+1==sa.to().length && sa.from().clazz().equals(temp[i].clazz())){
        		table.append(","+validateTable(sa.from(), temp[i], toTable(temp[i])));
        	}
        }
        
        for(int i=0;i<sa.to().length;i++){
        	FieldTag[] temp = sa.to();
        	String iParam = toParam(temp[i]);
        	//如果to最后一个(即要查询的表)和from表相同说明查询的是本身  需要加不同的别名
        	if(i+1==sa.to().length){
        		iParam = validateTable(sa.from(), temp[i], iParam);
        	}
        	//如果是第一条 添加初始条件(此时已经判断过要查询的表是不是本身表)
        	if(i==0){
        		param.append(" and "+baseParam+" = "+iParam);
        	}
        	//如果不是最后一条  添加条件
        	if(i+1<sa.to().length){
        		if(sa.to()[i].clazz().equals(sa.to()[i+1].clazz())){
        			continue;
        		}
        		//判断下一条是不是最后一条  如果是判断是否为本身表  需不需要加不同的别名
        		String iiParam = (i+1==sa.to().length?validateTable(sa.from(), temp[i+1], toParam(temp[i+1])):toParam(temp[i+1]));
        		param.append(" and "+iParam+" = "+iiParam);
        	}
        }
        
        hql += table+" where "+param;
        return hql;
    }
    /**
     * 字符串转换
     * @param anno
     * @return
     */
    private String toParam(FieldTag ft){
		return StringUtils.getClassName(ft.clazz())+"_."+ft.field()+" ";
	}
    /**
     * 字符串转换
     * @param anno
     * @return
     */
	private String toTable(FieldTag ft){
		return StringUtils.getClassName(ft.clazz())+" "+StringUtils.getClassName(ft.clazz())+"_ ";
	}
	
	@Override
	public <A> Map<String,Object> getTree(List<A> list) throws Exception{
		if(list.isEmpty()){
			return new HashMap<String, Object>();
		}
		List<Map<String,Object>> uL = gson.fromJson(gson.toJson(list), new TypeToken<List<Map<String, Object>>>(){}.getType());
		
		return getTree(list.get(0).getClass(),uL);
	}
	
	@Override
	public <A> Map<String,Object> getTree(Class<A> clazz, List<Map<String,Object>> uL) throws Exception{
		Map<String,Object> root = new HashMap<String, Object>();
		
		Field[] fields = clazz.getDeclaredFields();
		Field childField = null;
		Field parentField = null;
		for (Field field : fields) {
			TreeField tf = field.getAnnotation(TreeField.class);
			if(tf!=null){
				childField = field;
				parentField = clazz.getDeclaredField(tf.field());
				root.put(tf.field(), tf.value());
				childField.setAccessible(true);
				parentField.setAccessible(true);
				break;
			}
		}
		
		Map<String,Object> result = getTree(childField,parentField,root, uL);
		if(childField!=null && parentField!=null){
			childField.setAccessible(false);
			parentField.setAccessible(false);
		}
		return result;
	}
	
	private Map<String,Object> getTree(Field childField,Field parentField,Map<String,Object> root, List<Map<String,Object>> uL) throws Exception{
		if(childField==null || parentField==null){
			throw new RaysException(RaysMessage.ILLEGAL_TREE_CONFIG_NULL);
		}
		List<Map<String,Object>> c = getOneParentChildren(childField,parentField,root,uL);
		if(!c.isEmpty()){
			List<Map<String,Object>> children = new ArrayList<Map<String,Object>>();
			for (Map<String,Object> mod : c) {
				children.add(getTree(childField,parentField,mod,uL));
			}
			root.put("children", children);
		}
		return root;
	}
	
	private List<Map<String,Object>> getOneParentChildren(Field childField,Field parentField,Map<String,Object> root, List<Map<String,Object>> uL) throws Exception{
		List<Map<String,Object>> r = new ArrayList<Map<String,Object>>();
		TreeField tf = childField.getAnnotation(TreeField.class);
		for (Map<String,Object> mod : uL) {
			String parent = root.get(tf.field()).toString();
			Object child = mod.get(childField.getName());
			String children = child.toString();
			//如果级联查询  该字段为父节点对象数组(只有一个元素)
			if(ArrayList.class.equals(child.getClass())){
				List<?> chilrenList = (List<?>) child;
				if(chilrenList.size()>0){
					//获取第一个元素的父节点关联字段
					Object obj = chilrenList.get(0);
					children = parentField.get(obj).toString();
				}else{
					children = tf.value();
				}
			}
			if(parent.equals(children)){
				r.add(mod);
			}
		}
		return r;
	}
	
	private String validateTable(FieldTag from,FieldTag lastTo,String str){
		if(lastTo.clazz().equals(from.clazz())){
			return str.replaceAll("_", "__");
		}
		return str;
	}
	@Override
    public <A> List<QueryParam> before(Class<A> clazz,List<QueryParam> paramList) throws Exception{
    	return paramList;
    }
	@Override
    public <A> Serializable save(A po) throws Exception {
        return dao.save(po);
    }
	@Override
    public <A> void saveOrUpdate(A po) throws Exception {
        dao.saveOrUpdate(po);
    }
	@Override
    public <A> void update(A po) throws Exception {
        dao.update(po);
    }
	@Override
    public <A> void delete(Class<A> clazz,Serializable[] ids) throws Exception {
        dao.delete(clazz,ids);
    }
	@Override
    public <A> void executeHql(String hql) {
        dao.hql_Execute(hql);
    }
	@Override
    public <A> List<A> findByProperty(Class<A> clazz,A po) throws Exception {
        Field[] field = po.getClass().getDeclaredFields();
        for (Field key : field) {
            String name = key.getName();
            name = name.substring(0, 1).toUpperCase() + name.substring(1);
            Method m = po.getClass().getMethod("get" + name);
            Serializable value = (Serializable) m.invoke(po);
            Column col = key.getAnnotation(Column.class);
            if (value != null && !"".equals(value) && col != null) {
                return this.dao.findByProperty(clazz,col.name(), value);
            }
        }
        return null;
    }

	@Override
	public <A> List<A> findByProperty(Class<A> clazz, String code, Object value) throws Exception {
		return this.dao.findByProperty(clazz, code, value);
	}
	
	@Override
	public <A> A getByProperty(Class<A> clazz, String code, Object value) throws Exception {
		return this.dao.getByProperty(clazz, code, value);
	}

	@Override
	public <A> List<Map<String, Object>> after(List<Map<String, Object>> list) throws Exception {
		return list;
	}
}
