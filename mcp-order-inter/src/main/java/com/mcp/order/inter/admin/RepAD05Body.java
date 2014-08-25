package com.mcp.order.inter.admin;

import com.mcp.order.inter.PageInfo;
import com.mcp.order.inter.RepBody;
import com.mcp.order.model.ts.TOrder;

import java.util.List;

public class RepAD05Body extends RepBody {

    private List<TOrder> rst;

    public List<TOrder> getRst() {
        return rst;
    }

    public void setRst(List<TOrder> rst) {
        this.rst = rst;
    }

    private PageInfo pi;

    public PageInfo getPi() {
        return pi;
    }

    public void setPi(PageInfo pi) {
        this.pi = pi;
    }
}
