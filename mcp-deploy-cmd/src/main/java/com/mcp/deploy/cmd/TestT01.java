package com.mcp.deploy.cmd;

import com.mcp.core.util.CoreUtil;
import com.mcp.deploy.cmd.common.Station;
import com.mcp.deploy.cmd.common.Term;
import com.mcp.deploy.cmd.common.User;
import com.mcp.deploy.core.util.RemoteConfig;
import com.mcp.deploy.core.util.TestUtil;
import com.mcp.order.common.ConstantValues;
import com.mcp.order.inter.McpGtMsg;
import com.mcp.order.inter.account.ReqA04Body;
import com.mcp.order.inter.common.CmdContext;
import com.mcp.order.inter.trade.*;
import com.mcp.order.inter.util.CookieParam;
import com.mcp.order.inter.util.DigestFactory;
import com.mcp.order.inter.util.HttpClientUtil;
import com.mcp.order.util.DateTimeUtil;
import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TestT01 {
	
	private static Logger log = Logger.getLogger(TestT01.class);

	public static void main(String[] args) throws Exception {
        JsonNode bodyNode = TestA04.login("Q0003", "limitee2", "123456");
        String userId = bodyNode.get("customer").get("id").getTextValue();
        String key = bodyNode.get("st").getTextValue();
        for(int i = 0; i < 100; i++)
        {
            testT01(userId, key);
        }
	}

    public static void testT01(String userId, String key) throws Exception
    {
        ObjectMapper om = new ObjectMapper();
        ReqT01Body reqT01Body = new ReqT01Body();
        ReqOrder reqOrder = new ReqOrder();
        reqOrder.setGameCode("T01");
        reqOrder.setTermCode("2014001");
        reqOrder.setAmount(400);
        reqOrder.setOuterId(CoreUtil.getUUID());
        reqOrder.setPlatform("ANDROID");
        List<ReqTicket> tickets = new ArrayList<ReqTicket>();
        ReqTicket ticket = new ReqTicket();
        ticket.setAmount(200);
        ticket.setPlayTypeCode("00");
        ticket.setBetTypeCode("00");
        ticket.setMultiple(1);
        ticket.setNumbers("01,02,03,04,05|01,02");
        tickets.add(ticket);

        ReqTicket ticket2 = new ReqTicket();
        ticket2.setAmount(200);
        ticket2.setPlayTypeCode("00");
        ticket2.setBetTypeCode("00");
        ticket2.setMultiple(1);
        ticket2.setNumbers("09,13,23,31,33|02,04");
        tickets.add(ticket2);

        reqOrder.setTickets(tickets);
        reqT01Body.setOrder(reqOrder);

        om.setFilters(CmdContext.getInstance().getFilterProviderByCode("T010101"));
        String bodyStr = om.writeValueAsString(reqT01Body);
        log.info(bodyStr);
        String message = TestUtil.getDesCReqMessage(userId, Station.CODE, bodyStr, "T01", key);
        log.info(message);
        String content = HttpClientUtil.request(RemoteConfig.IP, RemoteConfig.PORT, RemoteConfig.PATH, message, HttpClientUtil.POST, null);
        log.info(content);
        McpGtMsg backMsg = om.readValue(content, McpGtMsg.class);
        log.info(DigestFactory.check(backMsg.getHead(), backMsg.getBody(), key));
    }

    public static void testT05(String userId, String key) throws Exception
    {
        ObjectMapper om = new ObjectMapper();
        ReqT01Body reqT01Body = new ReqT01Body();
        ReqOrder reqOrder = new ReqOrder();
        reqOrder.setGameCode("T05");
        reqOrder.setTermCode(Term.CODE);
        reqOrder.setAmount(2200);
        reqOrder.setPlatform("ANDROID");
        List<ReqTicket> tickets = new ArrayList<ReqTicket>();
        ReqTicket ticket = new ReqTicket();
        ticket.setAmount(2200);
        ticket.setPlayTypeCode("21");
        ticket.setBetTypeCode("01");
        ticket.setMultiple(1);
        ticket.setNumbers("01,02,03,04,05,06,07,08,09,10,11");
        tickets.add(ticket);
        reqOrder.setTickets(tickets);
        reqT01Body.setOrder(reqOrder);

        om.setFilters(CmdContext.getInstance().getFilterProviderByCode("T010101"));
        String bodyStr = om.writeValueAsString(reqT01Body);
        log.info(bodyStr);
        //String message = TestUtil.getCReqMessage(userId, Station.CODE, bodyStr, "T01", key);
        String message = TestUtil.getDesCReqMessage(userId, Station.CODE, bodyStr, "T01", key);
        log.info(message);
        String content = HttpClientUtil.request(RemoteConfig.IP, RemoteConfig.PORT, RemoteConfig.PATH, message, HttpClientUtil.POST, null);
        log.info(content);
        McpGtMsg backMsg = om.readValue(content, McpGtMsg.class);
        log.info(DigestFactory.check(backMsg.getHead(), backMsg.getBody(), key));
    }
}
