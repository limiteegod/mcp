package com.mcp.deploy.cmd;

import com.mcp.deploy.core.util.RemoteConfig;
import com.mcp.deploy.core.util.TestUtil;
import com.mcp.order.common.ConstantValues;
import com.mcp.order.inter.McpGtMsg;
import com.mcp.order.inter.common.CmdContext;
import com.mcp.order.inter.query.ReqQ11Body;
import com.mcp.order.inter.util.CookieParam;
import com.mcp.order.inter.util.DigestFactory;
import com.mcp.order.inter.util.HttpClientUtil;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;

public class TestQ11 {
	
	private static Logger log = Logger.getLogger(TestQ11.class);

	public static void main(String[] args) throws Exception {
		ObjectMapper om = new ObjectMapper();
		ReqQ11Body reqQ11Body = new ReqQ11Body();
		reqQ11Body.setStatus(ConstantValues.Game_Status_Open.getCode());
		om.setFilters(CmdContext.getInstance().getFilterProviderByCode("Q110101"));
		String bodyStr = om.writeValueAsString(reqQ11Body);
		String message = TestUtil.getReqMessage("", "Q0003", bodyStr, "Q11");
        String content = HttpClientUtil.request(RemoteConfig.IP, RemoteConfig.PORT, RemoteConfig.PATH, message, HttpClientUtil.POST, null);
        log.info(content);
	}
}
