package com.mcp.deploy.cmd;

import com.mcp.deploy.core.util.RemoteConfig;
import com.mcp.deploy.core.util.TestUtil;
import com.mcp.order.inter.account.ReqA04Body;
import com.mcp.order.inter.common.CmdContext;
import com.mcp.order.inter.util.CookieParam;
import com.mcp.order.inter.util.HttpClientUtil;
import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;

public class TestA04 {
	
	private static Logger log = Logger.getLogger(TestA04.class);

	public static void main(String[] args) throws Exception {
        JsonNode bodyNode = login("Q0003", "limitee2", "123456");
        log.info(bodyNode.get("st").getTextValue());
	}

    public static JsonNode login(String channelCode, String name, String password) throws Exception
    {
        ObjectMapper om = new ObjectMapper();
        ReqA04Body reqA04Body = new ReqA04Body();
        reqA04Body.setName(name);
        reqA04Body.setPassword(password);

        om.setFilters(CmdContext.getInstance().getFilterProviderByCode("A040101"));
        String bodyStr = om.writeValueAsString(reqA04Body);
        log.info(bodyStr);
        String message = TestUtil.getReqMessage("", channelCode, bodyStr, "A04");
        log.info(message);
        String content = HttpClientUtil.request(RemoteConfig.IP, RemoteConfig.PORT, RemoteConfig.PATH, message, HttpClientUtil.POST, null);
        log.info(content);
        JsonNode msgNode = om.readTree(content);
        JsonNode bodyNode = om.readTree(msgNode.get("body").getTextValue());
        return bodyNode;
    }

}
