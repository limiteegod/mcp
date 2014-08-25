package com.mcp.order.inter.print;

import com.mcp.order.exception.CoreException;
import com.mcp.order.inter.ReqBody;

public class ReqP03Body extends ReqBody {
	
private String terminalCode;
	
	private String gameCode;
	
	private String gameTermCode;
	
	private int size;

	public String getTerminalCode() {
		return terminalCode;
	}

	public void setTerminalCode(String terminalCode) {
		this.terminalCode = terminalCode;
	}

	public String getGameTermCode() {
		return gameTermCode;
	}

	public void setGameTermCode(String gameTermCode) {
		this.gameTermCode = gameTermCode;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public String getGameCode() {
		return gameCode;
	}

	public void setGameCode(String gameCode) {
		this.gameCode = gameCode;
	}

	@Override
	public void validate() throws CoreException {
		
	}
}
