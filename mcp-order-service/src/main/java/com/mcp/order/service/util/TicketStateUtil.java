package com.mcp.order.service.util;

import com.mcp.order.status.TermState;
import com.mcp.order.status.TicketState;

public class TicketStateUtil {
	
	/**
	 * 根据是否支持及期次状态获得票据状态
	 * @param afford
	 * @param termStatus
	 * @return
	 */
	public static int getTicketStatus(boolean afford, int termStatus)
	{
		if(afford)
		{
			if(termStatus == TermState.ON_SALE.getCode())
			{
				return TicketState.WAITING_PRINT.getCode();
			}
			else if(termStatus == TermState.NOT_ON_SALE.getCode())
			{
				return TicketState.PRESALE.getCode();
			}
		}
		else
		{
			return TicketState.WAITING_PAY.getCode();
		}
		return TicketState.INIT.getCode();
	}
	
}
