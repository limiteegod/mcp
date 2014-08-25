/**
 * 
 */
package com.mcp.order.model.mongo;

import com.mcp.order.common.ConstantValues;

/**
 * 记录等待支付的id的类型，比如订单，追号方案
 * @author ming.li
 *
 */
public class MgNotify {
	
	/**
	 * 渠道号
	 */
	private String id;
	
	/**
	 * 通知的url
	 */
	private String url;
	
	/**
	 * 加密的密钥
	 */
	private String key;

    /**
     * ftp服务器的父目录
     */
    private String ftpPath;

    /**
     * 渠道状态
     */
    private int status = ConstantValues.NOTIFY_CHANNEL_STATUS_WAITING.getCode();

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

    public String getFtpPath() {
        return ftpPath;
    }

    public void setFtpPath(String ftpPath) {
        this.ftpPath = ftpPath;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
