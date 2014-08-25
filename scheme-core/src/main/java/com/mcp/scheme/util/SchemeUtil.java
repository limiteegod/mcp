package com.mcp.scheme.util;

import com.mcp.core.util.CoreUtil;
import com.mcp.core.util.cons.SchemeType;
import com.mcp.order.common.ConstantValues;
import com.mcp.order.model.common.LotteryContext;
import com.mcp.order.model.entity.PlayType;
import com.mcp.order.model.ts.TOrder;
import com.mcp.order.model.ts.TTicket;
import com.mcp.order.status.ReceiptState;
import com.mcp.scheme.model.SchemeZh;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SchemeUtil {
	
	/**
	 * 根据追号方案，生成追号的订单
	 * @param scheme
	 * @return
	 */
	public static TOrder getOrderFromScheme(SchemeZh scheme)
	{
		return getOrderFromScheme(scheme, scheme.getStationId(), scheme.getPrintStationId(), -1, -1);
	}
	
	/**
	 * 根据追号方案，生成追号的订单
	 * @param scheme
	 * @return
	 */
	public static TOrder getOrderFromScheme(SchemeZh scheme, String stationId, String printStationId, int orderStatus, int ticketStatus)
	{
		String termCode = scheme.getCurTermCode();
		String gameCode = scheme.getGameCode();
		String customerId = scheme.getCustomerId();
		String channelCode = scheme.getChannelCode();
		
		Date now = new Date();
		TOrder order = new TOrder();
		order.setId(CoreUtil.getUUID());
		order.setTermCode(termCode);
		order.setSchemeId(scheme.getId());
		order.setStationId(stationId);
		order.setPrintStationId(printStationId);
		order.setGameCode(gameCode);
		order.setCustomerId(customerId);
		order.setSchemeType(SchemeType.SEQ_FOLLOW);
		order.setChannelCode(channelCode);
		order.setCreateTime(now);
		order.setAcceptTime(now);
		order.setPlatform(scheme.getPlatform());
		order.setStatus(orderStatus);
		order.setPayType(scheme.getPayType());
		
		List<TTicket> tList = new ArrayList<TTicket>();
		long oAmount = 0;
		String[] numList = scheme.getNumList().split("!");
		for(int j = 0; j < numList.length; j++)
		{
			String ticketString = numList[j];
			String[] ticketArray = ticketString.split("~");
			String playType = ticketArray[0];
			String betType = ticketArray[1];
			String number = ticketArray[2];
			long tAmount = Long.parseLong(ticketArray[3]);
			int multiple = Integer.parseInt(ticketArray[4]);
			
			TTicket ticket = new TTicket();
			ticket.setId(CoreUtil.getUUID());
			ticket.setOrderId(order.getId());
			ticket.setStationId(stationId);
			ticket.setPrinterStationId(printStationId);
			ticket.setCustomerId(customerId);
			ticket.setChannelCode(channelCode);
			ticket.setTermCode(termCode);
			ticket.setGameCode(gameCode);
			ticket.setBetTypeCode(betType);
			ticket.setPlayTypeCode(playType);
			ticket.setAmount(tAmount);
			ticket.setMultiple(multiple);
			PlayType pt = LotteryContext.getInstance().getPlayTypeByCode(gameCode + playType);
			ticket.setPrice(pt.getPrice());
			ticket.setNumbers(number);
			ticket.setCreateTime(now);
			ticket.setAcceptTime(now);
			ticket.setReceiptStatus(ReceiptState.NOT_TAKE_AWAY.getCode());
			ticket.setStatus(ticketStatus);
			ticket.setSeq(j + 1);
			ticket.setMultiple(multiple);
			tList.add(ticket);
			
			oAmount += tAmount;
		}
		order.setTicketCount(tList.size());
		order.setAmount(oAmount);
		order.setTickets(tList);
		return order;
	}
}
