package com.mcp.order.inter.print;

public class RepP03Ticket {
	
	/**
	 * id
	 */
	private String ticketId;
	
	/**
	 * 票根
	 */
	private String stubInfo;
	
	/**
	 * 号码
	 */
	private String numbers;
	
	/**
	 * 倍数
	 */
	private int multiple;
	
	/**
	 * 中奖金额
	 */
	private long bonus;
	
	/**
	 * 是否有纸质票
	 */
	private boolean paper;

	public String getTicketId() {
		return ticketId;
	}

	public void setTicketId(String ticketId) {
		this.ticketId = ticketId;
	}

	public String getStubInfo() {
		return stubInfo;
	}

	public void setStubInfo(String stubInfo) {
		this.stubInfo = stubInfo;
	}

	public String getNumbers() {
		return numbers;
	}

	public void setNumbers(String numbers) {
		this.numbers = numbers;
	}

	public int getMultiple() {
		return multiple;
	}

	public void setMultiple(int multiple) {
		this.multiple = multiple;
	}

	public long getBonus() {
		return bonus;
	}

	public void setBonus(long bonus) {
		this.bonus = bonus;
	}

	public boolean isPaper() {
		return paper;
	}

	public void setPaper(boolean paper) {
		this.paper = paper;
	}
}
