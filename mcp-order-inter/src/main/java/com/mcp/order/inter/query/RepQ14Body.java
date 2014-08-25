package com.mcp.order.inter.query;

import com.mcp.order.inter.RepBody;
import com.mcp.order.model.ts.Term;

import java.util.List;

public class RepQ14Body extends RepBody {
	
	private List<Term> rst;

	public List<Term> getRst() {
		return rst;
	}

	public void setRst(List<Term> rst) {
		this.rst = rst;
	}
}
