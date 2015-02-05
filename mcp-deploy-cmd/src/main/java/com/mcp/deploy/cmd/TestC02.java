package com.mcp.deploy.cmd;

import com.mcp.core.util.CoreUtil;
import com.mcp.deploy.cmd.common.Station;
import com.mcp.deploy.cmd.common.User;
import com.mcp.deploy.core.util.RemoteConfig;
import com.mcp.deploy.core.util.TestUtil;
import com.mcp.order.common.ConstantValues;
import com.mcp.order.inter.cash.ReqC02Body;
import com.mcp.order.inter.common.CmdContext;
import com.mcp.order.inter.util.HttpClientUtil;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;

public class TestC02 {
	
	private static Logger log = Logger.getLogger(TestC02.class);

	public static void main(String[] args) throws Exception {
		ObjectMapper om = new ObjectMapper();

		ReqC02Body reqC02Body = new ReqC02Body();
		reqC02Body.setAmount(100000);
		reqC02Body.setName(User.NAME);
		reqC02Body.setFromType(ConstantValues.Recharge_Channel_CEB.getCode());
		reqC02Body.setOrderId(CoreUtil.getUUID());
		
		om.setFilters(CmdContext.getInstance().getFilterProviderByCode("C020101"));
		String bodyStr = om.writeValueAsString(reqC02Body);
		String message = TestUtil.getCReqMessage("", Station.CODE, bodyStr, "C02", Station.KEY );
		log.info(message);
		String content = HttpClientUtil.request(RemoteConfig.IP, RemoteConfig.PORT, RemoteConfig.PATH, message, HttpClientUtil.POST, null);
		log.info(content);
		
	}
}
