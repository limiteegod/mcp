package com.mcp.order.model.mongo;

/**
 * 存储游戏的当前期
 * @author ming.li
 */
public class MgCurTerm {
	
	/**
	 * 游戏代码，gameCode
	 */
	private String id;
	
	/**
	 * 当前期次号
	 */
	private String curTermCode;
	
	/**
	 * 上一期期次号
	 */
	private String lastTermCode;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCurTermCode() {
		return curTermCode;
	}

	public void setCurTermCode(String curTermCode) {
		this.curTermCode = curTermCode;
	}

	public String getLastTermCode() {
		return lastTermCode;
	}

	public void setLastTermCode(String lastTermCode) {
		this.lastTermCode = lastTermCode;
	}
}
