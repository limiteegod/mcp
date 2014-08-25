package com.mcp.order.inter.trade;

import com.mcp.order.inter.RepBody;
import org.codehaus.jackson.map.annotate.JsonFilter;

@JsonFilter("repBody")
public class RepT06Body extends RepBody {
	
	private RepStation station;
	
	private RepOrder order;

	public RepOrder getOrder() {
		return order;
	}

	public void setOrder(RepOrder order) {
		this.order = order;
	}

	public RepStation getStation() {
		return station;
	}

	public void setStation(RepStation station) {
		this.station = station;
	}
}
