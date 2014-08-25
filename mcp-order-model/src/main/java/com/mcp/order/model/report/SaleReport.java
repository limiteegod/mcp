package com.mcp.order.model.report;

/*
 * User: yeeson he
 * Date: 13-11-11
 * Time: 上午11:51
 */

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "salereport")
public class SaleReport implements java.io.Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1526965446318762998L;

	@Id
    @Column(length = 32)
    private String id;
	
	@Basic
    private int scope; //  统计口径，订单
	
	@Basic
	@Column(length = 40)
    private String period; //统计时间段
	
	@Basic
    private int reportType; //报表类型
	
	@Basic
    private int clientType = -1; //客户端类型。不分类型，则置为-1.
	
	@Basic
	@Column(length = 40)
    private String channelCode = "-1"; //代理商编码。不分代理商，则置为“-1”。
	
	@Basic
	@Column(length = 10)
    private String gameCode;  //游戏代码
	
	@Basic
	@Column(length = 40)
    private String termCode; //期次代码
	
	@Basic
    private long total; //总条数
	
	@Basic
    private long totalAmount; //总金额
	
	@Basic
    private long succ; //成功条数
	
	@Basic
    private long succAmount; //成功金额
	
	@Basic
    private long fail; //失败条数
	
	@Basic
    private long failAmount; //失败金额
	
	@Basic
    private long dispute; //争议条数
	
	@Basic
    private long disputeAmount; //争议金额
	
	@Basic
    private long win; //中奖条数
	
	@Basic
    private long winAmount; //中奖金额
	
	@Basic
	@Column(length = 40)
    private String creator; //报表产生来源
	
	@Basic
    private Date createTime; //报表产生时间
	
	@Basic
	@Column(length = 40)
    private String remarks; //备注
	
	@Basic
    private int status; //状态。后期扩展使用，目前统一置为0.

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getScope() {
        return scope;
    }

    public void setScope(int scope) {
        this.scope = scope;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public int getReportType() {
        return reportType;
    }

    public void setReportType(int reportType) {
        this.reportType = reportType;
    }

    public int getClientType() {
        return clientType;
    }

    public void setClientType(int clientType) {
        this.clientType = clientType;
    }

    public String getChannelCode() {
        return channelCode;
    }

    public void setChannelCode(String channelCode) {
        this.channelCode = channelCode;
    }

    public String getGameCode() {
        return gameCode;
    }

    public void setGameCode(String gameCode) {
        this.gameCode = gameCode;
    }

    public String getTermCode() {
        return termCode;
    }

    public void setTermCode(String termCode) {
        this.termCode = termCode;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public long getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(long totalAmount) {
        this.totalAmount = totalAmount;
    }

    public long getSucc() {
        return succ;
    }

    public void setSucc(long succ) {
        this.succ = succ;
    }

    public long getSuccAmount() {
        return succAmount;
    }

    public void setSuccAmount(long succAmount) {
        this.succAmount = succAmount;
    }

    public long getFail() {
        return fail;
    }

    public void setFail(long fail) {
        this.fail = fail;
    }

    public long getFailAmount() {
        return failAmount;
    }

    public void setFailAmount(long failAmount) {
        this.failAmount = failAmount;
    }

    public long getDispute() {
        return dispute;
    }

    public void setDispute(long dispute) {
        this.dispute = dispute;
    }

    public long getDisputeAmount() {
        return disputeAmount;
    }

    public void setDisputeAmount(long disputeAmount) {
        this.disputeAmount = disputeAmount;
    }

    public long getWin() {
        return win;
    }

    public void setWin(long win) {
        this.win = win;
    }

    public long getWinAmount() {
        return winAmount;
    }

    public void setWinAmount(long winAmount) {
        this.winAmount = winAmount;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
