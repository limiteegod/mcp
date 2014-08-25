/**
 * 
 */
package com.mcp.order.inter.notify;

import com.mcp.order.exception.CoreException;
import com.mcp.order.exception.ErrCode;
import com.mcp.order.inter.ReqBody;
import com.mcp.order.model.ts.Term;

import java.util.List;


/**
 * @author ming.li
 *
 */
public class ReqN04Body extends ReqBody {
	
	private List<Term> terms;

	public List<Term> getTerms() {
		return terms;
	}

	public void setTerms(List<Term> terms) {
		this.terms = terms;
	}

	@Override
	public void validate() throws CoreException {
		//期次通知必须要有一个期次信息
		if(this.terms == null || terms.size() == 0)
		{
			throw new CoreException(ErrCode.E0003, ErrCode.codeToMsg(ErrCode.E0003));
		}
	}
}
