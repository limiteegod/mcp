package com.mcp.order.inter.resource;

import com.mcp.core.util.StringUtil;
import com.mcp.order.exception.CoreException;
import com.mcp.order.exception.ErrCode;
import com.mcp.order.inter.ReqBody;

public class ReqR04Body extends ReqBody {

	/**
	 * 用户名
	 */
	private String name;

	/**
	 * 电话号码
	 */
	private String phone;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Override
	public void validate() throws CoreException {
		if(StringUtil.isEmpty(name) || StringUtil.isEmpty(phone))
		{
			throw new CoreException(ErrCode.E0003, ErrCode.codeToMsg(ErrCode.E0003));
		}
	}
}
