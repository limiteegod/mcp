package com.mcp.order.model.admin;

import javax.persistence.*;

/**
 * User: yeeson he
 * Date: 13-7-17
 * Time: 上午10:01
 * To be the best of me!
 */
@Entity
@Table(name = "terminal")
public class Terminal implements java.io.Serializable {
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 5720569456823186037L;

	//    单台终端机。终端机与彩票玩法为多对多关系。
	@Id
	@Column(length=32)
    private String id;   //!
	
	@Basic
	@Column(length=32)
    private String stationId;  //!
	
	@Basic
	@Column(length = 40)
    private String code; //!终端机编号
	
	@Basic
	@Column(length = 40)
    private String password;//!终端机密码
	
	@Basic
	@Column(length = 32)
    private String terminalVersionId; //终端机型号
	
	@Basic
    private int status;//当前状态 d0 可用  1 不可用

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getStationId() {
		return stationId;
	}

	public void setStationId(String stationId) {
		this.stationId = stationId;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getTerminalVersionId() {
		return terminalVersionId;
	}

	public void setTerminalVersionId(String terminalVersionId) {
		this.terminalVersionId = terminalVersionId;
	}
	
}
