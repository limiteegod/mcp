/**
 * 
 */
package com.mcp.scheme.model;

import com.mcp.order.common.ConstantValues;
import com.mcp.order.model.ts.TOrder;
import org.codehaus.jackson.map.annotate.JsonFilter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * 合买方案
 * @author ming.li
 */
@Entity
@Table(name = "schemehm")
@JsonFilter("schemehm")
public class SchemeHm implements java.io.Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 836572051389092183L;

	@Id
    @Column(length = 32)
	private String id;
	
	/**
	 * 发起人用户的id
	 */
	@Basic
	@Column(length = 32)
	private String customerId;

    /**
     * 商户编号
     */
    @Basic
    @Column(length = 10)
    private String channelCode;

    /**
     * 销售机构id
     */
    @Basic
    @Column(length = 32)
    private String stationId;
	
	/**
	 * 提成比例
	 */
	@Transient
	private List<SchemeShare> share;
	
	/**
	 * 合买的订单
	 */
	@Transient
	private List<TOrder> orders;
	
	/**
	 * 合买方案的类型，比如普通、赠送等等
	 */
	@Basic
	private int type = ConstantValues.TSchemeHm_Type_Normal.getCode();
	
	/**
	 * 方案的金额，单位分
	 */
	@Basic
	private long amount;

    /**
     * 发起人的初始认购份额
     */
    @Basic
    private long cAmount;

    /**
     * 参与人数目
     */
    @Basic
    private int joinCount;

    /**
     * 发起活动时，指定参与的人数目，默认为3人
     */
    @Basic
    private int tJoinCount = 3;

    /**
     * 已经完成的数目
     */
    @Basic
    private long fAmount;

    /**
     * 创建时间
     */
    @Basic
    private Date createTime;

    /**
     * 系统接受时间
     */
    @Basic
    private Date acceptTime;

    /**
     * 过期时间
     */
    @Basic
    private Date endTime;

    /**
     * 方案状态
     */
    @Basic
    private int status;

    /**
     * 订单数目
     */
    @Basic
    private int orderCount;

    /**
     * 已完成算奖的数目
     */
    @Basic
    private int finishedOrderCount;

    /**
     * 中奖金额
     */
    @Basic
    private long bonus;

    /**
     * 税前奖金
     */
    @Basic
    private long bonusBeforeTax;
	
	/**
	 * 版本管理字段
	 */
	@Version
    private int version;

    public long getBonusBeforeTax() {
        return bonusBeforeTax;
    }

    public void setBonusBeforeTax(long bonusBeforeTax) {
        this.bonusBeforeTax = bonusBeforeTax;
    }

    public int gettJoinCount() {
        return tJoinCount;
    }

    public void settJoinCount(int tJoinCount) {
        this.tJoinCount = tJoinCount;
    }

    public long getBonus() {
        return bonus;
    }

    public void setBonus(long bonus) {
        this.bonus = bonus;
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

    public String getStationId() {
        return stationId;
    }

    public void setStationId(String stationId) {
        this.stationId = stationId;
    }

    public String getChannelCode() {
        return channelCode;
    }

    public void setChannelCode(String channelCode) {
        this.channelCode = channelCode;
    }

    public int getJoinCount() {
        return joinCount;
    }

    public void setJoinCount(int joinCount) {
        this.joinCount = joinCount;
    }

    public long getfAmount() {
        return fAmount;
    }

    public void setfAmount(long fAmount) {
        this.fAmount = fAmount;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
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

    public long getcAmount() {
        return cAmount;
    }

    public void setcAmount(long cAmount) {
        this.cAmount = cAmount;
    }

    public void setAcceptTime(Date acceptTime) {
        this.acceptTime = acceptTime;
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

	public List<SchemeShare> getShare() {
		return share;
	}

	public void setShare(List<SchemeShare> share) {
		this.share = share;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public long getAmount() {
		return amount;
	}

	public void setAmount(long amount) {
		this.amount = amount;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public List<TOrder> getOrders() {
		return orders;
	}

	public void setOrders(List<TOrder> orders) {
		this.orders = orders;
	}
}
