package com.mcp.order.model.admin;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Id;

/*
 * User: yeeson he
 * Date: 13-7-30
 * Time: 下午3:16
 */

public class ManagerRolePrivilege implements java.io.Serializable {
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 7331255492162021460L;
	
	//可用的操作。
	@Id
    @Column(length=40)
    private String id;      //!
	
	@Basic
    @Column(length=40)
    private String privilegeCode; //!
	
	@Basic
    @Column(length=40)
    private String privilegeName; //!
	
	@Basic
    @Column(length=200)
    private String privilegeUrl;  //!
	
	@Basic
    private int status; //! d0 可用 1 不可用
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPrivilegeCode() {
		return privilegeCode;
	}
	public void setPrivilegeCode(String privilegeCode) {
		this.privilegeCode = privilegeCode;
	}
	public String getPrivilegeName() {
		return privilegeName;
	}
	public void setPrivilegeName(String privilegeName) {
		this.privilegeName = privilegeName;
	}
	public String getPrivilegeUrl() {
		return privilegeUrl;
	}
	public void setPrivilegeUrl(String privilegeUrl) {
		this.privilegeUrl = privilegeUrl;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
    
}
