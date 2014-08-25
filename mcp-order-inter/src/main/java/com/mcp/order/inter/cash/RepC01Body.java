package com.mcp.order.inter.cash;

import com.mcp.order.inter.RepBody;

public class RepC01Body extends RepBody {
		
	private long prizeBefore;
	
	private long prizeAfter;
	
	private long rechargeBefore;
	
	private long rechargeAfter;

	public long getPrizeBefore() {
		return prizeBefore;
	}

	public void setPrizeBefore(long prizeBefore) {
		this.prizeBefore = prizeBefore;
	}

	public long getPrizeAfter() {
		return prizeAfter;
	}

	public void setPrizeAfter(long prizeAfter) {
		this.prizeAfter = prizeAfter;
	}

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
