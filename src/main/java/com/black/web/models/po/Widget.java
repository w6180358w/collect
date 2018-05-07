package com.black.web.models.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import com.black.web.base.annotation.RaysModel;
import com.black.web.base.annotation.ShowIgnore;
import com.black.web.base.bean.BasePo;


@Entity
@Table(name = "widget")
@RaysModel(deleteLogic=false,entity=true)
public class Widget extends BasePo{
	@Id
	@GeneratedValue(generator = "paymentableGenerator")
	@GenericGenerator(name = "paymentableGenerator", strategy = "native")
	// 编号
	@Column(name = "id")
	private Long id;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	// appId
	@Column(name = "app_id")
	@ShowIgnore
	private Long appId;
	public Long getAppId() {
		return appId;
	}
	public void setAppId(Long appId) {
		this.appId = appId;
	}
	
	// 部件名称
	@Column(name = "name")
	private String name;
	public String getName() {
		return this.name;
	}
	public void setName(String name) {
		this.name = name;
	}

	// 部件模板路径
	@Column(name = "template")
	private String template;
	public String getTemplate() {
		return this.template;
	}
	public void setTemplate(String template) {
		this.template = template;
	}

	// 数据API路径
	@Column(name = "dataapi")
	private String dataapi;
	public String getDataapi() {
		return this.dataapi;
	}
	public void setDataapi(String dataapi) {
		this.dataapi = dataapi;
	}

	// 部件说明
	@Column(name = "widget_desc")
	private String widgetDesc;
	public String getWidgetDesc() {
		return this.widgetDesc;
	}
	public void setWidgetDesc(String widgetDesc) {
		this.widgetDesc = widgetDesc;
	}

	// 部件类型
	@Column(name = "widget_type")
	private String widgetType;
	public String getWidgetType() {
		return this.widgetType;
	}
	public void setWidgetType(String widgetType) {
		this.widgetType = widgetType;
	}
}
