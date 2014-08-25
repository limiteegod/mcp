/**
 * 
 */
package com.mcp.order.model.ts;

import org.codehaus.jackson.map.annotate.JsonFilter;

import javax.persistence.*;
import java.util.Date;

/**
 * @author ming.li
 *
 */
@Entity
@Table(name = "withdraw")
@JsonFilter("withdraw")
public class WithDraw implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8111211197918802052L;
	
	@Id
    @Column(length = 32)
	private String id;

	@Basic
    @Column(length = 32)
	private String customerId;
	
	@Basic
    @Column(length = 32)
	private String moneyLogId;
	
	/**
	 * 提款的类型
	 */
	@Basic
	private int type;
	
	@Basic
	private Date startTime;
	
	@Basic
	private Date endTime;
	
	@Basic
	@Column(length = 80)
	private String cardNumber;
	
	@Basic
	private long amount;
	
	@Basic
	@Column(length = 80)
	private String desp;
	
	@Basic
	private int status;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getMoneyLogId() {
		return moneyLogId;
	}

	public void setMoneyLogId(String moneyLogId) {
		this.moneyLogId = moneyLogId;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	public long getAmount() {
		return amount;
	}

	public void setAmount(long amount) {
		this.amount = amount;
	}

	public String getDesp() {
		return desp;
	}

	public void setDesp(String desp) {
		this.desp = desp;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
}
