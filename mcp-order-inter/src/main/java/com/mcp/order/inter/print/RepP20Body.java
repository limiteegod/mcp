package com.mcp.order.inter.print;

import com.mcp.order.inter.PageInfo;
import com.mcp.order.inter.RepBody;
import com.mcp.order.model.ts.TTicket;

import java.util.List;

/**
 * Created by CH on 2014/10/29.
 */
public class RepP20Body  extends RepBody{

    private PageInfo pi;
    private List<TTicket> rst;
    public PageInfo getPi() {
        return pi;
    }

    public void setPi(PageInfo pi) {
        this.pi = pi;
    }

    public List<TTicket> getRst() {
        return rst;
    }

    public void setRst(List<TTicket> rst) {
        this.rst = rst;
    }
}
