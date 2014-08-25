package com.mcp.order.inter.print;

import com.mcp.order.exception.CoreException;
import com.mcp.order.inter.ReqBody;

public class ReqP04Body extends ReqBody {
	
private String ticketId;
	
	private int code;	//出票成功或失败。0 成功 1 失败 

	public String getTicketId() {
		return ticketId;
	}

	public void setTicketId(String ticketId) {
		this.ticketId = ticketId;
	}

	public int getCode() {
		return code;
	}
	
	public void setCode(int code) {
		this.code = code;
	}

	@Override
	public void validate() throws CoreException {
		
	}
}
