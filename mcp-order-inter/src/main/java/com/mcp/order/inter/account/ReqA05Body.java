package com.mcp.order.inter.account;

import com.mcp.order.exception.CoreException;
import com.mcp.order.inter.ReqBody;

public class ReqA05Body extends ReqBody {
	
	private String nickyName;

	private String phone;

	private String email;

	private String realName;

	private String identityId;
	
	/**
	 * 校验码
	 */
	private String authCode;
	
	/**
	 * 是否需要校验码，九歌自营客户端绑定手机需要校验码
	 */
	private boolean needAuthCode;
	
	private String cardNumber;

	public String getNickyName() {
		return nickyName;
	}

	public void setNickyName(String nickyName) {
		this.nickyName = nickyName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	public String getAuthCode() {
		return authCode;
	}

	public void setAuthCode(String authCode) {
		this.authCode = authCode;
	}

	public boolean isNeedAuthCode() {
		return needAuthCode;
	}

	public void setNeedAuthCode(boolean needAuthCode) {
		this.needAuthCode = needAuthCode;
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
