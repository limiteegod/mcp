/**
 * 
 */
package com.mcp.order.batch.model;

/**
 * @author ming.li
 *
 */
public class TicketBatch {
	
	private String id;
	
	/**
	 * 订单号
	 */
	private String orderId;
	
	/**
	 * 用户的id
	 */
	private String customerId;
	
	/**
	 * 投注站的id
	 */
	private String stationId;
	
	//玩法
	private String playTypeCode;
	
	//投注方式
	private String betTypeCode;
	
	//号码
	private String numbers;
	
	//单注金额
	private int price;
	
	//倍数
	private int multiple;
	
	//是否中奖
	private boolean win;
	
	//是否大奖
	private boolean big;
	
	//中奖金额
	private long prize;
	
	/**
	 * 是否返奖
	 */
	private boolean prized;
	
	private int status;
	
	/**
	 * 竞彩需要用这个字段算奖
	 */
	private String rNumber;
	
	/**
	 * 游戏代码
	 */
	private String gameCode;
	
	/**
	 * 期次号码
	 */
	private String termCode;

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public boolean isWin() {
		return win;
	}

	public void setWin(boolean win) {
		this.win = win;
	}

	public boolean isBig() {
		return big;
	}

	public void setBig(boolean big) {
		this.big = big;
	}

	public long getPrize() {
		return prize;
	}

	public void setPrize(long prize) {
		this.prize = prize;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPlayTypeCode() {
		return playTypeCode;
	}

	public void setPlayTypeCode(String playTypeCode) {
		this.playTypeCode = playTypeCode;
	}

	public String getBetTypeCode() {
		return betTypeCode;
	}

	public void setBetTypeCode(String betTypeCode) {
		this.betTypeCode = betTypeCode;
	}

	public String getNumbers() {
		return numbers;
	}

	public void setNumbers(String numbers) {
		this.numbers = numbers;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public int getMultiple() {
		return multiple;
	}

	public void setMultiple(int multiple) {
		this.multiple = multiple;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getStationId() {
		return stationId;
	}

	public void setStationId(String stationId) {
		this.stationId = stationId;
	}

	public boolean getPrized() {
		return prized;
	}

	public void setPrized(boolean prized) {
		this.prized = prized;
	}

	public String getrNumber() {
		return rNumber;
	}

	public void setrNumber(String rNumber) {
		this.rNumber = rNumber;
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
