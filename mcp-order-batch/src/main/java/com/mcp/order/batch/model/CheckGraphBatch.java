/**
 * 
 */
package com.mcp.order.batch.model;

/**
 * @author ming.li
 *
 */
public class CheckGraphBatch {
	
	private long bonusCount;
	
	private long bonus;
	
	private String channelCode;
	
	public CheckGraphBatch(String channelCode, long bonusCount, long bonus)
	{
		this.channelCode = channelCode;
		this.bonus = bonus;
		this.bonusCount = bonusCount;
	}

	public long getBonusCount() {
		return bonusCount;
	}

	public void setBonusCount(long bonusCount) {
		this.bonusCount = bonusCount;
	}

	public long getBonus() {
		return bonus;
	}

	public void setBonus(long bonus) {
		this.bonus = bonus;
	}

	public String getChannelCode() {
		return channelCode;
	}

	public void setChannelCode(String channelCode) {
		this.channelCode = channelCode;
	}
	
	public String toGraphString()
	{
		return this.channelCode + "," + this.bonusCount + "," + this.bonus;
	}
}
