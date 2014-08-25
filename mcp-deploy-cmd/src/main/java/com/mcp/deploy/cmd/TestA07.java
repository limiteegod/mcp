package com.mcp.deploy.cmd;

import com.mcp.deploy.core.util.RemoteConfig;
import com.mcp.deploy.core.util.TestUtil;
import com.mcp.order.inter.account.ReqA07Body;
import com.mcp.order.inter.common.CmdContext;
import com.mcp.order.inter.util.HttpClientUtil;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;

public class TestA07 {
	
	private static Logger log = Logger.getLogger(TestA07.class);

	public static void main(String[] args) throws Exception {
		ObjectMapper om = new ObjectMapper();
		ReqA07Body reqA07Body = new ReqA07Body();
		om.setFilters(CmdContext.getInstance().getFilterProviderByCode("A070101"));
		String bodyStr = om.writeValueAsString(reqA07Body);
		String message = TestUtil.getCReqMessage("", "TQCB", bodyStr, "A07", "123456");
		log.info(message);
		String content = HttpClientUtil.request(RemoteConfig.IP, RemoteConfig.PORT, RemoteConfig.PATH, message, HttpClientUtil.POST, null);
		log.info(content);
	}
}
