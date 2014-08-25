/**
 * 
 */
package com.mcp.order.gateway.util;

import com.mcp.core.util.CoreUtil;
import com.mcp.core.util.cons.SchemeType;
import com.mcp.order.common.ConstantValues;
import com.mcp.order.inter.trade.ReqOrder;
import com.mcp.order.inter.trade.ReqTicket;
import com.mcp.order.model.common.LotteryContext;
import com.mcp.order.model.entity.PlayType;
import com.mcp.order.model.ts.TOrder;
import com.mcp.order.model.ts.TTicket;
import com.mcp.order.status.OrderState;
import com.mcp.order.status.ReceiptState;
import com.mcp.order.status.TicketState;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * @author ming.li
 *
 */
public class ParamConverterUtil {
	
	/**
	 * 从一个ReqOrder对象获得TOrder对象
	 * @param reqOrder
	 * @return
	 */
	public static TOrder getTOrderFromReqOrder(ReqOrder reqOrder, String customerId, String channelCode, int payType, String stationId, String printStationId)
	{
		TOrder order = new TOrder();
		order.setId(CoreUtil.getUUID());
		order.setTermCode(reqOrder.getTermCode());
		order.setSchemeId(reqOrder.getSchemeId());
		order.setGameCode(reqOrder.getGameCode());
		order.setAmount(reqOrder.getAmount());
		order.setPlatform(reqOrder.getPlatform());
		order.setStationId(stationId);
		order.setPrintStationId(printStationId);
		order.setCreateTime(new Date());
		order.setStatus(OrderState.INIT.getCode());
		order.setMultiple(reqOrder.getMultiple());
		order.setOuterId(reqOrder.getOuterId());
		order.setSchemeType(SchemeType.NONE);
		
		order.setCustomerId(customerId);
		order.setPayType(payType);
		order.setChannelCode(channelCode);
		
		List<TTicket> orderTicketList = new ArrayList<TTicket>();
		//初始化票
		List<ReqTicket> reqTickets = reqOrder.getTickets();
		for(int i = 0; i < reqTickets.size(); i++)
		{
			//ticket最多只能有5注
			TTicket ticket = new TTicket();
			ReqTicket reqTicket = reqTickets.get(i);
			
			ticket.setOrderId(order.getId());
			ticket.setGameCode(order.getGameCode());
			ticket.setTermCode(order.getTermCode());
			ticket.setCustomerId(order.getCustomerId());
			ticket.setStationId(stationId);
			ticket.setPrinterStationId(printStationId);
			ticket.setChannelCode(channelCode);
			
			ticket.setBetTypeCode(reqTicket.getBetTypeCode());
			ticket.setPlayTypeCode(reqTicket.getPlayTypeCode());
			ticket.setAmount(reqTicket.getAmount());
			ticket.setMultiple(reqTicket.getMultiple());
			
			PlayType pt = LotteryContext.getInstance().getPlayTypeByCode(order.getGameCode() + ticket.getPlayTypeCode());
			ticket.setPrice(pt.getPrice());
			
			ticket.setId(CoreUtil.getUUID());
			ticket.setNumbers(reqTicket.getNumbers());
			ticket.setStatus(TicketState.INIT.getCode());
			ticket.setCreateTime(new Date());
			ticket.setReceiptStatus(ReceiptState.NOT_TAKE_AWAY.getCode());
			
			ticket.setSeq(i + 1);
			orderTicketList.add(ticket);
		}
		order.setTickets(orderTicketList);
		order.setTicketCount(reqTickets.size());
		return order;
	}
}
