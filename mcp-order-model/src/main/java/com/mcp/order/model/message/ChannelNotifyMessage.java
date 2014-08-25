/**
 * 
 */
package com.mcp.order.model.message;

/**
 * @author ming.li
 *
 */
public class ChannelNotifyMessage implements java.io.Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1681854236593865583L;

	/**
	 * 渠道代码
	 */
	private String channelCode;
	
	/**
	 * 消息的主体
	 */
	private String message;
	
	/**
	 * 消息发送的目标url
	 */
	private String url;
	
	/**
	 * 消息的类型
	 */
	private int type;

	public String getChannelCode() {
		return channelCode;
	}

	public void setChannelCode(String channelCode) {
		this.channelCode = channelCode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
}
