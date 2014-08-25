package com.mcp.order.model.admin;

import javax.persistence.*;

/**
 * User: yeeson he
 * Date: 13-7-18
 * Time: 下午12:04
 * To be the best of me!
 */
@Entity
@Table(name = "stationgameterminal")
public class StationGameTerminal implements java.io.Serializable {
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 4250765167479536711L;

	//彩票站彩种与终端机的多对多的关系。
	@Id
	@Column(length=32)
    private String id;    //!
	
	@Basic
	@Column(length=32)
    private String stationGameId;      //!
	
	@Basic
	@Column(length=32)
    private String terminalId;      //!

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTerminalId() {
		return terminalId;
	}

	public void setTerminalId(String terminalId) {
		this.terminalId = terminalId;
	}

	public String getStationGameId() {
		return stationGameId;
	}

	public void setStationGameId(String stationGameId) {
		this.stationGameId = stationGameId;
	}
	
}
