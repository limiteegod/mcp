/**
 * 
 */
package com.mcp.deploy.lot;

import com.mcp.deploy.util.TestConfig;
import com.mcp.deploy.util.TestUtil;
import com.mcp.order.common.ConstantValues;
import com.mcp.order.inter.account.ReqA04Body;
import com.mcp.order.inter.common.CmdContext;
import com.mcp.order.inter.trade.ReqOrder;
import com.mcp.order.inter.trade.ReqT01Body;
import com.mcp.order.inter.trade.ReqTicket;
import com.mcp.order.inter.util.CookieParam;
import com.mcp.order.inter.util.HttpClientUtil;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ming.li
 */
public class LotToFromCjToStation {
	
	private static Logger log = Logger.getLogger(LotToFromCjToStation.class);
	
	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		CookieParam uCp = new CookieParam();
		
		ObjectMapper om = new ObjectMapper();
		ReqA04Body reqA04Body = new ReqA04Body();
		reqA04Body.setName("limitee2");
		reqA04Body.setPassword("123456");
		om.setFilters(CmdContext.getInstance().getFilterProviderByCode("A040101"));
		String bodyStr = om.writeValueAsString(reqA04Body);
		String message = TestUtil.getReqMessage("", "Q0001", bodyStr, "A04");
		System.out.println(message);
		String content = HttpClientUtil.request(TestConfig.IP, TestConfig.PORT, TestConfig.PATH, message, HttpClientUtil.POST, uCp);
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
		content = HttpClientUtil.request(TestConfig.IP, TestConfig.PORT, TestConfig.PATH, message, HttpClientUtil.POST, uCp);
		log.info(content);
	}

}
