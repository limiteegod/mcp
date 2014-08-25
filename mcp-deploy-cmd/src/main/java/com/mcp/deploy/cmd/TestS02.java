package com.mcp.deploy.cmd;

import com.mcp.deploy.core.util.RemoteConfig;
import com.mcp.deploy.core.util.TestUtil;
import com.mcp.order.common.ConstantValues;
import com.mcp.order.inter.McpGtMsg;
import com.mcp.order.inter.common.CmdContext;
import com.mcp.order.inter.scheme.ReqS01Body;
import com.mcp.order.inter.scheme.ReqS02Body;
import com.mcp.order.inter.util.DigestFactory;
import com.mcp.order.inter.util.HttpClientUtil;
import com.mcp.scheme.model.SchemeZh;
import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;

public class TestS02 {
	
	private static Logger log = Logger.getLogger(TestS02.class);

	public static void main(String[] args) throws Exception {

        ObjectMapper om = new ObjectMapper();
        JsonNode bodyNode = TestA04.login("Q0003", "limitee2", "123456");
        String userId = bodyNode.get("customer").get("id").getTextValue();
        String key = bodyNode.get("st").getTextValue();

        ReqS02Body reqS02Body = new ReqS02Body();
        reqS02Body.setSchemeId("84d43a2631f14d90b10c76027917e658");
        om.setFilters(CmdContext.getInstance().getFilterProviderByCode("S020101"));
        String bodyStr = om.writeValueAsString(reqS02Body);

        log.info(bodyStr);
        //String message = TestUtil.getCReqMessage(userId, "Q0003", bodyStr, "A02", key);
        String message = TestUtil.getDesCReqMessage(userId, "Q0003", bodyStr, "S02", key);
        log.info(message);

        String content = HttpClientUtil.request(RemoteConfig.IP, RemoteConfig.PORT, RemoteConfig.PATH, message, HttpClientUtil.POST, null);
        log.info(content);
        McpGtMsg backMsg = om.readValue(content, McpGtMsg.class);
        log.info(DigestFactory.check(backMsg.getHead(), backMsg.getBody(), key));
	}
}
