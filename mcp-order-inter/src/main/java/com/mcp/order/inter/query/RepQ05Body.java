package com.mcp.order.inter.query;

import com.mcp.order.inter.PageInfo;
import com.mcp.order.inter.RepBody;
import com.mcp.order.model.admin.BetShop;
import com.mcp.order.model.admin.Station;

import java.util.List;


public class RepQ05Body extends RepBody {
	
	private List<BetShop> rst;

    public List<BetShop> getRst() {
        return rst;
    }

    public void setRst(List<BetShop> rst) {
        this.rst = rst;
    }
    
    private PageInfo pageInfo;

	public PageInfo getPageInfo() {
		return pageInfo;
	}

	public void setPageInfo(PageInfo pageInfo) {
		this.pageInfo = pageInfo;
	}
}
