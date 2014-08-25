/**
 * 
 */
package com.mcp.order.inter.print;

import com.mcp.order.util.JsonDateSerializer;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.util.Date;

/**
 * @author ming.li
 *
 */
public class RepP01Ticket {
	
	private String ticketId;
	
	private String orderId;
	
	private String gameCode;
	
	private int termIndex;
	
	private Date termIndexDeadline;
	
	private Date deadline;
	
    private String betType;
    
    private String playType;
    
    private int multiple;
    
    private String numbers;

	public String getTicketId() {
		return ticketId;
	}

	public void setTicketId(String ticketId) {
		this.ticketId = ticketId;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getGameCode() {
		return gameCode;
	}

	public void setGameCode(String gameCode) {
		this.gameCode = gameCode;
	}

	public int getTermIndex() {
		return termIndex;
	}

	public void setTermIndex(int termIndex) {
		this.termIndex = termIndex;
	}

	@JsonSerialize(using=JsonDateSerializer.class)
	public Date getTermIndexDeadline() {
		return termIndexDeadline;
	}

	public void setTermIndexDeadline(Date termIndexDeadline) {
		this.termIndexDeadline = termIndexDeadline;
	}

	@JsonSerialize(using=JsonDateSerializer.class)
	public Date getDeadline() {
		return deadline;
	}

	public void setDeadline(Date deadline) {
		this.deadline = deadline;
	}

	public String getBetType() {
		return betType;
	}

	public void setBetType(String betType) {
		this.betType = betType;
	}

	public String getPlayType() {
		return playType;
	}

	public void setPlayType(String playType) {
		this.playType = playType;
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
