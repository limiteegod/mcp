package com.mcp.deploy.cmd;

import com.mcp.deploy.core.util.RemoteConfig;
import com.mcp.deploy.core.util.TestUtil;
import com.mcp.order.common.ConstantValues;
import com.mcp.order.inter.McpGtMsg;
import com.mcp.order.inter.account.ReqA04Body;
import com.mcp.order.inter.common.CmdContext;
import com.mcp.order.inter.query.ReqQ03Body;
import com.mcp.order.inter.trade.*;
import com.mcp.order.inter.util.CookieParam;
import com.mcp.order.inter.util.HttpClientUtil;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;

import java.util.ArrayList;
import java.util.List;

public class TestQ03 {
	
	private static Logger log = Logger.getLogger(TestQ03.class);

	public static void main(String[] args) throws Exception {
		CookieParam uCp = new CookieParam();
		ObjectMapper om = new ObjectMapper();
		ReqA04Body reqA04Body = new ReqA04Body();
		reqA04Body.setName("limitee2");
		reqA04Body.setPassword("123456");
		
		om.setFilters(CmdContext.getInstance().getFilterProviderByCode("A040101"));
		String bodyStr = om.writeValueAsString(reqA04Body);
		String message = TestUtil.getReqMessage("", "Q0003", bodyStr, "A04");
		log.info(message);
		String content = HttpClientUtil.request(RemoteConfig.IP, RemoteConfig.PORT, RemoteConfig.PATH, message, HttpClientUtil.POST, uCp);
		log.info(content);
		uCp.headersToCookies();
		
		log.info("第三方支付方式投注...............");
		
		ReqT01Body reqT01Body = new ReqT01Body();
		ReqOrder reqOrder = new ReqOrder();
		reqOrder.setGameCode("T05");
		reqOrder.setTermCode("2013101009");
		reqOrder.setAmount(200);
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
		reqT01Body.setOrder(reqOrder);
		reqT01Body.setPayType(ConstantValues.TOrder_PayType_Company.getCode());
		
		om.setFilters(CmdContext.getInstance().getFilterProviderByCode("T010101"));
		bodyStr = om.writeValueAsString(reqT01Body);
		message = TestUtil.getReqMessage("", "Q0003", bodyStr, "T01");
		log.info(message);
		content = HttpClientUtil.request(RemoteConfig.IP, RemoteConfig.PORT, RemoteConfig.PATH, message, HttpClientUtil.POST, uCp);
		log.info(content);
		
		McpGtMsg rep = om.readValue(content, McpGtMsg.class);
		String repBodyStr = rep.getBody();
		RepT01Body repbody = om.readValue(repBodyStr, RepT01Body.class);
		
		log.info("第三方支付...............");
		
		ReqT02Body reqT02Body = new ReqT02Body();
		reqT02Body.setOrderId(repbody.getOrder().getId());
		om.setFilters(CmdContext.getInstance().getFilterProviderByCode("T020101"));
		bodyStr = om.writeValueAsString(reqT02Body);
		message = TestUtil.getCReqMessage("", "Q0003", bodyStr, "T02", "123456");
		log.info(message);
		content = HttpClientUtil.request(RemoteConfig.IP, RemoteConfig.PORT, RemoteConfig.PATH, message, HttpClientUtil.POST, null);
		log.info(content);
		
		ReqQ03Body reqQ03Body = new ReqQ03Body();
		reqQ03Body.setOrderId(repbody.getOrder().getId());
		om.setFilters(CmdContext.getInstance().getFilterProviderByCode("Q030101"));
		bodyStr = om.writeValueAsString(reqQ03Body);
		message = TestUtil.getReqMessage("", "Q0003", bodyStr, "Q03");
		log.info(bodyStr);
		content = HttpClientUtil.request(RemoteConfig.IP, RemoteConfig.PORT, RemoteConfig.PATH, message, HttpClientUtil.POST, uCp);
        log.info(om.readValue(content, McpGtMsg.class).getBody());
	}
}
