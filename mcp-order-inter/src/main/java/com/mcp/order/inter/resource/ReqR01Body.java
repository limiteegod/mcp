package com.mcp.order.inter.resource;

import com.mcp.core.util.StringUtil;
import com.mcp.order.exception.CoreException;
import com.mcp.order.exception.ErrCode;
import com.mcp.order.inter.ReqBody;

public class ReqR01Body extends ReqBody {
	
	private String phone;

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Override
	public void validate() throws CoreException {
		if(StringUtil.isEmpty(this.phone))
		{
			throw new CoreException(ErrCode.E0003, ErrCode.codeToMsg(ErrCode.E0003));
		}
	}
}
