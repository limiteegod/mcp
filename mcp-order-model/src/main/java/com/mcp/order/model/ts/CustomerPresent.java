package com.mcp.order.model.ts;

import javax.persistence.*;
import java.util.Date;

/**
 * TODO 优惠卷只花了部分的情况的处理
 * User: yeeson he Date: 13-7-17 Time: 下午3:22 To be the best
 * of me!
 */
@Entity
@Table(name = "customerpresent")
public class CustomerPresent implements java.io.Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 9186549022069981437L;

	// 定向发送的现金优惠券和折扣券。
	@Id
    @Column(length = 32)
	private String id;
	
	@Basic
	@Column(length = 32)
	private String customerId;
	
	@Basic
	@Column(length = 32)
	private String providerId;
	
	@Basic
	private int presentType;// 0 现金券 1 折扣券
	
	@Basic
	@Column(length = 40)
	private String acceptedGameCodes;// 可以使用的彩种类型列表。0000代表全部彩种可用。
	
	@Basic
	@Column(length = 40)
	private String providerCode; // 发行方代码
	
	@Basic
	@Column(length = 40)
	private String providerType; // 发行方类型  0 平台 1 投注站
	
	@Basic
	@Column(length = 40)
	private String presentDescTitle; // 礼品券描述标题
	
	@Basic
	@Column(length = 40)
	private String presentDescText; // 礼品券描述正文
	
	@Basic
	private int minorSpend;// 最低消费标准。在此标准之上才可享受折扣。
	
	@Basic
	private int present;// 礼卷金额
	
	@Basic
	private int presentRatio;// 礼卷金额
	
	@Basic
	private Date presentTime; // 发放日期
	
	@Basic
	private Date activeTime; // 生效日期
	
	@Basic
	private Date expiredTime; // 过期时间
	
	@Basic
	private int status; // d0 未到期 1 可以使用 2 过期 3 已经使用

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

	public String getProviderId() {
		return providerId;
	}

	public void setProviderId(String providerId) {
		this.providerId = providerId;
	}

	public int getPresentType() {
		return presentType;
	}

	public void setPresentType(int presentType) {
		this.presentType = presentType;
	}

	public String getAcceptedGameCodes() {
		return acceptedGameCodes;
	}

	public void setAcceptedGameCodes(String acceptedGameCodes) {
		this.acceptedGameCodes = acceptedGameCodes;
	}

	public String getProviderCode() {
		return providerCode;
	}

	public void setProviderCode(String providerCode) {
		this.providerCode = providerCode;
	}

	public String getProviderType() {
		return providerType;
	}

	public void setProviderType(String providerType) {
		this.providerType = providerType;
	}

	public String getPresentDescTitle() {
		return presentDescTitle;
	}

	public void setPresentDescTitle(String presentDescTitle) {
		this.presentDescTitle = presentDescTitle;
	}

	public String getPresentDescText() {
		return presentDescText;
	}

	public void setPresentDescText(String presentDescText) {
		this.presentDescText = presentDescText;
	}

	public int getMinorSpend() {
		return minorSpend;
	}

	public void setMinorSpend(int minorSpend) {
		this.minorSpend = minorSpend;
	}

	public int getPresent() {
		return present;
	}

	public void setPresent(int present) {
		this.present = present;
	}

	public int getPresentRatio() {
		return presentRatio;
	}

	public void setPresentRatio(int presentRatio) {
		this.presentRatio = presentRatio;
	}

	public Date getPresentTime() {
		return presentTime;
	}

	public void setPresentTime(Date presentTime) {
		this.presentTime = presentTime;
	}

	public Date getActiveTime() {
		return activeTime;
	}

	public void setActiveTime(Date activeTime) {
		this.activeTime = activeTime;
	}

	public Date getExpiredTime() {
		return expiredTime;
	}

	public void setExpiredTime(Date expiredTime) {
		this.expiredTime = expiredTime;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
}
