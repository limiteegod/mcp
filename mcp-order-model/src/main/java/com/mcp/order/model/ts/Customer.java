package com.mcp.order.model.ts;

import com.mcp.order.util.JsonDateSerializer;
import org.codehaus.jackson.map.annotate.JsonFilter;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import javax.persistence.*;
import java.util.Date;

/**
 * User: yeeson he
 * Date: 13-7-16
 * Time: 下午4:41
 * To be the best of me!
 */
@Entity
@Table(name = "customer")
@JsonFilter("customer")
public class Customer implements java.io.Serializable {
	
    /**
	 * 
	 */
	private static final long serialVersionUID = -2883870790811114167L;
	
	//    彩民实体类。用于彩民的注册、登录等。一个彩民实体类可能包含零到多个账户实体。
    @Id
    @Column(length = 32)
    private String id;
    //彩民所属的内部组。对应String类型的定义表。此信息不对外公开。可用于设置内部白名单、黑名单、特殊VIP组、马甲组、外部组等。
    @Basic
    @Column(length = 32)
    private String groupId;

    @Basic
    @Column(nullable=false, length=40)
    private String name;

    @Basic
    @Column(nullable=false, length=40)
    private String password;

    @Basic
    @Column(length = 40)
    private String nickyName;

    @Basic
    @Column(length = 40)
    private String phone;

    @Basic
    @Column(length = 80)
    private String email;

    @Basic
    @Column(length = 40)
    private String realName;

    @Basic
    @Column(length = 80)
    private String identityId;      // 身份证号

    @Basic
    private Date regDate;

    @Basic
    private Date lastActiveTime;
    
    /**
     * 最后登录时间
     */
    @Basic
    private Date lastLoginTime;

    @Basic
    @Column(length = 40)
    private String introducer;             //介绍人，或者软件分发渠道id。
    // 彩民在平台的账户资金。

    @Basic
    private long recharge;//账户余额

    @Basic
    private long integral; //积分

    @Basic
    private int status;  //账户状态。   0 可用   1 不可用
    
    @Basic
    @Column(length = 20)
    private String channelCode;

    @Version
    private int version;
    
    public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}
	
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickyName() {
        return nickyName;
    }

    public void setNickyName(String nickyName) {
        this.nickyName = nickyName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getIdentityId() {
        return identityId;
    }

    public void setIdentityId(String identityId) {
        this.identityId = identityId;
    }

    @JsonSerialize(using = JsonDateSerializer.class)
    public Date getRegDate() {
        return regDate;
    }

    public void setRegDate(Date regDate) {
        this.regDate = regDate;
    }

    @JsonSerialize(using = JsonDateSerializer.class)
    public Date getLastActiveTime() {
        return lastActiveTime;
    }

    public void setLastActiveTime(Date lastActiveTime) {
        this.lastActiveTime = lastActiveTime;
    }

    public String getIntroducer() {
        return introducer;
    }

    public void setIntroducer(String introducer) {
        this.introducer = introducer;
    }

    public long getRecharge() {
        return recharge;
    }

    public void setRecharge(long recharge) {
        this.recharge = recharge;
    }

    public long getIntegral() {
        return integral;
    }

    public void setIntegral(long integral) {
        this.integral = integral;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

	public Date getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	public String getChannelCode() {
		return channelCode;
	}

	public void setChannelCode(String channelCode) {
		this.channelCode = channelCode;
	}
}
