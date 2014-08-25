package com.mcp.order.inter.jbank;

import com.mcp.core.util.StringUtil;
import com.mcp.order.exception.CoreException;
import com.mcp.order.exception.ErrCode;
import com.mcp.order.inter.ReqBody;

public class ReqJ01Body extends ReqBody {
	
	/**
	 * 用户名
	 */
	private String name;
	
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
	
	/**
	 * 彩金
	 */
    private long recharge;
	
	/**
	 * 用户的奖金账户
	 */
	private long prize;
	
	/**
	 * 不存在此用户时，是否创建
	 */
	private boolean createWhenNotExist = true;

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

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

	public boolean isCreateWhenNotExist() {
		return createWhenNotExist;
	}

	public void setCreateWhenNotExist(boolean createWhenNotExist) {
		this.createWhenNotExist = createWhenNotExist;
	}

	@Override
	public void validate() throws CoreException {
		if(StringUtil.isEmpty(name) || StringUtil.isEmpty(password))
		{
			throw new CoreException(ErrCode.E2006, ErrCode.codeToMsg(ErrCode.E2006));
		}
	}
}
