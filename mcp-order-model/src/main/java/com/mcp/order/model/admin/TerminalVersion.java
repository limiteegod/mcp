package com.mcp.order.model.admin;

import javax.persistence.*;

/**
 * User: yeeson he
 * Date: 13-7-17
 * Time: 上午11:39
 * To be the best of me!
 */
@Entity
@Table(name = "terminalversion")
public class TerminalVersion implements java.io.Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -402274186332747543L;

	//    终端机类型
	@Id
	@Column(length=32)
    private String id; //!
	
	@Basic
    private int type;//0 未指定 1 福彩、2 体彩 。
	
	@Basic
	@Column(length = 40)
    private String vendorName;//厂家名称。
	
	@Basic
	@Column(length = 40)
    private String name;
	
	@Basic
	@Column(length = 40)
    private String code;
	
	@Basic
	@Column(length = 40)
    private String description;
	
	@Basic
    private int status;   //当前状态 0 可用  1 不可用

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getVendorName() {
		return vendorName;
	}

	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
