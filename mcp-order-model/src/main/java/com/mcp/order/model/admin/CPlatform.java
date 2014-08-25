package com.mcp.order.model.admin;/*
 * User: yeeson he
 * Date: 13-8-8
 * Time: 上午10:10
 */

import javax.persistence.*;

/**
 * 整个系统平台.采用key-value的列存储方式。key值采用多级目录方式。
 * CPlatform存储全局性的配置。比如系统的关闭、开启。
 * CPlatform存储全局性的数据。比如库存奖金、库存积分等。兑奖、发放积分等，均需要从库存金中扣除。
 */
@Entity
@Table(name = "cplatform")
public class CPlatform implements java.io.Serializable {
	
    /**
	 * 
	 */
	private static final long serialVersionUID = -8954410897345807673L;

	@Id
    @Column(length = 32)
    private String id;
    
    /**
     * cc:balance表示平台账户
     */
    @Basic
    @Column(unique=true, nullable=false)
    private String formKey;
    
    @Basic
    @Column(length=40)
    private String formValue;
    
    @Basic
    private long formLongValue;
    
    @Basic
    @Column(length=20)
    private String sponsor;
    
    @Basic
    @Column(length=20)
    private String remarks;
    
    @Basic
    private int status;//d0可用  1不可用
    
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

    public String getFormKey() {
		return formKey;
	}

	public void setFormKey(String formKey) {
		this.formKey = formKey;
	}

	public String getFormValue() {
		return formValue;
	}

	public void setFormValue(String formValue) {
		this.formValue = formValue;
	}

	public String getSponsor() {
        return sponsor;
    }

    public void setSponsor(String sponsor) {
        this.sponsor = sponsor;
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

	public long getFormLongValue() {
		return formLongValue;
	}

	public void setFormLongValue(long formLongValue) {
		this.formLongValue = formLongValue;
	}
}
