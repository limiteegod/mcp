package com.mcp.order.inter.print;

import com.mcp.order.inter.RepBody;

import java.util.List;

public class RepP01Body extends RepBody {
	
	private int size;
	
	private List<RepP01Ticket> tickets;

	public List<RepP01Ticket> getTickets() {
		return tickets;
	}

	public void setTickets(List<RepP01Ticket> tickets) {
		this.tickets = tickets;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}
}
