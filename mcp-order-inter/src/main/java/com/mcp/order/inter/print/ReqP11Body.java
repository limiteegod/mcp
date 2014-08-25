package com.mcp.order.inter.print;

import com.mcp.core.util.StringUtil;
import com.mcp.order.exception.CoreException;
import com.mcp.order.exception.ErrCode;
import com.mcp.order.inter.ReqBody;

public class ReqP11Body extends ReqBody {

    private String ticketId;

    public String getTicketId() {
        return ticketId;
    }

    public void setTicketId(String ticketId) {
        this.ticketId = ticketId;
    }

    @Override
	public void validate() throws CoreException {
        if(StringUtil.isEmpty(this.ticketId))
        {
            throw new CoreException(ErrCode.E0003, ErrCode.codeToMsg(ErrCode.E0003));
        }
	}
}
