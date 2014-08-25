package com.mcp.order.inter.jbank;

import com.mcp.core.util.StringUtil;
import com.mcp.order.exception.CoreException;
import com.mcp.order.exception.ErrCode;
import com.mcp.order.inter.ReqBody;

public class ReqJ05Body extends ReqBody {
	
	private String orderId;
	
	private String stationCode;
	
	private int payType;

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getStationCode() {
		return stationCode;
	}

	public void setStationCode(String stationCode) {
		this.stationCode = stationCode;
	}

	public int getPayType() {
		return payType;
	}

	public void setPayType(int payType) {
		this.payType = payType;
	}

	@Override
	public void validate() throws CoreException {
		if(StringUtil.isEmpty(orderId))
		{
			throw new CoreException(ErrCode.E2006, ErrCode.codeToMsg(ErrCode.E2006));
		}
	}
}
