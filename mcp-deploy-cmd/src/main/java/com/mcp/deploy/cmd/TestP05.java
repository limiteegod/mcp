package com.mcp.deploy.cmd;

import com.mcp.deploy.core.util.RemoteConfig;
import com.mcp.deploy.core.util.TestUtil;
import com.mcp.order.common.ConstantValues;
import com.mcp.order.common.Constants;
import com.mcp.order.inter.account.ReqA04Body;
import com.mcp.order.inter.common.CmdContext;
import com.mcp.order.inter.print.ReqP01Body;
import com.mcp.order.inter.print.ReqP05Body;
import com.mcp.order.inter.trade.ReqOrder;
import com.mcp.order.inter.trade.ReqT01Body;
import com.mcp.order.inter.trade.ReqTicket;
import com.mcp.order.inter.util.CookieParam;
import com.mcp.order.inter.util.HttpClientUtil;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;

import java.util.ArrayList;
import java.util.List;

public class TestP05 {
	
	private static Logger log = Logger.getLogger(TestP05.class);

	public static void main(String[] args) throws Exception {
		CookieParam uCp = new CookieParam();
		
		ObjectMapper om = new ObjectMapper();
		ReqP05Body reqP05Body = new ReqP05Body();
        reqP05Body.setCode("C0002");
        reqP05Body.setPassword("123456");
        reqP05Body.setType(Constants.STATION_OP_LOGIN);
		om.setFilters(CmdContext.getInstance().getFilterProviderByCode("P050101"));
		String bodyStr = om.writeValueAsString(reqP05Body);
		String message = TestUtil.getReqMessage("", "Q0001", bodyStr, "P05");
		System.out.println(message);
		String content = HttpClientUtil.request(RemoteConfig.IP, RemoteConfig.PORT, RemoteConfig.PATH, message, HttpClientUtil.POST, uCp);
		System.out.println(content);
	}
}
