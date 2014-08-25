package com.mcp.order.model.report;

import com.mcp.order.util.JsonDateSerializer;
import org.codehaus.jackson.map.annotate.JsonFilter;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by ming.li on 2014/7/2.
 */
@Entity
@Table(name = "coupon")
@JsonFilter("coupon")
public class Coupon implements Serializable {

    private static final long serialVersionUID = 379469230644708178L;

    @Id
    @Column(length = 32)
    private String id;

    /**
     * 金额，单位为分
     */
    @Basic
    private long amount;

    /**
     * 优惠卷的状态，未生效（等待发放）,1，已生效（已发放给用户）,2，已过期（在有效期内未被使用）,3，已使用,4
     */
    @Basic
    private int status;

    /**
     * 过期时间
     */
    @Basic
    private Date expiredTime;

    /**
     * 创建时间
     */
    @Basic
    private Date createTime;

    /**
     * 激活时间，发放给用户的时间
     */
    @Basic
    private Date activeTime;

    /**
     * 类型，渠道卷（由渠道出资），平台卷（由平台出资）
     */
    @Basic
    private int type;

    /**
     * 发放的用户id，未生效时，用户id为空
     */
    @Basic
    @Column(length = 32)
    private String customerId;

    /**
     * 优惠卷所属的渠道，由平台发放时为空
     */
    @Basic
    @Column(length = 40)
    private String channelCode;

    @Version
    private int version;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @JsonSerialize(using=JsonDateSerializer.class)
    public Date getExpiredTime() {
        return expiredTime;
    }

    public void setExpiredTime(Date expiredTime) {
        this.expiredTime = expiredTime;
    }

    @JsonSerialize(using=JsonDateSerializer.class)
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @JsonSerialize(using=JsonDateSerializer.class)
    public Date getActiveTime() {
        return activeTime;
    }

    public void setActiveTime(Date activeTime) {
        this.activeTime = activeTime;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
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
}
