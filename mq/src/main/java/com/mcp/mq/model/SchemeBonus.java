/**
 * 
 */
package com.mcp.mq.model;

/**
 * 方案中奖消息，当方案所拥有的订单中奖之后，订单引擎会发送方案中奖消息给方案引擎
 * @author ming.li
 */
public class SchemeBonus {
	
	/**
	 * 方案的id
	 */
	private String schemeId;
	
	/**
	 * 游戏代码
	 */
	private String gameCode;
	
	/**
	 * 期次代码
	 */
	private String termCode;
	
	/**
	 * 方案的类型
	 */
	private int schemeType;
	
	/**
	 * 方案的中奖金额
	 */
	private long bonus;

	public String getSchemeId() {
		return schemeId;
	}

	public void setSchemeId(String schemeId) {
		this.schemeId = schemeId;
	}

	public int getSchemeType() {
		return schemeType;
	}

	public void setSchemeType(int schemeType) {
		this.schemeType = schemeType;
	}

	public long getBonus() {
		return bonus;
	}

	public void setBonus(long bonus) {
		this.bonus = bonus;
	}

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
