package com.mcp.order.inter.cash;

import com.mcp.order.inter.RepBody;

public class RepC03Body extends RepBody {
		
	private long prizeBefore;
	
	private long prizeAfter;

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
}
