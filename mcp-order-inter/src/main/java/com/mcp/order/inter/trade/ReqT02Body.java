package com.mcp.order.inter.trade;

import com.mcp.core.util.StringUtil;
import com.mcp.order.exception.CoreException;
import com.mcp.order.exception.ErrCode;
import com.mcp.order.inter.ReqBody;

public class ReqT02Body extends ReqBody {
	
	private String orderId;

	public String getOrderId() {
		return orderId;
	}
	
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	@Override
	public void validate() throws CoreException {
		if(StringUtil.isEmpty(orderId))
		{
			throw new CoreException(ErrCode.E2006, ErrCode.codeToMsg(ErrCode.E2006));
		}
	}
}
