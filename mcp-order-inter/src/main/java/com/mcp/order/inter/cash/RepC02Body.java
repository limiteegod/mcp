package com.mcp.order.inter.cash;

import com.mcp.order.inter.RepBody;

public class RepC02Body extends RepBody {
	
	private long rechargeBefore;
	
	private long rechargeAfter;

	public long getRechargeBefore() {
		return rechargeBefore;
	}

	public void setRechargeBefore(long rechargeBefore) {
		this.rechargeBefore = rechargeBefore;
	}

	public long getRechargeAfter() {
		return rechargeAfter;
	}

	public void setRechargeAfter(long rechargeAfter) {
		this.rechargeAfter = rechargeAfter;
	}
	
}
