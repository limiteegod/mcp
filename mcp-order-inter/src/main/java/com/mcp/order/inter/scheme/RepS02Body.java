package com.mcp.order.inter.scheme;

import com.mcp.order.inter.RepBody;
import com.mcp.scheme.model.SchemeZh;

import java.util.List;

public class RepS02Body extends RepBody {
	
	private List<SchemeZh> rst;

	public List<SchemeZh> getRst() {
		return rst;
	}

	public void setRst(List<SchemeZh> rst) {
		this.rst = rst;
	}
}
