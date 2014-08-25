package com.mcp.order.inter.account;

import com.mcp.core.util.StringUtil;
import com.mcp.order.exception.CoreException;
import com.mcp.order.exception.ErrCode;
import com.mcp.order.inter.ReqBody;
import com.mcp.order.model.ts.Customer;

public class ReqA01Body extends ReqBody {
	
	private String stationId;
	
	private Customer customer;

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public String getStationId() {
		return stationId;
	}

	public void setStationId(String stationId) {
		this.stationId = stationId;
	}

	@Override
	public void validate() throws CoreException {
		if(StringUtil.isEmpty(customer.getName()) || StringUtil.isEmpty(customer.getPassword()))
		{
			throw new CoreException(ErrCode.E2006, ErrCode.codeToMsg(ErrCode.E2006));
		}
	}
}
