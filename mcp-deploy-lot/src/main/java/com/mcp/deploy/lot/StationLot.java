package com.mcp.deploy.lot;

import com.mcp.deploy.util.TestConfig;
import com.mcp.deploy.util.TestUtil;
import com.mcp.order.common.ConstantValues;
import com.mcp.order.inter.common.CmdContext;
import com.mcp.order.inter.trade.ReqOrder;
import com.mcp.order.inter.trade.ReqT03Body;
import com.mcp.order.inter.trade.ReqTicket;
import com.mcp.order.inter.util.HttpClientUtil;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;

import java.util.ArrayList;
import java.util.List;

public class StationLot {
	
	private static Logger log = Logger.getLogger(StationLot.class);

	public static void main(String[] args) throws Exception {
		ObjectMapper om = new ObjectMapper();
		ReqT03Body t03body = new ReqT03Body();
		t03body.setPayType(ConstantValues.TOrder_PayType_Cash.getCode());
		ReqOrder reqOrder = new ReqOrder();
		reqOrder.setGameCode("T05");
		reqOrder.setTermCode("2013101009");
		reqOrder.setAmount(200);
		reqOrder.setOuterId("987654321");
		reqOrder.setPlatform("ANDROID");
		List<ReqTicket> tickets = new ArrayList<ReqTicket>();
		ReqTicket ticket = new ReqTicket();
		ticket.setAmount(200);
		ticket.setPlayTypeCode("23");
		ticket.setBetTypeCode("00");
		ticket.setMultiple(1);
		ticket.setNumbers("01,02,03");
		tickets.add(ticket);
		reqOrder.setTickets(tickets);
		t03body.setOrder(reqOrder);
		
		om.setFilters(CmdContext.getInstance().getFilterProviderByCode("T030101"));
		String bodyStr = om.writeValueAsString(t03body);
		String message = TestUtil.getCReqMessage("", "Q0003", bodyStr, "T03");
		log.info(message);
		String content = HttpClientUtil.request(TestConfig.IP, TestConfig.PORT, TestConfig.PATH, message, HttpClientUtil.POST, null);
		log.info(content);
	}

}
