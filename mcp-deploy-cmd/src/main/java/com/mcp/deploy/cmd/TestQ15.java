package com.mcp.deploy.cmd;

import com.mcp.core.util.CoreUtil;
import com.mcp.deploy.core.util.RemoteConfig;
import com.mcp.deploy.core.util.TestUtil;
import com.mcp.order.common.ConstantValues;
import com.mcp.order.inter.McpGtMsg;
import com.mcp.order.inter.account.ReqA04Body;
import com.mcp.order.inter.common.CmdContext;
import com.mcp.order.inter.query.ReqQ03Body;
import com.mcp.order.inter.query.ReqQ15Body;
import com.mcp.order.inter.trade.*;
import com.mcp.order.inter.util.CookieParam;
import com.mcp.order.inter.util.HttpClientUtil;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;

import java.util.ArrayList;
import java.util.List;

/**
 * 测试渠道查询订单信息
 */
public class TestQ15 {
	
	private static Logger log = Logger.getLogger(TestQ15.class);

	public static void main(String[] args) throws Exception {

        ObjectMapper om = new ObjectMapper();
        ReqT03Body reqT03Body = new ReqT03Body();
        ReqOrder reqOrder = new ReqOrder();
        reqOrder.setGameCode("T01");
        reqOrder.setTermCode("2014008");
        reqOrder.setAmount(200);
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

        ReqQ15Body reqQ15Body = new ReqQ15Body();
        reqQ15Body.setOrderId(repbody.getOrder().getId());
        reqQ15Body.setShowTickets(true);

        om.setFilters(CmdContext.getInstance().getFilterProviderByCode("Q150101"));
        bodyStr = om.writeValueAsString(reqQ15Body);
        message = TestUtil.getCReqMessage("", "Q0004", bodyStr, "Q15", "123456");
        log.info(message);
        content = HttpClientUtil.request(RemoteConfig.IP, RemoteConfig.PORT, RemoteConfig.PATH, message, HttpClientUtil.POST, null);
        log.info(content);

	}
}
