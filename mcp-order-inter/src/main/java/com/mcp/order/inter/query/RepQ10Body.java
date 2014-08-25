package com.mcp.order.inter.query;

import com.mcp.order.inter.RepBody;
import com.mcp.order.model.ts.TTicket;

import java.util.List;


public class RepQ10Body extends RepBody {
	
	private int counts;
    
    private List<TTicket> rst;

    public int getCounts() {
        return counts;
    }

    public void setCounts(int counts) {
        this.counts = counts;
    }

    public List<TTicket> getRst() {
        return rst;
    }

    public void setRst(List<TTicket> rst) {
        this.rst = rst;
    }
}
