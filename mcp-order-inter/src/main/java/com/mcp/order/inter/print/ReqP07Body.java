package com.mcp.order.inter.print;

import com.mcp.core.util.StringUtil;
import com.mcp.order.exception.CoreException;
import com.mcp.order.exception.ErrCode;
import com.mcp.order.inter.ReqBody;
import com.mcp.order.model.ts.Customer;


public class ReqP07Body extends ReqBody {
	
	private Customer customer;

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	@Override
	public void validate() throws CoreException {
		if(StringUtil.isEmpty(customer.getName()))
		{
			throw new CoreException(ErrCode.E0003, ErrCode.codeToMsg(ErrCode.E0003));
		}
	}
}
