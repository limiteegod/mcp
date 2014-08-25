/**
 * 
 */
package com.mcp.order.service.util;

import com.mcp.order.model.ts.TOrder;
import com.mcp.order.model.ts.TTicket;
import com.mcp.order.status.OrderState;
import com.mcp.order.status.TermState;

import java.util.List;

/**
 * @author ming.li
 *
 */
public class OrderStateUtil {
	
	/**
	 * 根据订单是否支付及期次状态获得订单的状态 
	 * @param afford
	 * @param termStatus
	 * @return
	 */
	public static int getOrderStatus(boolean afford, int termStatus)
	{
		if(afford)
		{
			if(termStatus == TermState.ON_SALE.getCode())
			{
				return OrderState.WAITING_PRINT.getCode();
			}
			else if(termStatus == TermState.NOT_ON_SALE.getCode())
			{
				return OrderState.PRESALE.getCode();
			}
		}
		else
		{
			return OrderState.WAITING_PAY.getCode();
		}
		return OrderState.INIT.getCode();
	}
	
	/**
	 * 更新订单的状态
	 * @param order
	 * @param afford
	 * @param termStatus
	 */
	public static void updateOrderStatus(TOrder order, boolean afford, int termStatus)
	{
		order.setStatus(getOrderStatus(afford, termStatus));
		List<TTicket> tList = order.getTickets();
		if(tList != null)
		{
			for(int i = 0; i < tList.size(); i++)
			{
				tList.get(i).setStatus(TicketStateUtil.getTicketStatus(afford, termStatus));
			}
		}
	}
}
