package com.mcp.order.inter.print;

import com.mcp.order.inter.RepBody;
import com.mcp.order.model.ts.Customer;

public class RepP07Body extends RepBody {
	
	private Customer customer;

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	
}
