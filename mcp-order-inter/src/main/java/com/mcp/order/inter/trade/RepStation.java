/**
 * 
 */
package com.mcp.order.inter.trade;

/**
 * @author ming.li
 *
 */
public class RepStation {
	
	/**
	 * 投注站的id
	 */
	private String id;
	
	/**
	 * 投注站的余额
	 */
	private long recharge;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public long getRecharge() {
		return recharge;
	}

	public void setRecharge(long recharge) {
		this.recharge = recharge;
	}
}
