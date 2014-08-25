package com.mcp.deploy.cmd;

import com.mcp.deploy.core.util.RemoteConfig;
import com.mcp.deploy.core.util.TestUtil;
import com.mcp.order.common.ConstantValues;
import com.mcp.order.inter.McpGtMsg;
import com.mcp.order.inter.account.ReqA02Body;
import com.mcp.order.inter.cash.ReqC04Body;
import com.mcp.order.inter.cash.ReqC05Body;
import com.mcp.order.inter.common.CmdContext;
import com.mcp.order.inter.util.DigestFactory;
import com.mcp.order.inter.util.HttpClientUtil;
import com.mcp.order.util.DateTimeUtil;
import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;

public class TestC05 {
	
	private static Logger log = Logger.getLogger(TestC05.class);

	public static void main(String[] args) throws Exception {
        ObjectMapper om = new ObjectMapper();
        JsonNode bodyNode = TestA04.login("Q0003", "limitee2", "123456");
        String userId = bodyNode.get("customer").get("id").getTextValue();
        String key = bodyNode.get("st").getTextValue();

        ReqC05Body reqC05Body = new ReqC05Body();
        reqC05Body.setSize(4);
        reqC05Body.setStatus(ConstantValues.COUPON_STATUS_AVAILABLE.getCode());
        om.setFilters(CmdContext.getInstance().getFilterProviderByCode("C050101"));
        String bodyStr = om.writeValueAsString(reqC05Body);
        log.info(bodyStr);
        //String message = TestUtil.getCReqMessage(userId, "Q0003", bodyStr, "C05", key);
        String message = TestUtil.getDesCReqMessage(userId, "Q0003", bodyStr, "C05", key);
        log.info(message);
        String content = HttpClientUtil.request(RemoteConfig.IP, RemoteConfig.PORT, RemoteConfig.PATH, message, HttpClientUtil.POST, null);
        log.info(content);
        McpGtMsg backMsg = om.readValue(content, McpGtMsg.class);
        log.info(DigestFactory.check(backMsg.getHead(), backMsg.getBody(), key));
	}
}
