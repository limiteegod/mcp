/**
 * 
 */
package com.mcp.scheme.model;

import org.codehaus.jackson.map.annotate.JsonFilter;

import javax.persistence.*;
import java.util.Date;

/**
 * 合买方案份额表
 * @author ming.li
 */
@Entity
@Table(name = "schemeshare")
@JsonFilter("schemeshare")
public class SchemeShare implements java.io.Serializable {
	
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

    @Basic
    @Column(length = 32)
    private String schemeId;
	
	/**
	 * 用户所占有的份额
	 */
	@Basic
	private long amount;

    /**
     * 中奖金额
     */
    @Basic
    private long bonus;

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

    public long getBonus() {
        return bonus;
    }

    public void setBonus(long bonus) {
        this.bonus = bonus;
    }

    public String getSchemeId() {
        return schemeId;
    }

    public void setSchemeId(String schemeId) {
        this.schemeId = schemeId;
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

	public long getAmount() {
		return amount;
	}

	public void setAmount(long amount) {
		this.amount = amount;
	}
}
