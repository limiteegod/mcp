package com.mcp.order.inter.jbank;

import com.mcp.order.exception.CoreException;
import com.mcp.order.inter.ReqBody;

public class ReqJ03Body extends ReqBody {

	/**
	 * 密码
	 */
	private String password;

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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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

	@Override
	public void validate() throws CoreException {

	}
}
