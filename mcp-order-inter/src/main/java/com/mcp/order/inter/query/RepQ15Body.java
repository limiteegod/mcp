package com.mcp.order.inter.query;

import com.mcp.order.inter.PageInfo;
import com.mcp.order.inter.RepBody;
import com.mcp.order.model.ts.TOrder;

import java.util.List;


public class RepQ15Body extends RepBody {
	
	private TOrder order;

    public TOrder getOrder() {
        return order;
    }

    public void setOrder(TOrder order) {
        this.order = order;
    }
}
