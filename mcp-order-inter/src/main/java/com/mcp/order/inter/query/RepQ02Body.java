package com.mcp.order.inter.query;

import com.mcp.order.inter.RepBody;
import com.mcp.scheme.model.SchemeZh;

import java.util.List;

public class RepQ02Body extends RepBody {
	
	private List<SchemeZh> rst;

	public List<SchemeZh> getRst() {
		return rst;
	}

	public void setRst(List<SchemeZh> rst) {
		this.rst = rst;
	}
}
