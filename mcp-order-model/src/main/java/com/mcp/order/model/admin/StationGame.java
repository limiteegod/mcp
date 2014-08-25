package com.mcp.order.model.admin;

import javax.persistence.*;
import java.util.Date;

/**
 * User: yeeson he
 * Date: 13-7-18
 * Time: 上午11:40
 * To be the best of me!
 */
@Entity
@Table(name="stationgame")
public class StationGame implements java.io.Serializable {
	
    /**
	 * 
	 */
	private static final long serialVersionUID = -8149550912015305520L;
	

	//本站点可销售的彩种，以及彩种的各种配置。
//    彩站自己的对于彩种的本地配置参数均在此。
	@Id
    @Column(length=32)
    private String id;    //!
	
	@Basic
	@Column(length=32)
    private String stationId;//对应的彩票站ID。  //!
	
	@Basic
	@Column(length=32)
    private String relayToId;//转发到的站点名称。用于转票使用。
	
	@Basic
    private Date relayToExpired;//转入失效时间。
	
	@Basic
	@Column(length = 20)
    private String gameCode;
	
	@Basic
    private int earlyStopBufferSimplex;  //单式投注截止提前时间，单位为分钟。
	
	@Basic
    private int earlyStopBufferDuplex;  //复式投注截止提前时间，单位为分钟。
	
	@Basic
    private int status;//当前状态   ！ 0 可用 1 不可用
	
	/**
	 * 转票提成，10000表示100%
	 */
	@Basic
	private int rFactor;
	
	/**
	 * 出票提成，10000表示100%
	 */
	@Basic
	private int pFactor;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getStationId() {
		return stationId;
	}

	public void setStationId(String stationId) {
		this.stationId = stationId;
	}

	public String getRelayToId() {
		return relayToId;
	}

	public void setRelayToId(String relayToId) {
		this.relayToId = relayToId;
	}

	public Date getRelayToExpired() {
		return relayToExpired;
	}

	public void setRelayToExpired(Date relayToExpired) {
		this.relayToExpired = relayToExpired;
	}

	public String getGameCode() {
		return gameCode;
	}

	public void setGameCode(String gameCode) {
		this.gameCode = gameCode;
	}

	public int getEarlyStopBufferSimplex() {
		return earlyStopBufferSimplex;
	}

	public void setEarlyStopBufferSimplex(int earlyStopBufferSimplex) {
		this.earlyStopBufferSimplex = earlyStopBufferSimplex;
	}

	public int getEarlyStopBufferDuplex() {
		return earlyStopBufferDuplex;
	}

	public void setEarlyStopBufferDuplex(int earlyStopBufferDuplex) {
		this.earlyStopBufferDuplex = earlyStopBufferDuplex;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getrFactor() {
		return rFactor;
	}

	public void setrFactor(int rFactor) {
		this.rFactor = rFactor;
	}

	public int getpFactor() {
		return pFactor;
	}

	public void setpFactor(int pFactor) {
		this.pFactor = pFactor;
	}
}
