package com.mcp.order.model.mongo;

public class MgJcCheckTicket {
	
	//自增的id
	private long id;
	
	private String ticketId;
	
	/**
     * 普通游戏为期次代码；
     * 竞彩，最后一场比赛的代码，竞彩由场次驱动，当场次通知为票最后一场时，触发相关操作
     */
    private String termCode;
    
    /**
     * 仅供竞彩使用，已经完成的场次数目，完成指：场次已经开奖
     */
    private int finishedCount;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTicketId() {
		return ticketId;
	}

	public void setTicketId(String ticketId) {
		this.ticketId = ticketId;
	}

	public String getTermCode() {
		return termCode;
	}

	public void setTermCode(String termCode) {
		this.termCode = termCode;
	}

	public int getFinishedCount() {
		return finishedCount;
	}

	public void setFinishedCount(int finishedCount) {
		this.finishedCount = finishedCount;
	}
}
