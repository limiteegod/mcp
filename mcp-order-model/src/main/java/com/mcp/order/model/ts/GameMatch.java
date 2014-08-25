package com.mcp.order.model.ts;

import java.util.Date;

/**
 * User: yeeson he
 * Date: 13-7-17
 * Time: 上午10:44
 * To be the best of me!
 */
public class GameMatch implements java.io.Serializable {
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 5516729545390040288L;
	
	//    比赛场次，竞彩等。
    private String id;
    private String gameMatchType;// 类型。0 竞彩足球 1 竞彩篮球
    private String matchCode;  //内部编号
    private String matchName;  //外部编号，用于显示。比如“周四012”
    private String matchTypeCode; //赛事类型编号
    private String matchTypeName; // 赛事类型名称，比如欧锦赛、亚锦赛等。
    private String hostTeamCode;  //    主队编码
    private String hostTeamName;  //主队名称
    private String guestTeamCode;   //客队编码
    private String guestTeamName;     //客队名称
    private Date addTime;
    private Date openTime;
    private Date endTime;
    private boolean isConcedePoints;//是否让球
    private String odds;//赔率信息，实时更新。用json格式表示。
    private String matchResult;   //比赛结果，用json格式表示。
    private String prizeDesc;//本期开奖结果描述。使用json格式表示。
    private int status;//期次状态。需要完整表示一个期次流程。 // 当前状态。d0 不可用 1 可用
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getGameMatchType() {
		return gameMatchType;
	}
	public void setGameMatchType(String gameMatchType) {
		this.gameMatchType = gameMatchType;
	}
	public String getMatchCode() {
		return matchCode;
	}
	public void setMatchCode(String matchCode) {
		this.matchCode = matchCode;
	}
	public String getMatchName() {
		return matchName;
	}
	public void setMatchName(String matchName) {
		this.matchName = matchName;
	}
	public String getMatchTypeCode() {
		return matchTypeCode;
	}
	public void setMatchTypeCode(String matchTypeCode) {
		this.matchTypeCode = matchTypeCode;
	}
	public String getMatchTypeName() {
		return matchTypeName;
	}
	public void setMatchTypeName(String matchTypeName) {
		this.matchTypeName = matchTypeName;
	}
	public String getHostTeamCode() {
		return hostTeamCode;
	}
	public void setHostTeamCode(String hostTeamCode) {
		this.hostTeamCode = hostTeamCode;
	}
	public String getHostTeamName() {
		return hostTeamName;
	}
	public void setHostTeamName(String hostTeamName) {
		this.hostTeamName = hostTeamName;
	}
	public String getGuestTeamCode() {
		return guestTeamCode;
	}
	public void setGuestTeamCode(String guestTeamCode) {
		this.guestTeamCode = guestTeamCode;
	}
	public String getGuestTeamName() {
		return guestTeamName;
	}
	public void setGuestTeamName(String guestTeamName) {
		this.guestTeamName = guestTeamName;
	}
	public Date getAddTime() {
		return addTime;
	}
	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}
	public Date getOpenTime() {
		return openTime;
	}
	public void setOpenTime(Date openTime) {
		this.openTime = openTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public boolean isConcedePoints() {
		return isConcedePoints;
	}
	public void setConcedePoints(boolean isConcedePoints) {
		this.isConcedePoints = isConcedePoints;
	}
	public String getOdds() {
		return odds;
	}
	public void setOdds(String odds) {
		this.odds = odds;
	}
	public String getMatchResult() {
		return matchResult;
	}
	public void setMatchResult(String matchResult) {
		this.matchResult = matchResult;
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
    
}

