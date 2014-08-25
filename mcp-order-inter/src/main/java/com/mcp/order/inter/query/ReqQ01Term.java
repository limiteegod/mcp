/**
 * 期次状态查询
 */
package com.mcp.order.inter.query;

/**
 * @author ming.li
 */
public class ReqQ01Term {
	
	private String gameCode;
	
	private String termCode;

	public String getGameCode() {
		return gameCode;
	}

	public void setGameCode(String gameCode) {
		this.gameCode = gameCode;
	}

	public String getTermCode() {
		return termCode;
	}

	public void setTermCode(String termCode) {
		this.termCode = termCode;
	}
}
