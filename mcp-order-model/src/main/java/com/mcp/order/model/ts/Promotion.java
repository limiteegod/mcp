/**
 * 
 */
package com.mcp.order.model.ts;

import com.mcp.order.util.JsonDateSerializer;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import javax.persistence.*;
import java.util.Date;

/**
 * @author ming.li
 *
 */
@Entity
@Table(name = "promotion")
public class Promotion implements java.io.Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5461916640987362886L;

	@Id
    @Column(length = 32)
	private String id;
	
	/**
	 * 图片的地址
	 */
	@Basic
	@Column(length = 2048)
	private String picUrl;
	
	/**
	 * 图片要链接到的地址
	 */
	@Basic
	@Column(length = 2048)
	private String picToUrl;
	
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

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPicUrl() { 
		return picUrl;
	}

	public void setPicUrl(String picUrl) { 
		this.picUrl = picUrl;
	}

	public String getPicToUrl() {
		return picToUrl;
	}

	public void setPicToUrl(String picToUrl) {
		this.picToUrl = picToUrl;
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
}
