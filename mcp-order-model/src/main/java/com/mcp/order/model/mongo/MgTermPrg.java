package com.mcp.order.model.mongo;

/**
 * 记录期次进行的进度信息
 * @author ming.li
 */
public class MgTermPrg {
	
	/**
	 * 期次进度信息的键
	 */
	private String id;
	
	/**
	 * 期次进度信息的值
	 */
	private int cur;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getCur() {
		return cur;
	}

	public void setCur(int cur) {
		this.cur = cur;
	}
}
