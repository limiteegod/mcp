package com.mcp.deploy.cmd;

import com.mcp.core.util.CoreUtil;
import com.mcp.deploy.core.util.RemoteConfig;
import com.mcp.deploy.core.util.TestUtil;
import com.mcp.order.common.Constants;
import com.mcp.order.inter.McpGtMsg;
import com.mcp.order.inter.common.CmdContext;
import com.mcp.order.inter.trade.*;
import com.mcp.order.inter.util.HttpClientUtil;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;

import java.util.ArrayList;
import java.util.List;

public class TestT03 {
	
	private static Logger log = Logger.getLogger(TestT03.class);

	public static void main(String[] args) throws Exception {
        for(int i = 0; i < 200; i++)
        {
            lot();
        }
	}

    public static void lot() throws Exception
    {
        ObjectMapper om = new ObjectMapper();
        ReqT03Body reqT03Body = new ReqT03Body();
        ReqOrder reqOrder = new ReqOrder();
        reqOrder.setGameCode("T01");
        reqOrder.setTermCode("2014009");
        reqOrder.setAmount(400);
        reqOrder.setOuterId(CoreUtil.getUUID());
        reqOrder.setPlatform("ANDROID");
        List<ReqTicket> tickets = new ArrayList<ReqTicket>();
        ReqTicket ticket = new ReqTicket();
        ticket.setAmount(200);
        ticket.setPlayTypeCode("00");
        ticket.setBetTypeCode("00");
        ticket.setMultiple(1);
        ticket.setNumbers("01,07,08,22,23|01,02");
        tickets.add(ticket);
        ticket = new ReqTicket();
        ticket.setAmount(200);
        ticket.setPlayTypeCode("00");
        ticket.setBetTypeCode("00");
        ticket.setMultiple(1);
        ticket.setNumbers("01,07,08,22,23|01,02");
        tickets.add(ticket);

        reqOrder.setTickets(tickets);
        reqT03Body.setOrder(reqOrder);

        om.setFilters(CmdContext.getInstance().getFilterProviderByCode("T030101"));
        String bodyStr = om.writeValueAsString(reqT03Body);
        String message = TestUtil.getCReqMessage("", "Q0004", bodyStr, "T03", "123456");
        log.info(message);
        String content = HttpClientUtil.request(RemoteConfig.IP, RemoteConfig.PORT, RemoteConfig.PATH, message, HttpClientUtil.POST, null);
        log.info(content);

        McpGtMsg rep = om.readValue(content, McpGtMsg.class);
        String repBodyStr = rep.getBody();
        RepT03Body repbody = om.readValue(repBodyStr, RepT03Body.class);
    }

    public static void lotF04() throws Exception
    {
        ObjectMapper om = new ObjectMapper();
        ReqT03Body reqT03Body = new ReqT03Body();
        ReqOrder reqOrder = new ReqOrder();
        reqOrder.setGameCode("F04");
        reqOrder.setTermCode("2014001");
        reqOrder.setAmount(400);
        reqOrder.setOuterId(CoreUtil.getUUID());
        reqOrder.setPlatform("ANDROID");
        List<ReqTicket> tickets = new ArrayList<ReqTicket>();
        ReqTicket ticket = new ReqTicket();
        ticket.setAmount(200);
        ticket.setPlayTypeCode("01");
        ticket.setBetTypeCode("00");
        ticket.setMultiple(1);
        ticket.setNumbers("1,2,3");
        tickets.add(ticket);

        ReqTicket ticket2 = new ReqTicket();
        ticket2.setAmount(200);
        ticket2.setPlayTypeCode("01");
        ticket2.setBetTypeCode("00");
        ticket2.setMultiple(1);
        ticket2.setNumbers("1,2,3");
        tickets.add(ticket2);

        reqOrder.setTickets(tickets);
        reqT03Body.setOrder(reqOrder);

        om.setFilters(CmdContext.getInstance().getFilterProviderByCode("T030101"));
        String bodyStr = om.writeValueAsString(reqT03Body);
        String message = TestUtil.getCReqMessage("", "Q0003", bodyStr, "T03", "CePEYAR/Snc=");
        log.info(message);
        String content = HttpClientUtil.request(RemoteConfig.IP, RemoteConfig.PORT, RemoteConfig.PATH, message, HttpClientUtil.POST, null);
        log.info(content);

        McpGtMsg rep = om.readValue(content, McpGtMsg.class);
        String repBodyStr = rep.getBody();
        RepT03Body repbody = om.readValue(repBodyStr, RepT03Body.class);
    }

