package com.mcp.order.model.admin;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Id;
import java.util.Date;

/**
 * User: yeeson he
 * Date: 13-7-16
 * Time: 下午5:03
 * To be the best of me!
 */
public class Manager implements java.io.Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1643618011933449462L;
	
	//   本系统的内部管理员账户
	@Id
    @Column(length = 32)
    private String id;     //!
	
	@Basic
    @Column(length=40)
    private String userName;  //!
	
	@Basic
    @Column(length=40)
    private String password;     //!
	
	@Basic
    @Column(length=40)
    private String realName;        //!
	
	@Basic
    @Column(length=40)
    private String roleCode;   //! to ManagerRole
	
	@Basic
    private Date regDate;  //!
	
	@Basic
    private Date expiredDate;  //! 账户失效日期。需要有后台服务日结时修改各个账户的状态。
    
	@Basic
	private Date lastActiveTime;
	
	@Basic
    @Column(length=40)
    private String lastLoginIp;
	
	@Basic
    private int status;//! 状态 d0可用 1 不可用
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
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
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	public String getRoleCode() {
		return roleCode;
	}
	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}
	public Date getRegDate() {
		return regDate;
	}
	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}
	public Date getExpiredDate() {
		return expiredDate;
	}
	public void setExpiredDate(Date expiredDate) {
		this.expiredDate = expiredDate;
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
