package com.mcp.mq.model;

import java.io.Serializable;

public class TermChange implements Serializable {

    private static final long serialVersionUID = 4789509348246997900L;

    private String gameCode;
	
	private String termCode;
	
	/**
	 * 下一期的期号
	 */
	private String nextTermCode;
	
	private int status;

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

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getNextTermCode() {
		return nextTermCode;
	}

	public void setNextTermCode(String nextTermCode) {
		this.nextTermCode = nextTermCode;
	}
	
}
