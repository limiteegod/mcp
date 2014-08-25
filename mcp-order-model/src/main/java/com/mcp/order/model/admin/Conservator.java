package com.mcp.order.model.admin;

import javax.persistence.*;
import java.util.Date;

/**
 * User: yeeson he
 * Date: 13-7-17
 * Time: 上午11:18
 * To be the best of me!
 */
@Entity
@Table(name = "conservator")
public class Conservator implements java.io.Serializable {
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 4863812306772959584L;
	

	//    市场监管员。一个区域可能有多个市场监管员。
	@Id
	@Column(length=32)
    private String id; //!
	
	@Basic
	@Column(length=32)
    private String areaId;   //! 监管员可以查看本地区的所有彩种。
	
	@Basic
	@Column(length=40)
    private String conservatorName; //真实姓名。
	
	@Basic
	@Column(length=40)
    private String userName; //！登录的账户名。
	
	@Basic
	@Column(length=40)
    private String password;              //！用于登录的密码。
	
	@Basic
	@Column(length=80)
    private String email;//绑定的邮件地址。用于订阅系统报表或者其它信息。
	
	@Basic
	@Column(length=20)
    private String phone;
	
	@Basic
	@Column(length=20)
    private String note;
	
	@Basic
    private Date regDate; //!
	
	@Basic
    private Date lastActiveTime;
	
	@Basic
	@Column(length=20)
    private String lastLoginIp;
	
	@Basic
    private int status;//! 状态 0 可用 1 不可用

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAreaId() {
		return areaId;
	}

	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}

	public String getConservatorName() {
		return conservatorName;
	}

	public void setConservatorName(String conservatorName) {
		this.conservatorName = conservatorName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Date getRegDate() {
		return regDate;
	}

	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}

	public Date getLastActiveTime() {
		return lastActiveTime;
	}

	public void setLastActiveTime(Date lastActiveTime) {
		this.lastActiveTime = lastActiveTime;
	}

	public String getLastLoginIp() {
		return lastLoginIp;
	}

	public void setLastLoginIp(String lastLoginIp) {
		this.lastLoginIp = lastLoginIp;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
	
}
