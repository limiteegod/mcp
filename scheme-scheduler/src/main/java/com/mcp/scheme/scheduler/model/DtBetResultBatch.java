package com.mcp.scheme.scheduler.model;


public class DtBetResultBatch {
	
	/**
	 * 商户号
	 */
	private String channelCode;
	
	/**
	 * 持卡人编号
	 */
	private String cardOwnerId;
	
	/**
	 * 外部订单号
	 */
	private String outerOrderId;
	
	/**
	 * 成功或者失败标志，0，失败，1，成功。
	 */
	private int flag;
	
	/**
	 * 处理的描述
	 */
	private String description;

	public String getChannelCode() {
		return channelCode;
	}

	public void setChannelCode(String channelCode) {
		this.channelCode = channelCode;
	}

	public String getCardOwnerId() {
		return cardOwnerId;
	}

	public void setCardOwnerId(String cardOwnerId) {
		this.cardOwnerId = cardOwnerId;
	}

	public String getOuterOrderId() {
		return outerOrderId;
	}

	public void setOuterOrderId(String outerOrderId) {
		this.outerOrderId = outerOrderId;
	}

	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
