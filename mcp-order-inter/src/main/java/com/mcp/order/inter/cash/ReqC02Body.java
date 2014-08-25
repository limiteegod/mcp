package com.mcp.order.inter.cash;

import com.mcp.core.util.StringUtil;
import com.mcp.order.exception.CoreException;
import com.mcp.order.exception.ErrCode;
import com.mcp.order.inter.ReqBody;

/**
 * 用户充值到投注金
 * @author ming.li
 */
public class ReqC02Body extends ReqBody {
	
	/**
	 * 要充值的金额
	 */
	private long amount;
	
	/**
	 * 要充值的用户
	 */
	private String name;
	
	/**
	 * 充值的方式
	 */
	private int fromType;
	
	/**
	 * 充值时的外部订单号
	 */
	private String orderId;
	
	public long getAmount() {
		return amount;
	}

	public void setAmount(long amount) {
		this.amount = amount;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getFromType() {
		return fromType;
	}

	public void setFromType(int fromType) {
		this.fromType = fromType;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	@Override
	public void validate() throws CoreException {
		if(amount <= 0 || StringUtil.isEmpty(name) || StringUtil.isEmpty(orderId))
		{
			throw new CoreException(ErrCode.E0003, ErrCode.codeToMsg(ErrCode.E0003));
		}
	}
}
