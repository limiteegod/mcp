package com.mcp.deploy.cmd;

import com.mcp.core.util.CoreUtil;
import com.mcp.deploy.core.util.RemoteConfig;
import com.mcp.deploy.core.util.TestUtil;
import com.mcp.order.common.ConstantValues;
import com.mcp.order.common.Constants;
import com.mcp.order.inter.McpGtMsg;
import com.mcp.order.inter.account.ReqA04Body;
import com.mcp.order.inter.common.CmdContext;
import com.mcp.order.inter.print.RepP01Body;
import com.mcp.order.inter.print.RepP01Ticket;
import com.mcp.order.inter.print.ReqP01Body;
import com.mcp.order.inter.print.ReqP02Body;
import com.mcp.order.inter.trade.ReqOrder;
import com.mcp.order.inter.trade.ReqT01Body;
import com.mcp.order.inter.trade.ReqTicket;
import com.mcp.order.inter.util.CookieParam;
import com.mcp.order.inter.util.HttpClientUtil;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;

import java.util.ArrayList;
import java.util.List;

public class TestP02 {
	
	private static Logger log = Logger.getLogger(TestP02.class);

	public static void main(String[] args) throws Exception {
		/*CookieParam uCp = new CookieParam();
		
		ObjectMapper om = new ObjectMapper();
		ReqA04Body reqA04Body = new ReqA04Body();
		reqA04Body.setName("limitee2");
		reqA04Body.setPassword("123456");
		om.setFilters(CmdContext.getInstance().getFilterProviderByCode("A040101"));
		String bodyStr = om.writeValueAsString(reqA04Body);
		String message = TestUtil.getReqMessage("", "Q0001", bodyStr, "A04");
		System.out.println(message);
		String content = HttpClientUtil.request(RemoteConfig.IP, RemoteConfig.PORT, RemoteConfig.PATH, message, HttpClientUtil.POST, uCp);
		System.out.println(content);
		uCp.headersToCookies();
		
		om = new ObjectMapper();
		ReqT01Body t01body = new ReqT01Body();
		t01body.setPayType(ConstantValues.TOrder_PayType_Cash.getCode());
		ReqOrder reqOrder = new ReqOrder();
		reqOrder.setGameCode("T05");
		reqOrder.setTermCode("2013101009");
		reqOrder.setAmount(200);
		reqOrder.setPlatform("ANDROID");
		reqOrder.setStationId("91a730694eeb4dd4b3fca7a71a5e610d");
		List<ReqTicket> tickets = new ArrayList<ReqTicket>();
		ReqTicket ticket = new ReqTicket();
		ticket.setAmount(200);
		ticket.setPlayTypeCode("23");
		ticket.setBetTypeCode("00");
		ticket.setMultiple(1);
		ticket.setNumbers("01,02,03");
		tickets.add(ticket);
		reqOrder.setTickets(tickets);
		t01body.setOrder(reqOrder);
		
		om.setFilters(CmdContext.getInstance().getFilterProviderByCode("T010101"));
		bodyStr = om.writeValueAsString(t01body);
		message = TestUtil.getReqMessage("", "Q0001", bodyStr, "T01");
		log.info(message);
		content = HttpClientUtil.request(RemoteConfig.IP, RemoteConfig.PORT, RemoteConfig.PATH, message, HttpClientUtil.POST, uCp);
		log.info(content);
		
		ReqP01Body reqP01Body = new ReqP01Body();
		reqP01Body.setSize(10);
		om.setFilters(CmdContext.getInstance().getFilterProviderByCode("P010101"));
		bodyStr = om.writeValueAsString(reqP01Body);
		message = TestUtil.getCReqMessage("", "00001", bodyStr, "P01", "123456");
		log.info(message);
		content = HttpClientUtil.request(RemoteConfig.IP, RemoteConfig.PORT, RemoteConfig.PATH, message, HttpClientUtil.POST, null);
		log.info(content);
		
		McpGtMsg rep = om.readValue(content, McpGtMsg.class);
		String repBodyStr = rep.getBody();
		RepP01Body repbody = om.readValue(repBodyStr, RepP01Body.class);
		List<RepP01Ticket> tList = repbody.getTickets();
		for(RepP01Ticket t:tList)
		{
			ReqP02Body reqP02Body = new ReqP02Body();
			reqP02Body.setTicketId(t.getTicketId());
			reqP02Body.setPaper(false);
			reqP02Body.setCode(Constants.TICKET_PRINT_RECEIPT_SUCCESS);
			om.setFilters(CmdContext.getInstance().getFilterProviderByCode("P020101"));
			bodyStr = om.writeValueAsString(reqP02Body);
			message = TestUtil.getCReqMessage("", "00001", bodyStr, "P02", "123456");
			log.info(message);
			content = HttpClientUtil.request(RemoteConfig.IP, RemoteConfig.PORT, RemoteConfig.PATH, message, HttpClientUtil.POST, null);
			log.info(content);
		}*/

        String ticketId = "8763b518212d45fb897298e6a2744305";
        ObjectMapper om = new ObjectMapper();
        ReqP02Body reqP02Body = new ReqP02Body();
        reqP02Body.setTicketId(ticketId);
        reqP02Body.setPaper(true);
        reqP02Body.setCode(Constants.TICKET_PRINT_RECEIPT_SUCCESS);
        om.setFilters(CmdContext.getInstance().getFilterProviderByCode("P020101"));
        String bodyStr = om.writeValueAsString(reqP02Body);
        String message = TestUtil.getCReqMessage("", "C0002", bodyStr, "P02", "wzl0okmnhy6");
        log.info(message);
        String content = HttpClientUtil.request(RemoteConfig.IP, RemoteConfig.PORT, RemoteConfig.PATH, message, HttpClientUtil.POST, null);
        log.info(content);
	}
}
