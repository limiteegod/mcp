package com.mcp.order.model.ts;

import com.mcp.order.model.admin.Station;
import org.codehaus.jackson.map.annotate.JsonFilter;

import javax.persistence.*;

/**
 * User: yeeson he
 * Date: 13-7-16
 * Time: 下午5:05
 * To be the best of me!
 */
@Entity
@Table(name = "customeraccount")
@JsonFilter("customeraccount")
public class CustomerAccount implements java.io.Serializable {
//    彩民的账户，一个账户对应一个投注站。
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2479259070965861426L;

	@Id
    @Column(length = 32)
    private String id;
	
	@Basic
	@Column(length = 32)
    private String customerId;
	
	@Basic
	@Column(length = 32)
    private String stationId;
	
	@Basic
    private long recharge;//彩民在本投注站的现金账户
	
	/**
	 * 用户的奖金账户
	 */
	@Basic
	private long prize; 

	@Basic
    private int integral; //积分

	@Basic
    private int status;  //账户状态。（未定义）d0 可用 1 不可用
	
	@Transient
	private Station station;

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

	public String getStationId() {
		return stationId;
	}

	public void setStationId(String stationId) {
		this.stationId = stationId;
	}

	public long getRecharge() {
		return recharge;
	}

	public void setRecharge(long recharge) {
		this.recharge = recharge;
	}


	public int getIntegral() {
		return integral;
	}

	public void setIntegral(int integral) {
		this.integral = integral;
	}



	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Station getStation() {
		return station;
	}

	public void setStation(Station station) {
		this.station = station;
	}

	public long getPrize() {
		return prize;
	}

	public void setPrize(long prize) {
		this.prize = prize;
	}
	
}
