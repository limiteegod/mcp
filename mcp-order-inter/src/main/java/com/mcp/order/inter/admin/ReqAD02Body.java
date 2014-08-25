package com.mcp.order.inter.admin;

import com.mcp.core.util.StringUtil;
import com.mcp.order.exception.CoreException;
import com.mcp.order.exception.ErrCode;
import com.mcp.order.inter.ReqBody;

public class ReqAD02Body extends ReqBody {

    /**
     * 需要重新出票的票的id
     */
    private String ticketId;

    /**
     * 是否强制更新票的状态为等待出票的状态
     */
    public boolean resetStatus = false;

    /**
     * 是否强制更新票的id
     */
    public boolean resetId = false;

    /**
     * 重新指定出票口
     */
    private String stationId = null;

    public String getTicketId() {
        return ticketId;
    }

    public void setTicketId(String ticketId) {
        this.ticketId = ticketId;
    }

    public boolean isResetStatus() {
        return resetStatus;
    }

    public void setResetStatus(boolean resetStatus) {
        this.resetStatus = resetStatus;
    }

    public boolean isResetId() {
        return resetId;
    }

    public void setResetId(boolean resetId) {
        this.resetId = resetId;
    }

    public String getStationId() {
        return stationId;
    }

    public void setStationId(String stationId) {
        this.stationId = stationId;
    }

    @Override
	public void validate() throws CoreException {
        if(StringUtil.isEmpty(this.ticketId))
        {
            throw new CoreException(ErrCode.E0003);
        }
	}
}
