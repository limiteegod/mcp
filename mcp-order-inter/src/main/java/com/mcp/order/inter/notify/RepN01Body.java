/**
 * 
 */
package com.mcp.order.inter.notify;

import com.mcp.order.inter.RepBody;
import com.mcp.order.model.ts.Term;


/**
 * @author ming.li
 */
public class RepN01Body extends RepBody {
	
	private Term term;

	public Term getTerm() {
		return term;
	}

	public void setTerm(Term term) {
		this.term = term;
	}
	
	/**
	 * 附加信息
	 */
	private String tip;

	public String getTip() {
		return tip;
	}

	public void setTip(String tip) {
		this.tip = tip;
	}
}
