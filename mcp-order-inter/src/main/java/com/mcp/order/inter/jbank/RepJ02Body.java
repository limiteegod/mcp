package com.mcp.order.inter.jbank;

import com.mcp.order.inter.RepBody;

public class RepJ02Body extends RepBody {
	
	private RepJ02Customer customer;

	public RepJ02Customer getCustomer() {
		return customer;
	}

	public void setCustomer(RepJ02Customer customer) {
		this.customer = customer;
	}
}
