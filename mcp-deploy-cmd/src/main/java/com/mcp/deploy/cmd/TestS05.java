package com.mcp.deploy.cmd;

import com.mcp.deploy.cmd.common.Station;
import com.mcp.deploy.cmd.common.Term;
import com.mcp.deploy.cmd.common.User;
import com.mcp.deploy.core.util.RemoteConfig;
import com.mcp.deploy.core.util.TestUtil;
import com.mcp.order.common.ConstantValues;
import com.mcp.order.inter.McpGtMsg;
import com.mcp.order.inter.account.ReqA04Body;
import com.mcp.order.inter.common.CmdContext;
import com.mcp.order.inter.scheme.RepS04Body;
import com.mcp.order.inter.scheme.RepS05Body;
import com.mcp.order.inter.scheme.ReqS04Body;
import com.mcp.order.inter.scheme.ReqS05Body;
import com.mcp.order.inter.trade.RepT03Body;
import com.mcp.order.inter.util.CookieParam;
import com.mcp.order.inter.util.HttpClientUtil;
import com.mcp.order.model.ts.TOrder;
import com.mcp.order.model.ts.TTicket;
import com.mcp.scheme.model.SchemeHm;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;

import java.util.ArrayList;
import java.util.List;

public class TestS05 {
	
	private static Logger log = Logger.getLogger(TestS05.class);

	public static void main(String[] args) throws Exception {
		CookieParam uCp = new CookieParam();
		
		ObjectMapper om = new ObjectMapper();
		ReqA04Body reqA04Body = new ReqA04Body();
		reqA04Body.setName(User.NAME);
		reqA04Body.setPassword(User.PASSWORD);
		om.setFilters(CmdContext.getInstance().getFilterProviderByCode("A040101"));
		String bodyStr = om.writeValueAsString(reqA04Body);
		String message = TestUtil.getReqMessage("", Station.CODE, bodyStr, "A04");
		log.info(message);
		String content = HttpClientUtil.request(RemoteConfig.IP, RemoteConfig.PORT, RemoteConfig.PATH, message, HttpClientUtil.POST, uCp);
		log.info(content);
		uCp.headersToCookies();
		
		ReqS04Body reqS04Body = new ReqS04Body();
		SchemeHm scheme = new SchemeHm();
        scheme.setAmount(2200);
        List<TOrder> oList = new ArrayList<TOrder>();
        TOrder order = new TOrder();
        order.setGameCode("T05");
        //order.setTermCode(Term.CODE);
        order.setPlatform("html5");
        order.setAmount(2200);
        List<TTicket> tList = new ArrayList<TTicket>();
        TTicket ticket = new TTicket();
        ticket.setAmount(2200);
        ticket.setPlayTypeCode("21");
        ticket.setBetTypeCode("01");
        ticket.setMultiple(1);
        ticket.setNumbers("01,02,03,04,05,06,07,08,09,10,11");
        tList.add(ticket);
        order.setTickets(tList);
        oList.add(order);
        scheme.setOrders(oList);
        scheme.setcAmount(2200);
        scheme.setType(ConstantValues.TSchemeHm_Type_Gift.getCode());
        reqS04Body.setScheme(scheme);
		om.setFilters(CmdContext.getInstance().getFilterProviderByCode("S040101"));
		bodyStr = om.writeValueAsString(reqS04Body);
		message = TestUtil.getReqMessage("", Station.CODE, bodyStr, "S04");
		log.info(message);
		content = HttpClientUtil.request(RemoteConfig.IP, RemoteConfig.PORT, RemoteConfig.PATH, message, HttpClientUtil.POST, uCp);
		log.info(content);

        McpGtMsg rep = om.readValue(content, McpGtMsg.class);
        String repBodyStr = rep.getBody();
        RepS04Body repbody = om.readValue(repBodyStr, RepS04Body.class);
        String schemeId = repbody.getScheme().getId();

        ReqS05Body reqS05Body = new ReqS05Body();
        //reqS05Body.setAmount(400);
        reqS05Body.setSchemeId(schemeId);

        bodyStr = om.writeValueAsString(reqS05Body);
        message = TestUtil.getReqMessage("", Station.CODE, bodyStr, "S05");
        log.info(message);
        content = HttpClientUtil.request(RemoteConfig.IP, RemoteConfig.PORT, RemoteConfig.PATH, message, HttpClientUtil.POST, uCp);
        log.info(content);

        reqS05Body = new ReqS05Body();
        //reqS05Body.setAmount(1400);
        reqS05Body.setSchemeId(schemeId);

        bodyStr = om.writeValueAsString(reqS05Body);
        message = TestUtil.getReqMessage("", Station.CODE, bodyStr, "S05");
        log.info(message);
        content = HttpClientUtil.request(RemoteConfig.IP, RemoteConfig.PORT, RemoteConfig.PATH, message, HttpClientUtil.POST, uCp);
        log.info(content);

        reqS05Body = new ReqS05Body();
        //reqS05Body.setAmount(1400);
        reqS05Body.setSchemeId(schemeId);

        bodyStr = om.writeValueAsString(reqS05Body);
        message = TestUtil.getReqMessage("", Station.CODE, bodyStr, "S05");
        log.info(message);
        content = HttpClientUtil.request(RemoteConfig.IP, RemoteConfig.PORT, RemoteConfig.PATH, message, HttpClientUtil.POST, uCp);
        log.info(content);
	}
}
