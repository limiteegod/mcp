package com.mcp.deploy.cmd;

import com.mcp.deploy.core.util.RemoteConfig;
import com.mcp.deploy.core.util.TestUtil;
import com.mcp.order.inter.account.ReqA04Body;
import com.mcp.order.inter.cash.ReqC01Body;
import com.mcp.order.inter.common.CmdContext;
import com.mcp.order.inter.util.CookieParam;
import com.mcp.order.inter.util.HttpClientUtil;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;

public class TestC01 {
	
	private static Logger log = Logger.getLogger(TestC01.class);

	public static void main(String[] args) throws Exception {
		CookieParam uCp = new CookieParam();
		ObjectMapper om = new ObjectMapper();
		ReqA04Body reqA04Body = new ReqA04Body();
		reqA04Body.setName("limitee2");
		reqA04Body.setPassword("123456");
		
		om.setFilters(CmdContext.getInstance().getFilterProviderByCode("A040101"));
		String bodyStr = om.writeValueAsString(reqA04Body);
		String message = TestUtil.getReqMessage("", "Q0003", bodyStr, "A04");
		log.info(message);
		String content = HttpClientUtil.request(RemoteConfig.IP, RemoteConfig.PORT, RemoteConfig.PATH, message, HttpClientUtil.POST, uCp);
		log.info(content);
		uCp.headersToCookies();
		
		ReqC01Body reqC01Body = new ReqC01Body();
		reqC01Body.setAmount(100);
		om.setFilters(CmdContext.getInstance().getFilterProviderByCode("C010101"));
		bodyStr = om.writeValueAsString(reqC01Body);
		message = TestUtil.getReqMessage("", "Q0003", bodyStr, "C01");
		log.info(message);
		content = HttpClientUtil.request(RemoteConfig.IP, RemoteConfig.PORT, RemoteConfig.PATH, message, HttpClientUtil.POST, uCp);
		log.info(content);
	}
}
