package com.mcp.order.inter.jbank;

import com.mcp.order.inter.RepBody;

public class RepJ01Body extends RepBody {
	
	private boolean status;
	
	private RepJ01Customer customer;
	
	private RepJ01Cookie cookie;

	public RepJ01Customer getCustomer() {
		return customer;
	}

	public void setCustomer(RepJ01Customer customer) {
		this.customer = customer;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public RepJ01Cookie getCookie() {
		return cookie;
	}

	public void setCookie(RepJ01Cookie cookie) {
		this.cookie = cookie;
	}
}
