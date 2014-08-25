package com.mcp.order.inter.query;

import com.mcp.order.inter.PageInfo;
import com.mcp.order.inter.RepBody;
import com.mcp.order.model.ts.TOrder;

import java.util.List;


public class RepQ03Body extends RepBody {
	
	private List<TOrder> rst;


    public List<TOrder> getRst() {
        return rst;
    }

    public void setRst(List<TOrder> rst) {
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
