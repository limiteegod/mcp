/**
 * 
 */
package com.mcp.order.inter.jbank;

/**
 * @author ming.li
 *
 */
public class RepJ02Customer {
	
	/**
	 * 用户名
	 */
	private String name;
	
	/**
	 * 真是姓名
	 */
	private String realName;
	
	/**
	 * 身份证号
	 */
	private String identityId;
	
	/**
	 * 银行卡号
	 */
	private String cardNumber;
	
	/**
	 * 彩金
	 */
    private long recharge;
	
	/**
	 * 用户的奖金账户
	 */
	private long prize;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getIdentityId() {
		return identityId;
	}

	public void setIdentityId(String identityId) {
		this.identityId = identityId;
	}

	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	public long getRecharge() {
		return recharge;
	}

	public void setRecharge(long recharge) {
		this.recharge = recharge;
	}

	public long getPrize() {
		return prize;
	}

	public void setPrize(long prize) {
		this.prize = prize;
	}
}
