package com.mcp.order.inter.trade;

import java.util.List;

public class ReqOrder {
	
    private String termCode;
    
    private String schemeId; //对应的TSchema的id，可以为空。
    
    private String stationId;
    
    private String gameCode;//对应的彩种编号。
    
    private int type;//0，普通，1、发起合买认购，2、合买认购，3、自动跟单，4、保底转认购，5，系统保底，6、先发起后上传，7、单式上传
    
    private int amount;         //订单总额。
    
    private String platform;   //来源平台。android、ios等。
    
    private int multiple = 1;
    
    private String outerId;  //外部id，供存储第三方销售系统的订单号。可以为空。
    
    //竞彩使用
    private String number;
    
    //竞彩使用
    private String playType;
    
    //竞彩使用
    private String betType;
    
    //票
    private List<ReqTicket> tickets;

	public List<ReqTicket> getTickets() {
		return tickets;
	}

	public void setTickets(List<ReqTicket> tickets) {
		this.tickets = tickets;
	}

	public String getTermCode() {
		return termCode;
	}

	public void setTermCode(String termCode) {
		this.termCode = termCode;
	}

	public String getSchemeId() {
		return schemeId;
	}

	public void setSchemeId(String schemeId) {
		this.schemeId = schemeId;
	}

	public String getStationId() {
		return stationId;
	}

	public void setStationId(String stationId) {
		this.stationId = stationId;
	}

	public String getGameCode() {
		return gameCode;
	}

	public void setGameCode(String gameCode) {
		this.gameCode = gameCode;
	}

	public String getPlatform() {
		return platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
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

	public String getOuterId() {
		return outerId;
	}

	public void setOuterId(String outerId) {
		this.outerId = outerId;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getPlayType() {
		return playType;
	}

	public void setPlayType(String playType) {
		this.playType = playType;
	}

	public String getBetType() {
		return betType;
	}

	public void setBetType(String betType) {
		this.betType = betType;
	}
}
