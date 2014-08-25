package com.mcp.order.inter.print;

import com.mcp.order.exception.CoreException;
import com.mcp.order.inter.ReqBody;

public class ReqP02Body extends ReqBody {
	
	private String ticketId;
	
	private String terminalCode;
	
	/**
	 * 票根信息
	 */
	private String stubInfo;
	
	/**
	 * 是否已经打印纸质票
	 */
	private boolean paper;
	
	private String rNumber;
	
	public String getStubInfo() {
		return stubInfo;
	}

	public void setStubInfo(String stubInfo) {
		this.stubInfo = stubInfo;
	}
	
	private int code;	//出票成功或失败。0 成功 1 失败  2 原票返回
	
	public String getTicketId() {
		return ticketId;
	}

	public void setTicketId(String ticketId) {
		this.ticketId = ticketId;
	}
	
	public String getTerminalCode() {
		return terminalCode;
	}

	public void setTerminalCode(String terminalCode) {
		this.terminalCode = terminalCode;
	}
	
	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public boolean isPaper() {
		return paper;
	}

	public void setPaper(boolean paper) {
		this.paper = paper;
	}

	public String getrNumber() {
		return rNumber;
	}

	public void setrNumber(String rNumber) {
		this.rNumber = rNumber;
	}

	@Override
	public void validate() throws CoreException {
		
	}
}
