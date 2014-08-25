/**
 * 
 */
package com.mcp.order.inter.trade;

import com.mcp.order.common.ConstantValues;

/**
 * @author ming.li
 *
 */
public class RepOrder {
	
	private String id;
	
	private int status;
	
	private int schemeType = ConstantValues.TScheme_Type_Default.getCode();

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getSchemeType() {
		return schemeType;
	}

	public void setSchemeType(int schemeType) {
		this.schemeType = schemeType;
	}
	
}
