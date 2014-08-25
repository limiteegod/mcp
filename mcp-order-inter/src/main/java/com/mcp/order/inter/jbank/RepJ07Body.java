package com.mcp.order.inter.jbank;

import com.mcp.order.inter.PageInfo;
import com.mcp.order.inter.RepBody;
import com.mcp.order.model.ts.TOrder;

import java.util.List;

/**
 * 获取游戏的当前期的投注记录
 * @author ming.li
 */
public class RepJ07Body extends RepBody {
	
	private PageInfo pageInfo;

	public PageInfo getPageInfo() {
		return pageInfo;
	}

	public void setPageInfo(PageInfo pageInfo) {
		this.pageInfo = pageInfo;
	}
		
	private List<TOrder> rst;

    public List<TOrder> getRst() {
        return rst;
    }

    public void setRst(List<TOrder> rst) {
        this.rst = rst;
    }
}
