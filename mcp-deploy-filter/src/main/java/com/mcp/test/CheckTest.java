package com.mcp.test;

import com.deploy.filter.TestUtil;
import com.mcp.core.util.CoreUtil;
import com.mcp.deploy.print.TestConfig;
import com.mcp.order.common.ConstantValues;
import com.mcp.order.inter.account.ReqA04Body;
import com.mcp.order.inter.common.CmdContext;
import com.mcp.order.inter.notify.ReqN04Body;
import com.mcp.order.inter.trade.ReqOrder;
import com.mcp.order.inter.trade.ReqT01Body;
import com.mcp.order.inter.trade.ReqTicket;
import com.mcp.order.inter.util.CookieParam;
import com.mcp.order.inter.util.HttpClientUtil;
import com.mcp.order.model.ts.Term;
import com.mcp.order.status.TermState;
import com.mcp.order.util.DateTimeUtil;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CheckTest {
	
	private static Logger log = Logger.getLogger(CheckTest.class);
	
	public static void main(String[] args) throws Exception
	{
		Term t = addTerm();
		Thread.sleep(15000);
		lot(t);
	}
	
	public static String getTermCode()
	{
		String df = "yyyyMMddHHmmss";
		SimpleDateFormat dateFormat = new SimpleDateFormat(df);
		return dateFormat.format(new Date());
	}
	
	public static void lot(Term t) throws Exception
	{
		CookieParam uCp = new CookieParam();
		
		ObjectMapper om = new ObjectMapper();
		ReqA04Body reqA04Body = new ReqA04Body();
		reqA04Body.setName("limitee2");
		reqA04Body.setPassword("123456");
		om.setFilters(CmdContext.getInstance().getFilterProviderByCode("A040101"));
		String bodyStr = om.writeValueAsString(reqA04Body);
		String message = TestUtil.getReqMessage("", "Q0003", bodyStr, "A04");
		System.out.println(message);
		String content = HttpClientUtil.request(TestConfig.IP, TestConfig.PORT, TestConfig.PATH, message, HttpClientUtil.POST, uCp);
		System.out.println(content);
		uCp.headersToCookies();
		
		om = new ObjectMapper();
		ReqT01Body t01body = new ReqT01Body();
		t01body.setPayType(ConstantValues.TOrder_PayType_Cash.getCode());
		ReqOrder reqOrder = new ReqOrder();
		reqOrder.setGameCode("T05");
		reqOrder.setTermCode(t.getCode());
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
		t01body.setOrder(reqOrder);
		
		om.setFilters(CmdContext.getInstance().getFilterProviderByCode("T010101"));
		bodyStr = om.writeValueAsString(t01body);
		message = TestUtil.getReqMessage("", "Q0003", bodyStr, "T01");
		log.info(message);
		content = HttpClientUtil.request(TestConfig.IP, TestConfig.PORT, TestConfig.PATH, message, HttpClientUtil.POST, uCp);
		log.info(content);
	}
	
	public static Term addTerm() throws Exception
	{
		ObjectMapper om = new ObjectMapper();
		ReqN04Body body = new ReqN04Body();
		
		Term t = new Term();
		t.setId(CoreUtil.getUUID());
		t.setGameCode("T05");
		t.setCode(getTermCode());
		t.setOpenTime(new Date());
		t.setEndTime(DateTimeUtil.getDateAfterMilliseconds(70000));
		t.setCreateTime(new Date());
		t.setStatus(TermState.NOT_ON_SALE.getCode());
		List<Term> tList = new ArrayList<Term>();
		tList.add(t);
		
		body.setTerms(tList);
		
		om.setFilters(CmdContext.getInstance().getFilterProviderByCode("N040101"));
		String bodyStr = om.writeValueAsString(body);
		String message = TestUtil.getReqMessage("", "Q0001", bodyStr, "N04");
		System.out.println(message);
		String content = HttpClientUtil.request(TestConfig.IP, TestConfig.PORT, TestConfig.PATH, message, HttpClientUtil.POST, null);
		System.out.println(content);
		
		return t;
	}
}
