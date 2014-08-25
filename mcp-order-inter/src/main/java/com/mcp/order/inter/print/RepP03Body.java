package com.mcp.order.inter.print;

import com.mcp.order.inter.RepBody;

import java.util.List;

public class RepP03Body extends RepBody {
	
	private List<RepP03Ticket> tickets;
	
	private int size;

	public List<RepP03Ticket> getTickets() {
		return tickets;
	}

	public void setTickets(List<RepP03Ticket> tickets) {
		this.tickets = tickets;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}
}
