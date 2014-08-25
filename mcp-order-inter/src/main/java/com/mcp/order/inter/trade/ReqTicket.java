/**
 * 
 */
package com.mcp.order.inter.trade;

/**
 * @author ming.li
 *
 */
public class ReqTicket {
    
    private String betTypeCode;
    
    private String playTypeCode;
    
    private int amount;
	
    private int multiple;
	
    private String numbers;//单式多注之间用;分割。

	public String getBetTypeCode() {
		return betTypeCode;
	}

	public void setBetTypeCode(String betTypeCode) {
		this.betTypeCode = betTypeCode;
	}

	public String getPlayTypeCode() {
		return playTypeCode;
	}

	public void setPlayTypeCode(String playTypeCode) {
		this.playTypeCode = playTypeCode;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public int getMultiple() {
		return multiple;
	}

	public void setMultiple(int multiple) {
		this.multiple = multiple;
	}

	public String getNumbers() {
		return numbers;
	}

	public void setNumbers(String numbers) {
		this.numbers = numbers;
	}
}
