/**
 * 
 */
package com.mcp.order.model.mongo;

/**
 * 自增长的id，类似oracle的序列
 * @author ming.li
 *
 */
public class MgAutoIncrId {
	
	private String id;
	
	private long value;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public long getValue() {
		return value;
	}

	public void setValue(long value) {
		this.value = value;
	}
}
