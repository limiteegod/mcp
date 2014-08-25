package com.mcp.deploy.cmd;

import com.mcp.deploy.core.util.RemoteConfig;
import com.mcp.deploy.core.util.TestUtil;
import com.mcp.order.inter.McpGtMsg;
import com.mcp.order.inter.common.CmdContext;
import com.mcp.order.inter.query.ReqQ07Body;
import com.mcp.order.inter.util.CookieParam;
import com.mcp.order.inter.util.HttpClientUtil;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;

public class TestQ07 {
	
	private static Logger log = Logger.getLogger(TestQ07.class);

	public static void main(String[] args) throws Exception {
		CookieParam uCp = new CookieParam();
		ObjectMapper om = new ObjectMapper();
		ReqQ07Body reqQ07Body = new ReqQ07Body();
		
		om.setFilters(CmdContext.getInstance().getFilterProviderByCode("Q070101"));
		String bodyStr = om.writeValueAsString(reqQ07Body);
		String message = TestUtil.getReqMessage("", "Q0003", bodyStr, "Q07");
		log.info(bodyStr);
		String content = HttpClientUtil.request(RemoteConfig.IP, RemoteConfig.PORT, RemoteConfig.PATH, message, HttpClientUtil.POST, uCp);
        log.info(om.readValue(content, McpGtMsg.class).getBody());
	}
}
