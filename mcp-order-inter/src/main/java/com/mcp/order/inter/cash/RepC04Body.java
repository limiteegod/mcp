package com.mcp.order.inter.cash;

import com.mcp.order.inter.RepBody;
import com.mcp.order.model.report.Coupon;

public class RepC04Body extends RepBody {

    private Coupon coupon;

    public Coupon getCoupon() {
        return coupon;
    }

    public void setCoupon(Coupon coupon) {
        this.coupon = coupon;
    }

}
