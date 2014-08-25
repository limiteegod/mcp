package com.mcp.order.inter;

import com.mcp.core.util.cons.SystemUserType;
import com.mcp.order.util.JsonDateSerializer;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.util.Date;

public class Head {
	
	/**
	 * 版本
	 */
	private String ver;
	
	/**
	 * 唯一ID
	 */
	private String id;
	
	/**
	 * 时间，不能用date类型，可恶的时间精度丢失问题。日期转字符串，字符串再转日期之后，很可能精度发生丢失
	 */
	private String timestamp;
	
	/**
	 * 渠道编码
	 */
	private String channelCode;
	
	/**
	 * 命令号
	 */
	private String cmd;

    /**
     * 用户的id
     */
    private String userId;

    /**
     * 用户的类型
     */
    private int userType = SystemUserType.GUEST.getCode();

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getVer() {
		return ver;
	}

	public void setVer(String ver) {
		this.ver = ver;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	/**
	 * 加密的标志码，md5加密时，为加密之后的密文
	 */
	private String digest;
	
	/**
	 * 加密类型
	 */
	private String digestType;

	public String getDigest() {
		return digest;
	}

	public void setDigest(String digest) {
		this.digest = digest;
	}

	public String getDigestType() {
		return digestType;
	}

	public void setDigestType(String digestType) {
		this.digestType = digestType;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public String getChannelCode() {
		return channelCode;
	}

	public void setChannelCode(String channelCode) {
		this.channelCode = channelCode;
	}

	public String getCmd() {
		return cmd;
	}

	public void setCmd(String cmd) {
		this.cmd = cmd;
	}

    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }
}
