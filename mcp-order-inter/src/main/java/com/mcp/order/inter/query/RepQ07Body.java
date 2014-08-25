package com.mcp.order.inter.query;

import com.mcp.order.inter.RepBody;
import com.mcp.order.util.JsonDateSerializer;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.util.Date;


public class RepQ07Body extends RepBody {
	
	private Date time;

	@JsonSerialize(using=JsonDateSerializer.class)
	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}
}
