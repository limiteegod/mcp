/**
 * 
 */
package com.mcp.scheme.model;

import com.mcp.order.common.Constants;
import com.mcp.order.util.JsonDateSerializer;
import org.codehaus.jackson.map.annotate.JsonFilter;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import javax.persistence.*;
import java.util.Date;

/**
 * 追号方案
 * @author ming.li
 */
@Entity
@Table(name = "schemezh")
@JsonFilter("schemezh")
public class SchemeZh implements java.io.Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 836572051389092183L;

	@Id
    @Column(length = 32)
	private String id;
	
	/**
	 * 用户的id
	 */
	@Basic
	@Column(length = 32)
	private String customerId;
	
	/**
	 * 商户号
	 */
	@Basic
	@Column(length = 10)
	private String channelCode;
	
	/**
	 * 彩种编号
	 */
	@Basic
	@Column(length = 10)
	private String gameCode;
	
	/**
	 * 号码列表，各字段以~分隔，玩法~投注方式~号码~金额~倍数!玩法~投注方式~号码~金额~倍数
	 */
	@Basic
	@Column(length = 400)
	private String numList;
	
	/**
	 * 金额
	 */
	@Basic
	private long amount;
	
	/**
	 * 投注站id
	 */
	@Basic
	@Column(length = 32)
	private String stationId;
	
	/**
	 * 创建时间
	 */
	@Basic
	private Date createTime;
	
	/**
	 * 接受时间
	 */
	@Basic
	private Date acceptTime;
	
	/**
	 * 总订单数
	 */
	@Basic
	private int orderCount;
	
	/**
	 * 已经完成的订单数
	 */
	@Basic
	private int finishedOrderCount;

    /**
     * 取消的期次数
     */
    @Basic
    private int cancelOrderCount;
	
	/**
	 * 方案的状态
	 */
	@Basic
	private int status;
	
	/**
	 * 开始的期次
	 */
	@Basic
	@Column(length = 40)
	private String startTermCode;
	
	/**
	 * 当前已经处理的期次号，比如期次2013001算奖之后，此时，这个字段为2013001，生成2013002期的追号订单，然后更新这个字段为2013002
	 */
	@Basic
	@Column(length = 40)
	private String curTermCode;
	
	@Basic
	@Column(length = 40)
	private String platform;

    /**
     * 支付方式
     */
	@Basic
	private int payType;
	
	/**
	 * 中奖后是否停追
	 */
	@Basic
	private boolean winStop;
	
	/**
	 * 方案的中奖金额
	 */
	@Basic
	private long bonus;

    /**
     * 税前奖金
     */
    @Basic
    private long bonusBeforeTax;
	
	@Basic
	@Column(length = 32)
	private String printStationId;

    public long getBonusBeforeTax() {
        return bonusBeforeTax;
    }

    public void setBonusBeforeTax(long bonusBeforeTax) {
        this.bonusBeforeTax = bonusBeforeTax;
    }

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

	public long getAmount() {
		return amount;
	}

	public void setAmount(long amount) {
		this.amount = amount;
	}

	public String getStationId() {
		return stationId;
	}

	public void setStationId(String stationId) {
		this.stationId = stationId;
	}

	@JsonSerialize(using=JsonDateSerializer.class)
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@JsonSerialize(using=JsonDateSerializer.class)
	public Date getAcceptTime() {
		return acceptTime;
	}

	public void setAcceptTime(Date acceptTime) {
		this.acceptTime = acceptTime;
	}

	public int getOrderCount() {
		return orderCount;
	}

	public void setOrderCount(int orderCount) {
		this.orderCount = orderCount;
	}

	public int getFinishedOrderCount() {
		return finishedOrderCount;
	}

	public void setFinishedOrderCount(int finishedOrderCount) {
		this.finishedOrderCount = finishedOrderCount;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getStartTermCode() {
		return startTermCode;
	}

	public void setStartTermCode(String startTermCode) {
		this.startTermCode = startTermCode;
	}

	public String getPlatform() {
		return platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
	}

	public int getPayType() {
		return payType;
	}

	public void setPayType(int payType) {
		this.payType = payType;
	}

	public String getCurTermCode() {
		return curTermCode;
	}

	public void setCurTermCode(String curTermCode) {
		this.curTermCode = curTermCode;
	}

	public boolean isWinStop() {
		return winStop;
	}

	public void setWinStop(boolean winStop) {
		this.winStop = winStop;
	}

	public long getBonus() {
		return bonus;
	}

	public void setBonus(long bonus) {
		this.bonus = bonus;
	}

	public String getNumList() {
		return numList;
	}

	public void setNumList(String numList) {
		this.numList = numList;
	}
    
	public String getPrintStationId() {
		return printStationId;
	}

	public void setPrintStationId(String printStationId) {
		this.printStationId = printStationId;
	}

    public int getCancelOrderCount() {
        return cancelOrderCount;
    }

    public void setCancelOrderCount(int cancelOrderCount) {
        this.cancelOrderCount = cancelOrderCount;
    }

    public String toString()
	{
		StringBuffer sb = new StringBuffer();
		sb.append(id);
		sb.append(Constants.SEP_SMILE);
		sb.append(customerId);
		sb.append(Constants.SEP_SMILE);
		sb.append(channelCode);
		sb.append(Constants.SEP_SMILE);
		sb.append(gameCode);
		sb.append(Constants.SEP_SMILE);
		sb.append(numList);
		sb.append(Constants.SEP_SMILE);
		sb.append(amount);
		sb.append(Constants.SEP_SMILE);
		sb.append(stationId);
		sb.append(Constants.SEP_SMILE);
		sb.append(orderCount);
		sb.append(Constants.SEP_SMILE);
		sb.append(finishedOrderCount);
		sb.append(Constants.SEP_SMILE);
		sb.append(status);
		sb.append(Constants.SEP_SMILE);
		sb.append(startTermCode);
		sb.append(Constants.SEP_SMILE);
		sb.append(platform);
		sb.append(Constants.SEP_SMILE);
		sb.append(payType);
		return sb.toString();
	}
}
