/**
 * 
 */
package com.mcp.order.inter;

import com.mcp.order.exception.ErrCode;


/**
 * @author ming.li
 *
 */
public abstract class RepBody implements Body {
	
	private String repCode = ErrCode.E0000;
	
	private String description = ErrCode.codeToMsg(ErrCode.E0000);

	public String getRepCode() {
		return repCode;
	}

	public void setRepCode(String repCode) {
		this.repCode = repCode;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
