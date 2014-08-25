package com.mcp.order.inter.query;

import com.mcp.order.exception.CoreException;
import com.mcp.order.exception.ErrCode;
import com.mcp.order.inter.ReqBody;

public class ReqQ10Body extends ReqBody {
	
	private String orderId = "";
    
    private String ticketId = "";

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getTicketId() {
        return ticketId;
    }

    public void setTicketId(String ticketId) {
        this.ticketId = ticketId;
    }

    /* (non-Javadoc)
         * @see com.mcp.core.inter.define.Body#validate()
         */
	@Override
	public void validate() throws CoreException {
		if(this.ticketId == null || this.orderId == null)
		{
			throw new CoreException(ErrCode.E0003, ErrCode.codeToMsg(ErrCode.E0003));
		}
		if(this.ticketId.length() == 0 && this.orderId.length() == 0)
		{
			throw new CoreException(ErrCode.E0003, ErrCode.codeToMsg(ErrCode.E0003));
		}
	}
}
