package com.mcp.deploy.cmd;

import com.mcp.deploy.core.util.RemoteConfig;
import com.mcp.deploy.core.util.TestUtil;
import com.mcp.order.common.ConstantValues;
import com.mcp.order.inter.account.ReqA04Body;
import com.mcp.order.inter.common.CmdContext;
import com.mcp.order.inter.print.ReqP01Body;
import com.mcp.order.inter.print.ReqP10Body;
import com.mcp.order.inter.print.ReqP11Body;
import com.mcp.order.inter.trade.ReqOrder;
import com.mcp.order.inter.trade.ReqT01Body;
import com.mcp.order.inter.trade.ReqTicket;
import com.mcp.order.inter.util.CookieParam;
import com.mcp.order.inter.util.HttpClientUtil;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;

import java.util.ArrayList;
import java.util.List;

public class TestP11 {
	
	private static Logger log = Logger.getLogger(TestP11.class);

	public static void main(String[] args) throws Exception {
        ObjectMapper om = new ObjectMapper();
        ReqP11Body reqP11Body = new ReqP11Body();
        reqP11Body.setTicketId("e950b9daf72849f4a22f971cd6a5dd55");
        om.setFilters(CmdContext.getInstance().getFilterProviderByCode("P110101"));
        String bodyStr = om.writeValueAsString(reqP11Body);
        String message = TestUtil.getCReqMessage("", "C0002", bodyStr, "P11", "wzl0okmnhy6");
        log.info(message);
        String content = HttpClientUtil.request(RemoteConfig.IP, RemoteConfig.PORT, RemoteConfig.PATH, message, HttpClientUtil.POST, null);
        log.info(content);
	}
}
