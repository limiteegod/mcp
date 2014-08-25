package com.mcp.deploy.cmd;

import com.mcp.deploy.cmd.common.Station;
import com.mcp.deploy.cmd.common.User;
import com.mcp.deploy.core.util.RemoteConfig;
import com.mcp.deploy.core.util.TestUtil;
import com.mcp.order.inter.McpGtMsg;
import com.mcp.order.inter.account.ReqA04Body;
import com.mcp.order.inter.common.CmdContext;
import com.mcp.order.inter.trade.ReqOrder;
import com.mcp.order.inter.trade.ReqT05Body;
import com.mcp.order.inter.util.CookieParam;
import com.mcp.order.inter.util.DigestFactory;
import com.mcp.order.inter.util.HttpClientUtil;
import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;

public class TestT05 {
	
	private static Logger log = Logger.getLogger(TestT05.class);

	public static void main(String[] args) throws Exception {
        for(int i = 0; i < 50; i++)
        {
            lot();
            lot1();
        }
	}

    public static void lot() throws Exception
    {
        ObjectMapper om = new ObjectMapper();
        JsonNode bodyNode = TestA04.login("Q0003", "limitee2", "123456");
        String userId = bodyNode.get("customer").get("id").getTextValue();
        String key = bodyNode.get("st").getTextValue();

        ReqT05Body reqT05Body = new ReqT05Body();
        ReqOrder reqOrder = new ReqOrder();
        reqOrder.setGameCode("T51");
        reqOrder.setAmount(1200);
        reqOrder.setPlatform("ANDROID");
        reqOrder.setPlayType("06");
        reqOrder.setBetType("21");
        reqOrder.setMultiple(1);
        reqOrder.setNumber("01|201312172001|1@1.53,2@2.32&02|201312172001|1@1.64;01|201312172002|1@1.96,2@3.02");
        reqT05Body.setOrder(reqOrder);
        om.setFilters(CmdContext.getInstance().getFilterProviderByCode("T050101"));
        String bodyStr = om.writeValueAsString(reqT05Body);

        log.info(bodyStr);
        //String message = TestUtil.getCReqMessage(userId, "Q0003", bodyStr, "A02", key);
        String message = TestUtil.getDesCReqMessage(userId, "Q0003", bodyStr, "T05", key);
        log.info(message);

        String content = HttpClientUtil.request(RemoteConfig.IP, RemoteConfig.PORT, RemoteConfig.PATH, message, HttpClientUtil.POST, null);
        log.info(content);
        McpGtMsg backMsg = om.readValue(content, McpGtMsg.class);
        log.info(DigestFactory.check(backMsg.getHead(), backMsg.getBody(), key));
    }

    public static void lot1() throws Exception
    {
        ObjectMapper om = new ObjectMapper();
        JsonNode bodyNode = TestA04.login("Q0003", "limitee2", "123456");
        String userId = bodyNode.get("customer").get("id").getTextValue();
        String key = bodyNode.get("st").getTextValue();

        ReqT05Body reqT05Body = new ReqT05Body();
        ReqOrder reqOrder = new ReqOrder();
        reqOrder.setGameCode("T51");
        reqOrder.setAmount(400);
        reqOrder.setPlatform("ANDROID");
        reqOrder.setPlayType("06");
        reqOrder.setBetType("21");
        reqOrder.setMultiple(1);
        reqOrder.setNumber("02|201312172001|0@2.64;01|201312172002|1@1.96,2@3.02");
        reqT05Body.setOrder(reqOrder);
        om.setFilters(CmdContext.getInstance().getFilterProviderByCode("T050101"));
        String bodyStr = om.writeValueAsString(reqT05Body);

        log.info(bodyStr);
        //String message = TestUtil.getCReqMessage(userId, "Q0003", bodyStr, "A02", key);
        String message = TestUtil.getDesCReqMessage(userId, "Q0003", bodyStr, "T05", key);
        log.info(message);

        String content = HttpClientUtil.request(RemoteConfig.IP, RemoteConfig.PORT, RemoteConfig.PATH, message, HttpClientUtil.POST, null);
        log.info(content);
        McpGtMsg backMsg = om.readValue(content, McpGtMsg.class);
        log.info(DigestFactory.check(backMsg.getHead(), backMsg.getBody(), key));
    }
}
