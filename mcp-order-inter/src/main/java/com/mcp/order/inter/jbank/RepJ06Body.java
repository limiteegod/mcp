package com.mcp.order.inter.jbank;

import com.mcp.order.inter.RepBody;
import com.mcp.order.model.ts.Term;

import java.util.List;

/**
 * 获取游戏的当前期
 * @author ming.li
 */
public class RepJ06Body extends RepBody {
		
	private List<Term> rst;

	public List<Term> getRst() {
		return rst;
	}

	public void setRst(List<Term> rst) {
		this.rst = rst;
	}
}
