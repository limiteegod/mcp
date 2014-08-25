/**
 * 
 */
package com.mcp.order.inter;

/**
 * filter层收到的原始请求
 * @author ming.li
 */
public class McpGtMsg {
	
	private Head head;
	
	private String body;

	public Head getHead() {
		return head;
	}

	public void setHead(Head head) {
		this.head = head;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}
}
