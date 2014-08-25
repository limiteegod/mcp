/**
 * 
 */
package com.mcp.order.gateway.util;

import com.mcp.core.util.CoreUtil;
import com.mcp.jc.common.JcParam;
import com.mcp.jc.common.JcUtils;
import com.mcp.order.inter.trade.ReqOrder;
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
public class JcOrderUtil {
	
	
	/**
	 * 根据参数生成订单
	 * @param reqOrder
	 * @param channelCode
	 * @param customerId
	 * @param stationId
	 * @param printStationId
	 * @param payType
	 * @return
	 */
	public static TOrder getOrder(ReqOrder reqOrder, String channelCode, 
			String customerId, String stationId, String printStationId, int payType)
	{
		String playType = reqOrder.getPlayType();
		String gameCode = reqOrder.getGameCode();
		
		//判断订单是否可接受，每一期都在开售状态
		String number = reqOrder.getNumber();
		Date now = new Date();
		
		PlayType pt = LotteryContext.getInstance().getPlayTypeByCode(gameCode + playType);
		int price = pt.getPrice();
		TOrder order = new TOrder();
		order.setId(CoreUtil.getUUID());
		order.setChannelCode(channelCode);
		order.setStationId(stationId);
		order.setCustomerId(customerId);
		order.setGameCode(gameCode);
		order.setMultiple(reqOrder.getMultiple());
		order.setNumbers(reqOrder.getNumber());
		order.setAmount(reqOrder.getAmount());
		order.setPlatform(reqOrder.getPlatform());
		order.setPayType(payType);
		order.setCreateTime(new Date());
		order.setPrintStationId(printStationId);
		order.setStatus(OrderState.WAITING_PAY.getCode());
		String[] oMatchStrArray = reqOrder.getNumber().split(";");
		String[] oNumberArray = oMatchStrArray[oMatchStrArray.length - 1].split("\\|");
		order.setTermCode(oNumberArray[1]);	//设置订单的最后一期
		order.setOuterId(reqOrder.getOuterId());

		List<TTicket> ticketList = new ArrayList<TTicket>();
		String betType = reqOrder.getBetType();
		String[] betTypeArray = betType.split(",");
		for(int j = 0; j < betTypeArray.length; j++)
		{
			String dBetType = betTypeArray[j];
			
			int m = Integer.parseInt(dBetType.substring(0, 1));
			int n = Integer.parseInt(dBetType.substring(1));
			JcParam param = JcUtils.splitOrders(number, m, n);
			List<String> tList = param.getTicketList();
			List<Integer> cList = param.getCountList();
			for(int i = 0; i < tList.size(); i++)
			{
				String tNumber = tList.get(i);
				String[] tMatchStrArray = tNumber.split(";");
				String termCode = "";
				for(int k = 0; k < tMatchStrArray.length; k++)
				{
					if(k > 0)
					{
						termCode += ",";
					}
					termCode += tMatchStrArray[k].split("\\|")[1];
				}
				TTicket ticket = new TTicket();
				ticket.setId(CoreUtil.getUUID());
				ticket.setOrderId(order.getId());
				ticket.setStationId(stationId);
				ticket.setPrinterStationId(printStationId);
				ticket.setChannelCode(channelCode);
				ticket.setGameCode(order.getGameCode());
				ticket.setBetTypeCode(dBetType);
				ticket.setNumbers(tNumber);
				ticket.setPlayTypeCode(JcUtils.getTicketType(tNumber));
				ticket.setrNumber(tNumber); 	//TODO 正式环境设置为空
				ticket.setTermCode(termCode);
				ticket.setMultiple(reqOrder.getMultiple());
				int amount = price*reqOrder.getMultiple()*cList.get(i);
				ticket.setAmount(amount);
				ticket.setCreateTime(now);
				ticket.setCustomerId(customerId);
				ticket.setPrice(price);
				ticket.setReceiptStatus(ReceiptState.NOT_TAKE_AWAY.getCode());
				ticket.setStatus(TicketState.INIT.getCode());
				ticket.setSeq(i+1);
				ticketList.add(ticket);
			}
		}
		order.setTicketCount(ticketList.size());
		order.setTickets(ticketList);
		return order;
	}
}
