package com.black.web.models.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import com.black.web.base.annotation.RaysModel;
import com.black.web.base.annotation.Unique;
import com.black.web.base.bean.BasePo;

@Entity
@Table(name = "user")
@RaysModel(deleteLogic=false,entity=true)
public class User extends BasePo{
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
	// 用户名称
	@Column(name = "name")
	private String name;
	public String getName() {
		return this.name;
	}
	public void setName(String name) {
		this.name = name;
	}

	// area
	@Column(name = "area")
	private String area;
	public String getArea() {
		return this.area;
	}
	public void setArea(String area) {
		this.area = area;
	}

	// domain
	@Column(name = "domain")
	private String domain;
	public String getDomain() {
		return this.domain;
	}
	public void setDomain(String domain) {
		this.domain = domain;
	}

	// email
	@Column(name = "email")
	private String email;
	public String getEmail() {
		return this.email;
	}
	public void setEmail(String email) {
		this.email = email;
	}

	// 组ID
	@Column(name = "guid")
	private String guid;
	public String getGuid() {
		return this.guid;
	}
	public void setGuid(String guid) {
		this.guid = guid;
	}

	// idnumber
	@Column(name = "idnumber")
	private String idnumber;
	public String getIdnumber() {
		return this.idnumber;
	}
	public void setIdnumber(String idnumber) {
		this.idnumber = idnumber;
	}

	// password
	@Column(name = "password")
	private String password;
	public String getPassword() {
		return this.password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	// phone
	@Column(name = "phone")
	private String phone;
	public String getPhone() {
		return this.phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}

	// seat_num
	@Column(name = "seat_num")
	private String seatNum;
	public String getSeatNum() {
		return this.seatNum;
	}
	public void setSeatNum(String seatNum) {
		this.seatNum = seatNum;
	}

	// 类型
	@Column(name = "type")
	private String type;
	public String getType() {
		return this.type;
	}
	public void setType(String type) {
		this.type = type;
	}

	// ucode
	@Column(name = "ucode")
	@Unique
	private String ucode;
	public String getUcode() {
		return this.ucode;
	}
	public void setUcode(String ucode) {
		this.ucode = ucode;
	}

	// uid
	@Column(name = "uid")
	private String uid;
	public String getUid() {
		return this.uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}

	// uname
	@Column(name = "uname")
	private String uname;
	public String getUname() {
		return this.uname;
	}
	public void setUname(String uname) {
		this.uname = uname;
	}

	// unit_id
	@Column(name = "unit_id")
	private Long unitId;
	public Long getUnitId() {
		return this.unitId;
	}
	public void setUnitId(Long unitId) {
		this.unitId = unitId;
	}

	// unit_ou
	@Column(name = "unit_ou")
	private String unitOu;
	public String getUnitOu() {
		return this.unitOu;
	}
	public void setUnitOu(String unitOu) {
		this.unitOu = unitOu;
	}

}
