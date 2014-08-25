package com.mcp.order.inter.query;

import com.mcp.order.exception.CoreException;
import com.mcp.order.inter.ReqBody;

import java.util.Date;

public class ReqQ08Body extends ReqBody {
	
	/**
	 * 支付渠道名称
	 */
	private String payment;

	/**
	 * 支付渠道的类型
	 */
	private int paymentType;

	/**
	 * 交易系统的内部订单id
	 */
	private String orderId;

	/**
	 * 支付渠道的内部id
	 */
	private String outerId;

	/**
	 * 交易通知时间
	 */
	private Date notifyTime;
	
	/**
	 * （外部）通知创建时间
	 */
	private Date notifyCreateTime;

	/**
	 * 交易通知类型
	 */
	private String notifyType;

	/**
	 * 支付金额
	 */
	private int amount;

	/**
	 * 备注描述
	 */
	private String description;

	public Date getNotifyCreateTime() {
		return notifyCreateTime;
	}

	public void setNotifyCreateTime(Date notifyCreateTime) {
		this.notifyCreateTime = notifyCreateTime;
	}

	public String getPayment() {
		return payment;
	}

	public void setPayment(String payment) {
		this.payment = payment;
	}

	public int getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(int paymentType) {
		this.paymentType = paymentType;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getOuterId() {
		return outerId;
	}

	public void setOuterId(String outerId) {
		this.outerId = outerId;
	}

	public Date getNotifyTime() {
		return notifyTime;
	}

	public void setNotifyTime(Date notifyTime) {
		this.notifyTime = notifyTime;
	}

	public String getNotifyType() {
		return notifyType;
	}

	public void setNotifyType(String notifyType) {
		this.notifyType = notifyType;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.mcp.core.inter.define.Body#validate()
	 */
	@Override
	public void validate() throws CoreException {

	}
}
