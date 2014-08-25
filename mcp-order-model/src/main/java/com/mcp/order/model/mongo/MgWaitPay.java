/**
 * 
 */
package com.mcp.order.model.mongo;

import java.util.Date;

/**
 * 记录等待支付的id的类型，比如订单，追号方案
 * @author ming.li
 *
 */
public class MgWaitPay {
	
	private String id;
	
	private int schemeType;
	
	private Date createTime;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getSchemeType() {
		return schemeType;
	}

	public void setSchemeType(int schemeType) {
		this.schemeType = schemeType;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}
