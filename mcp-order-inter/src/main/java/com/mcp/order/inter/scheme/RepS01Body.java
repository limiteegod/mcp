package com.mcp.order.inter.scheme;

import com.mcp.order.inter.RepBody;
import com.mcp.order.inter.trade.RepStation;
import com.mcp.scheme.model.SchemeZh;

public class RepS01Body extends RepBody {
	
	private RepStation station; 
	
	private SchemeZh scheme;

	public RepStation getStation() {
		return station;
	}

	public void setStation(RepStation station) {
		this.station = station;
	}

	public SchemeZh getScheme() {
		return scheme;
	}

	public void setScheme(SchemeZh scheme) {
		this.scheme = scheme;
	}
}
