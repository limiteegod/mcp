/**
 * 
 */
package com.mcp.jbank;

import com.deploy.filter.TestUtil;
import com.mcp.deploy.print.TestConfig;
import com.mcp.order.common.ConstantValues;
import com.mcp.order.common.Constants;
import com.mcp.order.inter.McpGtMsg;
import com.mcp.order.inter.common.CmdContext;
import com.mcp.order.inter.jbank.RepJ01Body;
import com.mcp.order.inter.jbank.RepJ01Cookie;
import com.mcp.order.inter.jbank.ReqJ01Body;
import com.mcp.order.inter.jbank.ReqJ05Body;
import com.mcp.order.inter.print.ReqP05Body;
import com.mcp.order.inter.trade.RepT01Body;
import com.mcp.order.inter.trade.ReqOrder;
import com.mcp.order.inter.trade.ReqT01Body;
import com.mcp.order.inter.trade.ReqTicket;
import com.mcp.order.inter.util.CookieParam;
import com.mcp.order.inter.util.HttpClientUtil;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;

import javax.servlet.http.Cookie;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ming.li
 *
 */
public class TradeTest {
	
	private static Logger log = Logger.getLogger(TradeTest.class);
	
	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		
		CookieParam sCp = new CookieParam();
		
		ObjectMapper om = new ObjectMapper();
		ReqP05Body body = new ReqP05Body();
		body.setCode("Q000201");
		body.setPassword("123456");
		body.setType(Constants.STATION_OP_LOGIN);
		om.setFilters(CmdContext.getInstance().getFilterProviderByCode("P050101"));
		String bodyStr = om.writeValueAsString(body);
		String message = TestUtil.getReqMessage("", "Q0002", bodyStr, "P05");
		log.info(message);
		String content = HttpClientUtil.request(TestConfig.IP, TestConfig.PORT, TestConfig.PATH, message, HttpClientUtil.POST, sCp);
		log.info(content);
		sCp.headersToCookies();
		
		om = new ObjectMapper();
		ReqJ01Body j01body = new ReqJ01Body();
		j01body.setName("limitee2");
		j01body.setPassword("123456");
		j01body.setRealName("李明");
		j01body.setCreateWhenNotExist(false);
		om.setFilters(CmdContext.getInstance().getFilterProviderByCode("J010101"));
		bodyStr = om.writeValueAsString(j01body);
		message = TestUtil.getReqMessage("", "Q0002", bodyStr, "J01");
		log.info(message);
		content = HttpClientUtil.request(TestConfig.IP, TestConfig.PORT, TestConfig.PATH, message, HttpClientUtil.POST, sCp);
		log.info(content);
		
		om = new ObjectMapper();
		McpGtMsg rep = om.readValue(content, McpGtMsg.class);
		String repBodyStr = rep.getBody();
		RepJ01Body repJ01body = om.readValue(repBodyStr, RepJ01Body.class);
		RepJ01Cookie cookie = repJ01body.getCookie();
		Cookie[] cookList = new Cookie[3];
		Cookie c0 = new Cookie("userId", cookie.getUserId());
		c0.setPath("/");
		Cookie c1 = new Cookie("userType", String.valueOf(cookie.getUserType()));
		c1.setPath("/");
		Cookie c2 = new Cookie("userMd5", cookie.getUserMd5());
		c2.setPath("/");
		cookList[0] = c0;
		cookList[1] = c1;
		cookList[2] = c2;
		CookieParam uCp = new CookieParam();
		uCp.setCookies(cookList);
		
		om = new ObjectMapper();
		ReqT01Body t01body = new ReqT01Body();
		t01body.setPayType(ConstantValues.TOrder_PayType_Company.getCode());
		ReqOrder reqOrder = new ReqOrder();
		reqOrder.setGameCode("F01");
		reqOrder.setAmount(200);
		reqOrder.setPlatform("ANDROID");
		List<ReqTicket> tickets = new ArrayList<ReqTicket>();
		ReqTicket ticket = new ReqTicket();
		ticket.setAmount(200);
		ticket.setBetTypeCode("00");
		ticket.setPlayTypeCode("00");
		ticket.setMultiple(1);
		ticket.setNumbers("01,02,03,04,05,06|02");
		tickets.add(ticket);
		reqOrder.setTickets(tickets);
		t01body.setOrder(reqOrder);
		
		om.setFilters(CmdContext.getInstance().getFilterProviderByCode("T010101"));
		bodyStr = om.writeValueAsString(t01body);
		message = TestUtil.getReqMessage("", "Q0002", bodyStr, "T01");
		log.info(message);
		content = HttpClientUtil.request(TestConfig.IP, TestConfig.PORT, TestConfig.PATH, message, HttpClientUtil.POST, uCp);
		log.info(content);
		
		om = new ObjectMapper();
		McpGtMsg repT01Msg = om.readValue(content, McpGtMsg.class);
		String repT01BodyStr = repT01Msg.getBody();
		RepT01Body repT01Body = om.readValue(repT01BodyStr, RepT01Body.class);
		String orderId = repT01Body.getOrder().getId();
		
		om = new ObjectMapper();
		ReqJ05Body reqJ05Body = new ReqJ05Body();
		reqJ05Body.setOrderId(orderId);
		om.setFilters(CmdContext.getInstance().getFilterProviderByCode("J050101"));
		bodyStr = om.writeValueAsString(reqJ05Body);
		message = TestUtil.getReqMessage("", "Q0002", bodyStr, "J05");
		log.info(message);
		content = HttpClientUtil.request(TestConfig.IP, TestConfig.PORT, TestConfig.PATH, message, HttpClientUtil.POST, sCp);
		log.info(content);
	}

}
