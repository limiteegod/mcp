package com.mcp.order.inter.query;

import com.mcp.order.inter.PageInfo;
import com.mcp.order.inter.RepBody;
import com.mcp.order.model.ts.MoneyLog;

import java.util.List;


public class RepQ04Body extends RepBody {
	
	private List<MoneyLog> rst;

    public List<MoneyLog> getRst() {
        return rst;
    }

    public void setRst(List<MoneyLog> rst) {
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
