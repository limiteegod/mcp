package com.mcp.order.inter.account;

import com.mcp.order.exception.CoreException;
import com.mcp.order.exception.ErrCode;
import com.mcp.order.inter.ReqBody;

public class ReqA03Body extends ReqBody {
	
	/**
	 * 密码
	 */
	private String password;
	
	/**
	 * 新密码
	 */
	private String newPassword;
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	@Override
	public void validate() throws CoreException {
		if(this.password == null)
		{
			throw new CoreException(ErrCode.E0003, "password的格式不正确");
		}
		if(this.newPassword == null)
		{
			throw new CoreException(ErrCode.E0003, "新的password的格式不正确");
		}
	}
}
