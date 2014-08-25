package com.mcp.order.inter.jbank;

import com.mcp.order.inter.RepBody;

public class RepJ03Body extends RepBody {
	
	private boolean status = false;

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}
}
