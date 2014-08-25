/**
 * 
 */
package com.mcp.scheme.model;

import org.codehaus.jackson.map.annotate.JsonFilter;

import javax.persistence.*;
import java.util.Date;

/**
 * @author ming.li
 *
 */
@Entity
@Table(name = "schemedt")
@JsonFilter("schemedt")
public class SchemeDt implements java.io.Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8757864006501154515L;

	@Id
    @Column(length = 32)
	private String id;
	
	/**
	 * 商户号
	 */
	@Basic
	@Column(length = 10)
	private String channelCode;
	
	/**
	 * 持卡人编号
	 */
	@Basic
	@Column(length = 40)
	private String cardOwnerId;
	
	/**
	 * 外部订单号
	 */
	@Basic
	@Column(length = 40)
	private String outerOrderId;
	
	/**
	 * 投注者姓名
	 */
	@Basic
	@Column(length = 40)
	private String ownerName;

	/**
	 * 证件类型
	 */
	@Basic
	@Column(length = 40)
	private String cardType;
	
	/**
	 * 证件号码
	 */
	@Basic
	@Column(length = 40)
	private String cardNumber;
	
	/**
	 * 手机号码
	 */
	@Basic
	@Column(length = 40)
	private String tel;
	
	/**
	 * 邮箱
	 */
	@Basic
	@Column(length = 40)
	private String email;
	
	/**
	 * 彩种编号
	 */
	@Basic
	@Column(length = 10)
	private String gameCode;
	
	/**
	 * 玩法
	 */
	@Basic
	@Column(length = 10)
	private String playType;
	
	/**
	 * 投注方式
	 */
	@Basic
	@Column(length = 10)
	private String betType;
	
	/**
	 * 选号方式
	 */
	@Basic
	private int selectType;
	
	/**
	 * 号码注数
	 */
	@Basic
	private int nCount;
	
	/**
	 * 投注号码
	 */
	@Basic
	@Column(length = 120)
	private String number;
	
	/**
	 * 倍数
	 */
	@Basic
	private int multiplier;
	
	/**
	 * 定投期数
	 */
	@Basic
	private int termCount;
	
	/**
	 * 定投金额
	 */
	@Basic
	private long amount;
	
	/**
	 * 是否续订
	 */
	@Basic
	private int renewable;
	
	/**
	 * 银行网点代码
	 */
	@Basic
	@Column(length = 40)
	private String branchBankId;

	/**
	 * 经办员代码
	 */
	@Basic
	@Column(length = 40)
	private String officerId;
	
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
	
	
	@Basic
	private int status;
	
	@Basic
	private long bonus = 0;
	
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
	 * 所属用户
	 */
	@Basic
	@Column(length = 32)
	private String customerId;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getChannelCode() {
		return channelCode;
	}

	public void setChannelCode(String channelCode) {
		this.channelCode = channelCode;
	}

	public String getCardOwnerId() {
		return cardOwnerId;
	}

	public void setCardOwnerId(String cardOwnerId) {
		this.cardOwnerId = cardOwnerId;
	}

	public String getOuterOrderId() {
		return outerOrderId;
	}

	public void setOuterOrderId(String outerOrderId) {
		this.outerOrderId = outerOrderId;
	}

	public String getOwnerName() {
		return ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getGameCode() {
		return gameCode;
	}

	public void setGameCode(String gameCode) {
		this.gameCode = gameCode;
	}

	public String getPlayType() {
		return playType;
	}

	public void setPlayType(String playType) {
		this.playType = playType;
	}

	public String getBetType() {
		return betType;
	}

	public void setBetType(String betType) {
		this.betType = betType;
	}

	public int getSelectType() {
		return selectType;
	}

	public void setSelectType(int selectType) {
		this.selectType = selectType;
	}

	public int getnCount() {
		return nCount;
	}

	public void setnCount(int nCount) {
		this.nCount = nCount;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public int getMultiplier() {
		return multiplier;
	}

	public void setMultiplier(int multiplier) {
		this.multiplier = multiplier;
	}

	public int getTermCount() {
		return termCount;
	}

	public void setTermCount(int termCount) {
		this.termCount = termCount;
	}

	public long getAmount() {
		return amount;
	}

	public void setAmount(long amount) {
		this.amount = amount;
	}

	public int getRenewable() {
		return renewable;
	}

	public void setRenewable(int renewable) {
		this.renewable = renewable;
	}

	public String getBranchBankId() {
		return branchBankId;
	}

	public void setBranchBankId(String branchBankId) {
		this.branchBankId = branchBankId;
	}

	public String getOfficerId() {
		return officerId;
	}

	public void setOfficerId(String officerId) {
		this.officerId = officerId;
	}

	public String getStationId() {
		return stationId;
	}

	public void setStationId(String stationId) {
		this.stationId = stationId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

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

	public long getBonus() {
		return bonus;
	}

	public void setBonus(long bonus) {
		this.bonus = bonus;
	}

	public String getStartTermCode() {
		return startTermCode;
	}

	public void setStartTermCode(String startTermCode) {
		this.startTermCode = startTermCode;
	}

	public String getCurTermCode() {
		return curTermCode;
	}

	public void setCurTermCode(String curTermCode) {
		this.curTermCode = curTermCode;
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

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	
}
