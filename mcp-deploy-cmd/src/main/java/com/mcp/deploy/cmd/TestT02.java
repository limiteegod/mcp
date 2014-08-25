package com.mcp.deploy.cmd;

import com.mcp.core.util.CoreUtil;
import com.mcp.deploy.core.util.RemoteConfig;
import com.mcp.deploy.core.util.TestUtil;
import com.mcp.order.common.ConstantValues;
import com.mcp.order.inter.McpGtMsg;
import com.mcp.order.inter.account.ReqA04Body;
import com.mcp.order.inter.common.CmdContext;
import com.mcp.order.inter.trade.*;
import com.mcp.order.inter.util.CookieParam;
import com.mcp.order.inter.util.DigestFactory;
import com.mcp.order.inter.util.HttpClientUtil;
import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;

import java.util.ArrayList;
import java.util.List;

public class TestT02 {
	
	private static Logger log = Logger.getLogger(TestT02.class);

	public static void main(String[] args) throws Exception {
        ObjectMapper om = new ObjectMapper();
        JsonNode bodyNode = TestA04.login("Q0003", "limitee2", "123456");
        String userId = bodyNode.get("customer").get("id").getTextValue();
        String key = bodyNode.get("st").getTextValue();
		
		log.info("第三方支付方式投注...............");
		
		ReqT01Body reqT01Body = new ReqT01Body();
        ReqOrder reqOrder = new ReqOrder();
        reqOrder.setGameCode("T51");
        reqOrder.setAmount(1200);
        reqOrder.setPlatform("ANDROID");
        reqOrder.setPlayType("06");
        reqOrder.setBetType("21");
        reqOrder.setMultiple(1);
        reqOrder.setOuterId(CoreUtil.getUUID());
        reqOrder.setNumber("01|201312172001|1@1.43,2@2.32&02|201312172001|1@1.44;01|201312172002|1@1.54,2@3.02");
		reqT01Body.setOrder(reqOrder);
		reqT01Body.setPayType(ConstantValues.TOrder_PayType_Company.getCode());
		
		om.setFilters(CmdContext.getInstance().getFilterProviderByCode("T050101"));
        String bodyStr = om.writeValueAsString(reqT01Body);
        log.info(bodyStr);
        //String message = TestUtil.getCReqMessage(userId, "Q0003", bodyStr, "T05", key);
        String message = TestUtil.getDesCReqMessage(userId, "Q0003", bodyStr, "T05", key);
		log.info(message);
        String content = HttpClientUtil.request(RemoteConfig.IP, RemoteConfig.PORT, RemoteConfig.PATH, message, HttpClientUtil.POST, null);
        log.info(content);
        McpGtMsg backMsg = om.readValue(content, McpGtMsg.class);
        String repBodyStr = DigestFactory.check(backMsg.getHead(), backMsg.getBody(), key);
        log.info(repBodyStr);
		RepT01Body repbody = om.readValue(repBodyStr, RepT01Body.class);
		
		log.info("第三方支付...............");

		ReqT02Body reqT02Body = new ReqT02Body();
		reqT02Body.setOrderId(repbody.getOrder().getId());
		om.setFilters(CmdContext.getInstance().getFilterProviderByCode("T020101"));
		bodyStr = om.writeValueAsString(reqT02Body);
        log.info(bodyStr);
		//message = TestUtil.getCReqMessage("", "Q0003", bodyStr, "T02", "CePEYAR/Snc=");
        message = TestUtil.getDesCReqMessage("", "Q0003", bodyStr, "T02", "CePEYAR/Snc=");
		log.info(message);
		content = HttpClientUtil.request(RemoteConfig.IP, RemoteConfig.PORT, RemoteConfig.PATH, message, HttpClientUtil.POST, null);
        log.info(content);
        backMsg = om.readValue(content, McpGtMsg.class);
        log.info(DigestFactory.check(backMsg.getHead(), backMsg.getBody(), "CePEYAR/Snc="));
	}
}
