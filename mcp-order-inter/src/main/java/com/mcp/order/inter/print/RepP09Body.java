package com.mcp.order.inter.print;

import com.mcp.order.inter.RepBody;
import com.mcp.order.model.ts.TTicket;

import java.util.List;

public class RepP09Body extends RepBody {
	
	private List<TTicket> ttickets;

	public List<TTicket> getTtickets() {
		return ttickets;
	}

	public void setTtickets(List<TTicket> ttickets) {
		this.ttickets = ttickets;
	}
}
