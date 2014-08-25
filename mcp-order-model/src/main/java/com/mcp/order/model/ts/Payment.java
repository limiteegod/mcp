package com.mcp.order.model.ts;

import javax.persistence.*;
/*
 * User: yeeson he
 * Date: 13-7-27
 * Time: 上午10:08
 */
@Entity
@Table(name = "payment")
public class Payment implements java.io.Serializable {
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1705606878469308776L;

	//支付方式，用于保存彩民的支付方式、返奖方式等。
	@Id
    @Column(length = 32)
    private String id;
	
	@Basic
	@Column(length = 32)
    private String customerId;
	
	@Basic
	@Column(length = 40)
    private String cardType;    //支付卡类型，划分银行卡类型。
	
	@Basic
	@Column(length = 40)
    private String cardNumber;//卡号
	
	/**
	 * 银行代码
	 */
	@Basic
	@Column(length = 40)
	private String bankNo;
	
	/**
	 * 省级代码
	 */
	@Basic
	@Column(length = 40)
	private String provinceNo;
	
	/**
	 * 市级代码
	 */
	@Basic
	@Column(length = 40)
	private String cityNo;
	
	/**
	 * 区县级代码
	 */
	@Basic
	@Column(length = 40)
	private String areaNo;
	
	@Basic
	@Column(length = 40)
	private String remarks;
	
	@Basic
    private int status;//状态位。d0 可用 1 不可用

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

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getBankNo() {
		return bankNo;
	}

	public void setBankNo(String bankNo) {
		this.bankNo = bankNo;
	}

	public String getProvinceNo() {
		return provinceNo;
	}

	public void setProvinceNo(String provinceNo) {
		this.provinceNo = provinceNo;
	}

	public String getCityNo() {
		return cityNo;
	}

	public void setCityNo(String cityNo) {
		this.cityNo = cityNo;
	}

	public String getAreaNo() {
		return areaNo;
	}

	public void setAreaNo(String areaNo) {
		this.areaNo = areaNo;
	}
}
