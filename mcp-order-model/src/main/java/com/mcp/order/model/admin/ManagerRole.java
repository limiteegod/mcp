package com.mcp.order.model.admin;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Id;

/**
 * User: yeeson he
 * Date: 13-7-18
 * Time: 上午10:49
 * To be the best of me!
 */
public class ManagerRole implements java.io.Serializable {
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 4997372571923054766L;
	
	// 管理员角色。 超级管理员、运维人员、财务人员、客服人员
	@Id
    @Column(length = 32)
    private String id; //!
	
	@Basic
    @Column(length=40)
    private String roleName;  //!
	
	@Basic
    @Column(length=40)
    private String roleDesc;   //!
	
	@Basic
    @Column(length=40)
    private String privileges;  //!多个编号之间用逗号分隔，对应ManagerRolePrivilege的id值。
    
	@Basic
	private int status;   //! d0 可用 1 不可用
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public String getRoleDesc() {
		return roleDesc;
	}
	public void setRoleDesc(String roleDesc) {
		this.roleDesc = roleDesc;
	}
	public String getPrivileges() {
		return privileges;
	}
	public void setPrivileges(String privileges) {
		this.privileges = privileges;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
    
    
}
