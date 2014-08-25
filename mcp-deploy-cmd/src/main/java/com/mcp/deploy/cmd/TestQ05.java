package com.mcp.deploy.cmd;

import com.mcp.deploy.core.util.RemoteConfig;
import com.mcp.deploy.core.util.TestUtil;
import com.mcp.order.inter.common.CmdContext;
import com.mcp.order.inter.query.ReqQ05Body;
import com.mcp.order.inter.util.CookieParam;
import com.mcp.order.inter.util.HttpClientUtil;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;

public class TestQ05 {
	
	private static Logger log = Logger.getLogger(TestQ05.class);

	public static void main(String[] args) throws Exception {
		CookieParam uCp = new CookieParam();
		ObjectMapper om = new ObjectMapper();
		ReqQ05Body reqQ05Body = new ReqQ05Body();
		reqQ05Body.setKeyword("九歌");
		
		om.setFilters(CmdContext.getInstance().getFilterProviderByCode("Q050101"));
		String bodyStr = om.writeValueAsString(reqQ05Body);
		String message = TestUtil.getReqMessage("", "Q0003", bodyStr, "Q05");
		log.info(message);
		String content = HttpClientUtil.request(RemoteConfig.IP, RemoteConfig.PORT, RemoteConfig.PATH, message, HttpClientUtil.POST, uCp);
		log.info(content);
	}
}
