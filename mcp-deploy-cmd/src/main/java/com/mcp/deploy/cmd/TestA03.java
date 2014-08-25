package com.mcp.deploy.cmd;

import com.mcp.deploy.core.util.RemoteConfig;
import com.mcp.deploy.core.util.TestUtil;
import com.mcp.order.inter.McpGtMsg;
import com.mcp.order.inter.account.ReqA03Body;
import com.mcp.order.inter.account.ReqA04Body;
import com.mcp.order.inter.common.CmdContext;
import com.mcp.order.inter.util.CookieParam;
import com.mcp.order.inter.util.DigestFactory;
import com.mcp.order.inter.util.HttpClientUtil;
import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;

public class TestA03 {
	
	private static Logger log = Logger.getLogger(TestA03.class);

	public static void main(String[] args) throws Exception {
        ObjectMapper om = new ObjectMapper();
        JsonNode bodyNode = TestA04.login("Q0003", "limitee2", "123456");
        String userId = bodyNode.get("customer").get("id").getTextValue();
        String key = bodyNode.get("st").getTextValue();
		
		ReqA03Body reqA03Body = new ReqA03Body();
		reqA03Body.setPassword("123456");
		reqA03Body.setNewPassword("123456");
		om.setFilters(CmdContext.getInstance().getFilterProviderByCode("A030101"));
        String bodyStr = om.writeValueAsString(reqA03Body);
        log.info(bodyStr);
        String message = TestUtil.getDesCReqMessage(userId, "Q0003", bodyStr, "A03", key);
		log.info(message);
        String content = HttpClientUtil.request(RemoteConfig.IP, RemoteConfig.PORT, RemoteConfig.PATH, message, HttpClientUtil.POST, null);
        log.info(content);
        McpGtMsg backMsg = om.readValue(content, McpGtMsg.class);
        log.info(DigestFactory.check(backMsg.getHead(), backMsg.getBody(), key));
	}
}
