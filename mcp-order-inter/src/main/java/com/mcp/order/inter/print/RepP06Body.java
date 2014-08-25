package com.mcp.order.inter.print;

import com.mcp.order.inter.RepBody;
import com.mcp.order.model.ts.TTicket;

import java.util.List;

public class RepP06Body extends RepBody {
	
	private List<TTicket> tickets;

	public List<TTicket> getTickets() {
		return tickets;
	}

	public void setTickets(List<TTicket> tickets) {
		this.tickets = tickets;
	}
	
}
