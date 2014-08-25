package com.mcp.order.inter.account;

import com.mcp.core.util.StringUtil;
import com.mcp.order.exception.CoreException;
import com.mcp.order.exception.ErrCode;
import com.mcp.order.inter.ReqBody;

public class ReqA06Body extends ReqBody {
	
	/**
	 * 编码
	 */
	private String code;
	
	/**
	 * 密码
	 */
	private String password;

	public String getCode() {
		return code;
	}
	
	public void setCode(String code) {
		this.code = code;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	@Override
	public void validate() throws CoreException {
		if(StringUtil.isEmpty(code))
		{
			throw new CoreException(ErrCode.E2006, ErrCode.codeToMsg(ErrCode.E2006));
		}
		if(StringUtil.isEmpty(password))
		{
			throw new CoreException(ErrCode.E2006, ErrCode.codeToMsg(ErrCode.E2006));
		}
	}

}
