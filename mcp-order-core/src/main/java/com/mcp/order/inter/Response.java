/**
 * 
 */
package com.mcp.order.inter;

/**
 * @author ming.li
 *
 */
public class Response {
	
	private String code;
	
	private Filter filter;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Filter getFilter() {
		return filter;
	}

	public void setFilter(Filter filter) {
		this.filter = filter;
	}
}
