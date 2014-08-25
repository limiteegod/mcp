package com.mcp.order.inter.query;

import com.mcp.order.exception.CoreException;
import com.mcp.order.inter.ReqBody;

public class ReqQ15Body extends ReqBody {

    /**
     * 订单号
     */
	private String orderId;

    /**
     * 外部订单号
     */
    private String outerId;

    /**
     * 是否显示票的信息
     */
    private boolean showTickets = false;

    public String getOrderId() {
        return orderId;
    }

    public boolean isShowTickets() {
        return showTickets;
    }

    public void setShowTickets(boolean showTickets) {
        this.showTickets = showTickets;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOuterId() {
        return outerId;
    }

    public void setOuterId(String outerId) {
        this.outerId = outerId;
    }

    /* (non-Javadoc)
         * @see com.mcp.core.inter.define.Body#validate()
         */
    @Override
    public void validate() throws CoreException {
    }

}
