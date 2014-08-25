package com.mcp.order.model.jc;

import com.mcp.order.util.JsonDateSerializer;
import org.codehaus.jackson.map.annotate.JsonFilter;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: yeeson
 * Date: 13-12-17
 * Time: 10:00
 * To change this template use File | Settings | File Templates.
 */
//记录赔率信息。赔率信息在不断变动之中，如果有变动，则增加一条记录。
//ID、赔率类型、游戏ID、玩法ID、场次ID、赔率信息、更新时间。
@Entity
@Table(name = "jodds")
@JsonFilter("jodds")
public class JOdds implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2318542969669608191L;

	@Id
    @Column(length = 32)
    private String id;
	
	@Basic
	@Column(length = 40)
    private String oddsCode;
	
	@Basic
	@Column(length = 40)
    private String oddsName;
    
	@Basic
	@Column(length = 10)
	private String gameCode;
	
	@Basic
	@Column(length = 10)
    private String playTypeCode;
	
	@Basic
	@Column(length = 40)
    private String matchCode;
	
	@Basic
	@Column(length = 200)
    private String oddsInfo;
	
	@Basic
    private Date createTime;
	
	@Version
    private int version;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getOddsCode() {
		return oddsCode;
	}

	public void setOddsCode(String oddsCode) {
		this.oddsCode = oddsCode;
	}

	public String getOddsName() {
		return oddsName;
	}

	public void setOddsName(String oddsName) {
		this.oddsName = oddsName;
	}

	public String getGameCode() {
		return gameCode;
	}

	public void setGameCode(String gameCode) {
		this.gameCode = gameCode;
	}

	public String getPlayTypeCode() {
		return playTypeCode;
	}

	public void setPlayTypeCode(String playTypeCode) {
		this.playTypeCode = playTypeCode;
	}

	public String getMatchCode() {
		return matchCode;
	}

	public void setMatchCode(String matchCode) {
		this.matchCode = matchCode;
	}

	public String getOddsInfo() {
		return oddsInfo;
	}

	public void setOddsInfo(String oddsInfo) {
		this.oddsInfo = oddsInfo;
	}

	@JsonSerialize(using=JsonDateSerializer.class)
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}
	
}
