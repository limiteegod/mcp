package com.mcp.order.inter.print;

import com.mcp.order.exception.CoreException;
import com.mcp.order.inter.ReqBody;
import com.mcp.order.model.ts.TOrder;

public class ReqP08Body extends ReqBody {
	
	private TOrder order;

	public TOrder getOrder() {
		return order;
	}

	public void setOrder(TOrder order) {
		this.order = order;
	}

	@Override
	public void validate() throws CoreException {
		
	}
}
