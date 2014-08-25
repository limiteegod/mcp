package com.mcp.order.inter.jbank;

import com.mcp.order.inter.RepBody;

public class RepJ04Body extends RepBody {
	
	private RepJ04Customer customer;
	
	private RepJ04Cookie cookie;

	public RepJ04Customer getCustomer() {
		return customer;
	}

	public void setCustomer(RepJ04Customer customer) {
		this.customer = customer;
	}

	public RepJ04Cookie getCookie() {
		return cookie;
	}

	public void setCookie(RepJ04Cookie cookie) {
		this.cookie = cookie;
	}
}
