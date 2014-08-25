package com.mcp.deploy.cmd;

import com.mcp.core.util.CoreUtil;
import com.mcp.deploy.core.util.RemoteConfig;
import com.mcp.deploy.core.util.TestUtil;
import com.mcp.order.common.ConstantValues;
import com.mcp.order.inter.McpGtMsg;
import com.mcp.order.inter.cash.ReqC04Body;
import com.mcp.order.inter.common.CmdContext;
import com.mcp.order.inter.trade.RepT01Body;
import com.mcp.order.inter.trade.ReqOrder;
import com.mcp.order.inter.trade.ReqT01Body;
import com.mcp.order.inter.trade.ReqT02Body;
import com.mcp.order.inter.util.DigestFactory;
import com.mcp.order.inter.util.HttpClientUtil;
import com.mcp.order.util.DateTimeUtil;
import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;

public class TestC04 {
	
	private static Logger log = Logger.getLogger(TestC04.class);

	public static void main(String[] args) throws Exception {
        ObjectMapper om = new ObjectMapper();
		ReqC04Body reqC04Body = new ReqC04Body();
        reqC04Body.setName("limitee2");
        reqC04Body.setAmount(200);
        reqC04Body.setExpiredTime(DateTimeUtil.getDateAfterMilliseconds(4*24*60*60*1000));
		om.setFilters(CmdContext.getInstance().getFilterProviderByCode("C040101"));
		String bodyStr = om.writeValueAsString(reqC04Body);
        log.info(bodyStr);
        //String message = TestUtil.getCReqMessage("", "Q0003", bodyStr, "C04", "CePEYAR/Snc=");
        String message = TestUtil.getDesCReqMessage("", "Q0003", bodyStr, "C04", "CePEYAR/Snc=");
		log.info(message);
        String content = HttpClientUtil.request(RemoteConfig.IP, RemoteConfig.PORT, RemoteConfig.PATH, message, HttpClientUtil.POST, null);
        log.info(content);
        McpGtMsg backMsg = om.readValue(content, McpGtMsg.class);
        log.info(DigestFactory.check(backMsg.getHead(), backMsg.getBody(), "CePEYAR/Snc="));
	}
}
