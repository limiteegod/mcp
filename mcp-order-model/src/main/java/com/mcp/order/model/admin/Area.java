package com.mcp.order.model.admin;

import javax.persistence.*;

/**
 * User: yeeson he
 * Date: 13-7-17
 * Time: 上午11:21
 * To be the best of me!
 */
@Entity
@Table(name = "area")
public class Area implements java.io.Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = -1011492433206559941L;

	//    地区跟具体的体彩或福彩中心关联。目前精确到地市一级。可以扩展到县一级。
	@Id
	@Column(length=32)
    private String id;  //!
	
	@Basic
	@Column(length=32)
    private String parentId; //!上级管理单位。对应另外一个Area实体的id字段。可能与具体的行政级别略有不同。
	
	@Basic
	@Column(length=40)
    private String name;//!区域名称，比如山东、河北、大连等。
	
	@Basic
    private int aLevel;//! 0 未指定。1 国家中心、2 省中心、3 地市中心。level级别指的是所属彩票机构的级别，与实际的行政级别略有不同。
	
	@Basic
	@Column(length=40)
    private String code;  //! 地区编码，全国为86，省份和地市可以使用身份证的前两位、四位。精确到市。地区编码使用地理编码。
	
	@Basic
    private int type;//! 0 未指定 1 体彩 2 福彩    。默认为0
	
	@Basic
	@Column(length=40)
    private String contact;//本地区当地联系人。
	
	@Basic
	@Column(length=200)
    private String address;  //本地区彩票中心地址。
	
	@Basic
	@Column(length=20)
    private String telephone; //本地区彩票中心联系方式
	
	@Basic
	@Column(length=200)
    private String description;  //本地区描述
	
	@Basic
    private int status;//! 本地区状态，0 未开展、1 洽谈中、2 业务开展中、3 业务暂停或下线。一般用来描述我公司业务在当地的开展状态。

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getaLevel() {
		return aLevel;
	}

	public void setaLevel(int aLevel) {
		this.aLevel = aLevel;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
	
}
