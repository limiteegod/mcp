package com.mcp.deploy.cmd;

import com.mcp.core.util.CoreUtil;
import com.mcp.deploy.cmd.common.Station;
import com.mcp.deploy.cmd.common.User;
import com.mcp.deploy.core.util.RemoteConfig;
import com.mcp.deploy.core.util.TestUtil;
import com.mcp.order.inter.account.ReqA01Body;
import com.mcp.order.inter.common.CmdContext;
import com.mcp.order.inter.util.CookieParam;
import com.mcp.order.inter.util.HttpClientUtil;
import com.mcp.order.model.ts.Customer;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;

public class TestA01 {
	
	private static Logger log = Logger.getLogger(TestA01.class);

	public static void main(String[] args) throws Exception {
		ObjectMapper om = new ObjectMapper();
		ReqA01Body reqA01Body = new ReqA01Body();
		Customer customer = new Customer();
		customer.setName("limitee2");
		customer.setPassword("123456");
		reqA01Body.setCustomer(customer);
		
		om.setFilters(CmdContext.getInstance().getFilterProviderByCode("A010101"));
		String bodyStr = om.writeValueAsString(reqA01Body);
		String message = TestUtil.getReqMessage("", Station.CODE, bodyStr, "A01");
		log.info(message);
		String content = HttpClientUtil.request(RemoteConfig.IP, RemoteConfig.PORT, RemoteConfig.PATH, message, HttpClientUtil.POST, null);
		log.info(content);
	}
}
