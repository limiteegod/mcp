/**
 * 
 */
package com.mcp.mq.model;

/**
 * @author ming.li
 *
 */
public class McpMessage {
	
	/**
	 * 消息的类型
	 */
	private int code;
	
	/**
	 * 消息的内容，json格式
	 */
	private String content;

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	public String toString()
	{
		return "type:" + code + ",content:" + content;
	}
}
