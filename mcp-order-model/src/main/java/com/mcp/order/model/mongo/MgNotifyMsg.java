/**
 * 
 */
package com.mcp.order.model.mongo;

import com.mcp.order.common.ConstantValues;

import java.util.Date;

/**
 * 记录等待支付的id的类型，比如订单，追号方案
 * @author ming.li
 *
 */
public class MgNotifyMsg {

	private long id;

    private String hId;
	
	/**
	 * 通知的url
	 */
	private String msg;
	
	/**
	 * 通知的消息的状态，1，未处理，2，已经处理
	 */
	private int status = ConstantValues.NOTIFY_STATUS_UNUSED.getCode();
	
	/**
	 * 创建时间
	 */
	private Date createTime;

    public String gethId() {
        return hId;
    }

    public void sethId(String hId) {
        this.hId = hId;
    }

    public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}
