/**
 * 
 */
package com.mcp.order.inter.notify;

import com.mcp.order.exception.CoreException;
import com.mcp.order.inter.ReqBody;
import com.mcp.order.model.ts.Term;


/**
 * @author ming.li
 */
public class ReqN01Body extends ReqBody {
	
	private Term terms;

	public Term getTerms() {
		return terms;
	}

	public void setTerms(Term terms) {
		this.terms = terms;
	}

	@Override
	public void validate() throws CoreException {
		
	}
}
