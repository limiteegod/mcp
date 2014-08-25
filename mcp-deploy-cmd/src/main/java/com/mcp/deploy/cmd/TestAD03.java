package com.mcp.deploy.cmd;

import com.mcp.core.util.CoreUtil;
import com.mcp.core.util.cons.SystemUserType;
import com.mcp.core.util.cons.TOrderType;
import com.mcp.deploy.core.util.RemoteConfig;
import com.mcp.deploy.core.util.TestUtil;
import com.mcp.order.common.Constants;
import com.mcp.order.inter.McpGtMsg;
import com.mcp.order.inter.admin.ReqAD02Body;
import com.mcp.order.inter.admin.ReqAD03Body;
import com.mcp.order.inter.common.CmdContext;
import com.mcp.order.inter.util.DigestFactory;
import com.mcp.order.inter.util.HttpClientUtil;
import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;

public class TestAD03 {
	
	private static Logger log = Logger.getLogger(TestAD03.class);

	public static void main(String[] args) throws Exception {
		ObjectMapper om = new ObjectMapper();
        String userId = "admini";
        JsonNode bodyNode = TestAD01.login(userId, "0okmnhy6");
        String key = bodyNode.get("st").getTextValue();
		
		ReqAD03Body reqAD03Body = new ReqAD03Body();
        reqAD03Body.setUniqueId(CoreUtil.getUUID());
        reqAD03Body.setGameCode("T01");
        reqAD03Body.setTermCode("2014001");
        reqAD03Body.setSize(1000);
        //reqAD03Body.setOrderType(TOrderType.CUSTOMER.ordinal());

		om.setFilters(CmdContext.getInstance().getFilterProviderByCode("AD030101"));
        String bodyStr = om.writeValueAsString(reqAD03Body);
        log.info(bodyStr);
        String message = TestUtil.getDesCReqMessage(userId, "Q0003", bodyStr, "AD03", key,
                Constants.GATEWAY_DIGEST_TYPE_3DES, SystemUserType.ADMINISTRATOR.getCode());
		log.info(message);
        String content = HttpClientUtil.request(RemoteConfig.IP, RemoteConfig.PORT, RemoteConfig.PATH, message, HttpClientUtil.POST, null);
        log.info(content);
        McpGtMsg backMsg = om.readValue(content, McpGtMsg.class);
        log.info(DigestFactory.check(backMsg.getHead(), backMsg.getBody(), key));
	}

}
