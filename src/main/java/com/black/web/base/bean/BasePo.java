package com.black.web.base.bean;

import java.lang.reflect.Field;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.hibernate.Session;

import com.black.web.base.annotation.LogicDelete;
import com.black.web.base.annotation.ShowIgnore;

@MappedSuperclass
public class BasePo extends BaseModel{
	// 创建时间
	@Column(name = "creattime")
	@ShowIgnore
	private Long creattime;
	public Long getCreattime() {
		return this.creattime;
	}
	public void setCreattime(Long creattime) {
		this.creattime = creattime;
	}

	// 更新时间
	@Column(name = "updatetime")
	@ShowIgnore
	private Long updatetime;
	public Long getUpdatetime() {
		return this.updatetime;
	}
	public void setUpdatetime(Long updatetime) {
		this.updatetime = updatetime;
	}

	// 状态
	@Column(name = "status")
	@ShowIgnore
	private String status;
	public String getStatus() {
		return this.status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	//逻辑删除状态位
	@Column(name = "is_delete")
	@LogicDelete
	@ShowIgnore
	private Boolean isDelete;
	public Boolean getIsDelete() {
		return isDelete;
	}
	public void setIsDelete(Boolean isDelete) {
		this.isDelete = isDelete;
	}
	//创建用户
	@Column(name = "create_user")
	@ShowIgnore
	private String createUser;
	public String getCreateUser() {
		return createUser;
	}
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}
	//更新用户
	@Column(name = "update_user")
	@ShowIgnore
	private String updateUser;
	public String getUpdateUser() {
		return updateUser;
	}
	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}
	
	// 描述
	@Column(name = "description")
	@ShowIgnore
	private String description;
	public String getDescription() {
		return this.description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * 显示过滤(将一些通用字段和ID设置为空)
	 * @throws Exception
	 */
	public void showFilter(Session session) throws Exception{
		session.evict(this);
		showFilter();
	}
	public void showFilter() throws Exception{
		showFilter(this.getClass());
	}
	private void showFilter(Class<?> clazz) throws Exception{
		if(clazz.equals(BaseModel.class)){
			return;
		}
		Field[] fields = clazz.getDeclaredFields();
		for (Field field : fields) {
			ShowIgnore si = field.getAnnotation(ShowIgnore.class);
			if((si!=null && si.value()) || field.getAnnotation(Id.class)!=null){
				field.setAccessible(true);
				field.set(this, null);
				field.setAccessible(false);
			}
			
		}
		showFilter(clazz.getSuperclass());
	}
}
