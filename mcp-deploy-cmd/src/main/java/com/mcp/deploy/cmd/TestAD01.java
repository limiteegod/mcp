package com.mcp.deploy.cmd;

import com.mcp.deploy.cmd.common.Station;
import com.mcp.deploy.core.util.RemoteConfig;
import com.mcp.deploy.core.util.TestUtil;
import com.mcp.order.inter.account.ReqA01Body;
import com.mcp.order.inter.account.ReqA04Body;
import com.mcp.order.inter.admin.ReqAD01Body;
import com.mcp.order.inter.common.CmdContext;
import com.mcp.order.inter.util.HttpClientUtil;
import com.mcp.order.model.admin.Admini;
import com.mcp.order.model.ts.Customer;
import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;

public class TestAD01 {
	
	private static Logger log = Logger.getLogger(TestAD01.class);

	public static void main(String[] args) throws Exception {
		login("admini", "0okmnhy6");
	}

    public static JsonNode login(String name, String password) throws Exception
    {
        ObjectMapper om = new ObjectMapper();
        ReqAD01Body reqAD01Body = new ReqAD01Body();
        reqAD01Body.setName(name);
        reqAD01Body.setPassword(password);
        om.setFilters(CmdContext.getInstance().getFilterProviderByCode("AD010101"));
        String bodyStr = om.writeValueAsString(reqAD01Body);
        String message = TestUtil.getReqMessage("", Station.CODE, bodyStr, "AD01");
        log.info(message);
        String content = HttpClientUtil.request(RemoteConfig.IP, RemoteConfig.PORT, RemoteConfig.PATH, message, HttpClientUtil.POST, null);
        log.info(content);
        JsonNode msgNode = om.readTree(content);
        JsonNode bodyNode = om.readTree(msgNode.get("body").getTextValue());
        return bodyNode;
    }
}
