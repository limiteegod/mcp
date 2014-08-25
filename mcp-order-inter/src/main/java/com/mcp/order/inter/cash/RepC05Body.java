package com.mcp.order.inter.cash;

import com.mcp.order.inter.PageInfo;
import com.mcp.order.inter.RepBody;
import com.mcp.order.model.report.Coupon;

import java.util.List;

public class RepC05Body extends RepBody {

    private List<Coupon> rst;

    public List<Coupon> getRst() {
        return rst;
    }

    public void setRst(List<Coupon> rst) {
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
