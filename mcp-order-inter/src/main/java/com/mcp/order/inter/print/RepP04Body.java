package com.mcp.order.inter.print;

import com.mcp.order.inter.RepBody;

public class RepP04Body extends RepBody {
	
	private String ticketId;
	
	private long bonus;

	public String getTicketId() {
		return ticketId;
	}

	public void setTicketId(String ticketId) {
		this.ticketId = ticketId;
	}

	public long getBonus() {
		return bonus;
	}

	public void setBonus(long bonus) {
		this.bonus = bonus;
	}
}
