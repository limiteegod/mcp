/**
 * 
 */
package com.mcp.mq.model;

/**
 * @author ming.li
 *
 */
public class MessageConfig {
	
	/**
	 * 消息的类型
	 */
	private int code;
	
	private String name;
	
	private String queue;

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getQueue() {
		return queue;
	}

	public void setQueue(String queue) {
		this.queue = queue;
	}
}
