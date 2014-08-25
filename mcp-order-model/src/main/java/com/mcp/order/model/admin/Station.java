package com.mcp.order.model.admin;

import com.mcp.order.util.JsonDateSerializer;
import org.codehaus.jackson.map.annotate.JsonFilter;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import javax.persistence.*;
import java.util.Date;

/**
 * 投注站及投注站的子账户，主账户parentId为自己的id，子账户的parentId为其主账户的id
 * User: yeeson he
 * Date: 13-7-16
 * Time: 下午5:03
 * To be the best of me!
 */
@Entity
@Table(name = "station")
@JsonFilter("station")
public class Station implements java.io.Serializable {
	
    /**
	 * 
	 */
	private static final long serialVersionUID = -8616789989007533272L;
	
    @Id
    @Column(length = 32)
    private String id;
    
    /**
     * 投注站自己的编号
     */
    @Basic
    @Column(length = 40, unique=true, nullable=false)
    private String code;

    @Basic
    private boolean relayable;  // false.是否可以接收转票。转票功能仅向部分站点开放。转票最终能否成功，还需要看转入方是否能接收。

    @Basic
    @Column(length = 40)
    private String name;//登录的名称，编号。

    @Basic
    @Column(length = 40)
    private String password;   //!

    @Basic
    @Column(length = 40)
    private String description;
    
    @Basic
    private long balance;   //投注站结算账户（应收款项）。    //!

    @Basic
    @Column(length = 40)
    private String photo;

    @Basic
    private Date buildTime;        //!

    @Basic
    private Date expiredTime;      //!

    /**
     * 最后登录时间
     */
    @Basic
    private Date lastLoginTime;

    @Basic
    private int stationType;           // 0 普通投注站  1 出票中心

    @Basic
    private int status;           //d0 可用 1 不可用  2 注销
    
    @Version
    private int version;
    
    @Basic
    private int queueIndex = -1;
    
    /**
     * 数据签名的密钥
     */
    @Basic
    @Column(length = 32)
    private String secretKey;

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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public boolean isRelayable() {
        return relayable;
    }

    public void setRelayable(boolean relayable) {
        this.relayable = relayable;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    public long getBalance() {
        return balance;
    }

    public void setBalance(long balance) {
        this.balance = balance;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    @JsonSerialize(using = JsonDateSerializer.class)
    public Date getBuildTime() {
        return buildTime;
    }

    public void setBuildTime(Date buildTime) {
        this.buildTime = buildTime;
    }

    @JsonSerialize(using = JsonDateSerializer.class)
    public Date getExpiredTime() {
        return expiredTime;
    }

    public void setExpiredTime(Date expiredTime) {
        this.expiredTime = expiredTime;
    }

    @JsonSerialize(using = JsonDateSerializer.class)
    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public int getStationType() {
        return stationType;
    }

    public void setStationType(int stationType) {
        this.stationType = stationType;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

	public int getQueueIndex() {
		return queueIndex;
	}

	public void setQueueIndex(int queueIndex) {
		this.queueIndex = queueIndex;
	}

	public String getSecretKey() {
		return secretKey;
	}

	public void setSecretKey(String secretKey) {
		this.secretKey = secretKey;
	}
}