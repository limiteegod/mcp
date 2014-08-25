package com.mcp.deploy.cmd;

import com.mcp.deploy.core.util.RemoteConfig;
import com.mcp.deploy.core.util.TestUtil;
import com.mcp.order.inter.McpGtMsg;
import com.mcp.order.inter.common.CmdContext;
import com.mcp.order.inter.query.ReqQ15Body;
import com.mcp.order.inter.query.ReqQ16Body;
import com.mcp.order.inter.trade.RepT03Body;
import com.mcp.order.inter.trade.ReqOrder;
import com.mcp.order.inter.trade.ReqT03Body;
import com.mcp.order.inter.trade.ReqTicket;
import com.mcp.order.inter.util.HttpClientUtil;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;

import java.util.ArrayList;
import java.util.List;

/**
 * 测试渠道查询订单信息
 */
public class TestQ16 {
	
	private static Logger log = Logger.getLogger(TestQ16.class);

	public static void main(String[] args) throws Exception {
        ObjectMapper om = new ObjectMapper();
        ReqQ16Body reqQ16Body = new ReqQ16Body();
        reqQ16Body.setGameCode("T05");
        reqQ16Body.setTermCode("2013101009");

        om.setFilters(CmdContext.getInstance().getFilterProviderByCode("Q160101"));
        String bodyStr = om.writeValueAsString(reqQ16Body);
        String message = TestUtil.getCReqMessage("", "Q0003", bodyStr, "Q16", "123456");
        log.info(message);
        String content = HttpClientUtil.request(RemoteConfig.IP, RemoteConfig.PORT, RemoteConfig.PATH, message, HttpClientUtil.POST, null);
        log.info(content);

	}
}
