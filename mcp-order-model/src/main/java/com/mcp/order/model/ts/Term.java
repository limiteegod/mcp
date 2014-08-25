package com.mcp.order.model.ts;

import com.mcp.order.model.jc.JOdds;
import com.mcp.order.util.JsonDateSerializer;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.map.annotate.JsonFilter;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import javax.persistence.*;
import java.util.Date;
import java.util.Map;

/**
 * User: yeeson he
 * Date: 13-7-17
 * Time: 上午10:49
 * To be the best of me!
 */
@Entity
@Table(name="term")
@JsonFilter("term")
public class Term implements java.io.Serializable {
	
    /**
	 * 
	 */
	private static final long serialVersionUID = -6947473627646129654L;

//    彩种期次。用于常规彩种。
    //结构化的数据使用json存取。
	@Id
    @Column(length=32)
    private String id;
	
	@Basic
	@Column(length = 40)
    private String code;
	
	@Basic
	@Column(length = 40)
    private String nextCode;  //主要用于更新下一期奖池。以及期次导航。
	
	@Basic
	@Column(length = 40)
    private String name;
	
	@Basic
	@Column(length = 10)
    private String gameCode;
	
	@Basic
    private int prizePool;
	
	@Basic
    private Date createTime;
	
	@Basic
    private Date openTime;
	
	@Basic
    private Date endTime;
	
	@Basic
	@Column(length = 40)
    private String winningNumber;
	
	@Basic
	@Column(length=2048)
    private String prizeDesc;//本期开奖结果描述。使用json格式表示。

	@Basic
    private int status;
	
	@Version
	private int version;
	
	/**
	 * 详细信息，竞彩存储了对阵的详细信息，以|分隔，联赛代码|联赛名称|主队代码|主队名称|客队代码|客队名称
	 */
	@Basic
	@Column(length = 560)
	private String detailInfo;
	
	/**
	 * 仅争对竞彩，让球数
	 */
	@Basic
    private int concedePoints;
	
	/**
	 * 比赛时间
	 */
	@Basic
    private Date matchTime;
	
	/**
	 * key为oddsCode+playTypeCode，记录各分组的赔率信息
	 */
	@Transient
	private Map<String, JOdds> oddsMap;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getNextCode() {
		return nextCode;
	}

	public void setNextCode(String nextCode) {
		this.nextCode = nextCode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGameCode() {
		return gameCode;
	}

	public void setGameCode(String gameCode) {
		this.gameCode = gameCode;
	}

	public int getPrizePool() {
		return prizePool;
	}

	public void setPrizePool(int prizePool) {
		this.prizePool = prizePool;
	}

	@JsonIgnore
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@JsonSerialize(using=JsonDateSerializer.class)
	public Date getOpenTime() {
		return openTime;
	}

	public void setOpenTime(Date openTime) {
		this.openTime = openTime;
	}

	@JsonSerialize(using=JsonDateSerializer.class)
	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getWinningNumber() {
		return winningNumber;
	}

	public void setWinningNumber(String winningNumber) {
		this.winningNumber = winningNumber;
	}

	public String getPrizeDesc() {
		return prizeDesc;
	}

	public void setPrizeDesc(String prizeDesc) {
		this.prizeDesc = prizeDesc;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public String getDetailInfo() {
		return detailInfo;
	}

	public void setDetailInfo(String detailInfo) {
		this.detailInfo = detailInfo;
	}

	public int getConcedePoints() {
		return concedePoints;
	}

	public void setConcedePoints(int concedePoints) {
		this.concedePoints = concedePoints;
	}

	@JsonSerialize(using=JsonDateSerializer.class)
	public Date getMatchTime() {
		return matchTime;
	}

	public void setMatchTime(Date matchTime) {
		this.matchTime = matchTime;
	}

	public Map<String, JOdds> getOddsMap() {
		return oddsMap;
	}

	public void setOddsMap(Map<String, JOdds> oddsMap) {
		this.oddsMap = oddsMap;
	}
}