    public static void lotT06() throws Exception
    {
        ObjectMapper om = new ObjectMapper();
        ReqT03Body reqT03Body = new ReqT03Body();
        ReqOrder reqOrder = new ReqOrder();
        reqOrder.setGameCode("T06");
        reqOrder.setTermCode("2014003");
        reqOrder.setAmount(1200);
        reqOrder.setOuterId(CoreUtil.getUUID());
        reqOrder.setPlatform("ANDROID");
        List<ReqTicket> tickets = new ArrayList<ReqTicket>();
        ReqTicket ticket = new ReqTicket();
        ticket.setAmount(200);
        ticket.setPlayTypeCode("01");
        ticket.setBetTypeCode("00");
        ticket.setMultiple(1);
        ticket.setNumbers("1,2,3,4");
        tickets.add(ticket);

        ReqTicket ticket2 = new ReqTicket();
        ticket2.setAmount(1000);
        ticket2.setPlayTypeCode("01");
        ticket2.setBetTypeCode("01");
        ticket2.setMultiple(1);
        ticket2.setNumbers("1,2,3,4,5");
        tickets.add(ticket2);

        reqOrder.setTickets(tickets);
        reqT03Body.setOrder(reqOrder);

        om.setFilters(CmdContext.getInstance().getFilterProviderByCode("T030101"));
        String bodyStr = om.writeValueAsString(reqT03Body);
        String message = TestUtil.getCReqMessage("", "Q0003", bodyStr, "T03", "CePEYAR/Snc=");
        log.info(message);
        String content = HttpClientUtil.request(RemoteConfig.IP, RemoteConfig.PORT, RemoteConfig.PATH, message, HttpClientUtil.POST, null);
        log.info(content);

        McpGtMsg rep = om.readValue(content, McpGtMsg.class);
        String repBodyStr = rep.getBody();
        RepT03Body repbody = om.readValue(repBodyStr, RepT03Body.class);
    }

    public static void lotF01() throws Exception
    {
        ObjectMapper om = new ObjectMapper();
        ReqT03Body reqT03Body = new ReqT03Body();
        ReqOrder reqOrder = new ReqOrder();
        reqOrder.setGameCode("F01");
        reqOrder.setTermCode("2014176");
        reqOrder.setAmount(200);
        reqOrder.setOuterId(CoreUtil.getUUID());
        reqOrder.setPlatform("ANDROID");
        List<ReqTicket> tickets = new ArrayList<ReqTicket>();
        ReqTicket ticket = new ReqTicket();
        ticket.setAmount(200);
        ticket.setPlayTypeCode("00");
        ticket.setBetTypeCode("00");
        ticket.setMultiple(1);
        ticket.setNumbers("09,14,17,18,21,25|15");
        tickets.add(ticket);

        reqOrder.setTickets(tickets);
        reqT03Body.setOrder(reqOrder);

        om.setFilters(CmdContext.getInstance().getFilterProviderByCode("T030101"));
        String bodyStr = om.writeValueAsString(reqT03Body);
        String message = TestUtil.getCReqMessage("", "Q0004", bodyStr, "T03", "123456");
        log.info(message);
        String content = HttpClientUtil.request(RemoteConfig.IP, RemoteConfig.PORT, RemoteConfig.PATH, message, HttpClientUtil.POST, null);
        log.info(content);

        McpGtMsg rep = om.readValue(content, McpGtMsg.class);
        String repBodyStr = rep.getBody();
        RepT03Body repbody = om.readValue(repBodyStr, RepT03Body.class);
    }
}
