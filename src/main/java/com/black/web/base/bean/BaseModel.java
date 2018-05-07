package com.black.web.base.bean;

import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

@MappedSuperclass
public class BaseModel{
	
	@Transient
	private Long[] ids;
	public Long[] getIds() {
		return ids;
	}
	public void setIds(Long[] ids) {
		this.ids = ids;
	}

	final public String getKeyWordColumnHql(String keyWord){//, Class clazz){
		//TODO 2lbj 关键字字段拼接
		//return " and " + keyWord;
		return "";
	};
	
	protected boolean equals(String child, String other) {
		return objEquals(child, other);
	}

	protected int hashCode(String id) {
		return objHashCode(id);
	}

	protected boolean equals(Long child, Long other) {
		return objEquals(child, other);
	}

	protected int hashCode(Long id) {
		return objHashCode(id);
	}

	protected boolean equals(Integer child, Integer other) {
		return objEquals(child, other);
	}

	protected int hashCode(Integer id) {
		return objHashCode(id);
	}

	protected boolean objEquals(Object child, Object other) {
		if (child == other)
			return true;
		if (child == null || other == null)
			return false;
		return child.equals(other);
	}

	protected int objHashCode(Object id) {
		if (id == null)
			return super.hashCode();
		return id.hashCode();
	}
}

