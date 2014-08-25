package com.mcp.order.inter.print;

import com.mcp.order.exception.CoreException;
import com.mcp.order.exception.ErrCode;
import com.mcp.order.inter.ReqBody;

public class ReqP05Body extends ReqBody {
	
	private String code;
	
	private String password;
	
	private int type = -1;
	
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

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	@Override
	public void validate() throws CoreException {
		if(code == null || code.length() == 0)
		{
			throw new CoreException(ErrCode.E2006, ErrCode.codeToMsg(ErrCode.E2006));
		}
		if(password == null || password.length() == 0)
		{
			throw new CoreException(ErrCode.E2006, ErrCode.codeToMsg(ErrCode.E2006));
		}
		if(type < 0)
		{
			throw new CoreException(ErrCode.E2006, ErrCode.codeToMsg(ErrCode.E2006));
		}
	}
}
