package com.mcp.order.inter.print;

import com.mcp.order.inter.RepBody;

public class RepP02Body extends RepBody {
	
	private String ticketId;
	
	private long amount;
	
	private int code;

	public String getTicketId() {
		return ticketId;
	}

	public void setTicketId(String ticketId) {
		this.ticketId = ticketId;
	}
	
	public long getAmount() {
		return amount;
	}

	public void setAmount(long amount) {
		this.amount = amount;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}
}
