package com.black.web.base.controllers;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.black.web.Logger.Logger;
import com.black.web.base.bean.BaseModel;
import com.black.web.base.bean.PageRequest;
import com.black.web.base.bean.PageResponse;
import com.black.web.base.service.BaseService;
import com.black.web.base.utils.StringUtils;


public abstract class BaseController<PO extends BaseModel> extends RaysController{

    protected abstract BaseService getService();
    
    private Class<PO> po = null;
    
    @SuppressWarnings("unchecked")
	public BaseController(){
    	po = (Class <PO>) ((Class<PO>)((java.lang.reflect.ParameterizedType)getClass()
			      .getGenericSuperclass()).getActualTypeArguments()[0]);
    }
    
    public String load() throws Exception{
    	Logger.info(StringUtils.getClassName(po)+" load");
		return ok(gson.toJson(
				new PageResponse<PO>(true,
						getService().load(po))));
    }
    
    public <A> String get(Serializable id) throws Exception{
    	Logger.info(StringUtils.getClassName(po)+" get ["+id+"]");
		return ok(gson.toJson(
				new PageResponse<PO>(true,getService().findById(po,id))));
	}
    
    public String query(PageRequest<? extends BaseModel> vo) throws Exception{
    	Logger.info(StringUtils.getClassName(po)+" query ["+vo+"]");
		//如果为树结构  不分页
		if(vo.tree){
			vo.isNotPage = true;
		}
		
		List<Map<String,Object>> list = (List<Map<String, Object>>) getService().findListPage(po,vo);
		return formatData(vo, list);
	}
    
    public String select(PageRequest<? extends BaseModel> vo) throws Exception {
    	Logger.info(StringUtils.getClassName(po)+" select ["+vo+"]");
		//如果为树结构  不分页
		if(vo.tree){
			vo.isNotPage = true;
		}
		
		List<Map<String,Object>> list = (List<Map<String, Object>>) getService().selectListPage(po,vo);
		return formatData(vo, list);
	}
    
    private String formatData(PageRequest<? extends BaseModel> vo,List<Map<String,Object>> list) throws Exception{
    	if(vo.tree){
			Object tree = getService().getTree(po,list);
			return ok(gson.toJson(new PageResponse<Object>(true,
					tree)));
		}
		return ok(gson.toJson(new PageResponse<Map<String,Object>>(true,list,vo)));
    }
    public String save(BaseModel po) throws Exception {
    	String className = StringUtils.getClassName(po.getClass());
    	Logger.info(className+" save ["+gson.toJson(po)+"]");
		Serializable save = getService().save(po);
		Logger.info(className+" save success ["+save+"]");
		
		return ok(gson.toJson(
				new PageResponse<Serializable>(true,save)));
    }
    
    public String update(BaseModel po) throws Exception {
    	String className = StringUtils.getClassName(po.getClass());
    	Logger.info(className+" update ["+gson.toJson(po)+"]");
		getService().update(po);
		Logger.info(className+" update success ");
		
		return ok(gson.toJson(
				new PageResponse<Object>(true)));
    }
    
    public String delete(BaseModel b) throws Exception{
    	Long[] ids = b.getIds();
    	Logger.info(StringUtils.getClassName(po)+" delete ["+gson.toJson(ids)+"]");
    	getService().delete(po,ids);
    	Logger.info(StringUtils.getClassName(po)+" delete success");
		return ok(gson.toJson(
			new PageResponse<Object>(true)));
    }
}
