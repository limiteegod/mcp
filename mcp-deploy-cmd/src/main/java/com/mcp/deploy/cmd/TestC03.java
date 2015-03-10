package com.mcp.deploy.cmd;

import com.mcp.core.util.CoreUtil;
import com.mcp.deploy.cmd.common.Station;
import com.mcp.deploy.core.util.RemoteConfig;
import com.mcp.deploy.core.util.TestUtil;
import com.mcp.order.inter.McpGtMsg;
import com.mcp.order.inter.account.ReqA04Body;
import com.mcp.order.inter.cash.ReqC03Body;
import com.mcp.order.inter.common.CmdContext;
import com.mcp.order.inter.trade.ReqOrder;
import com.mcp.order.inter.trade.ReqT01Body;
import com.mcp.order.inter.trade.ReqTicket;
import com.mcp.order.inter.util.CookieParam;
import com.mcp.order.inter.util.DigestFactory;
import com.mcp.order.inter.util.HttpClientUtil;
import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;

import java.util.ArrayList;
import java.util.List;

public class TestC03 {

    private static Logger log = Logger.getLogger(TestC03.class);

    public static void main(String[] args) throws Exception {
        JsonNode bodyNode = TestA04.login("Q0003", "liming", "123456");
        String userId = bodyNode.get("customer").get("id").getTextValue();
        String key = bodyNode.get("st").getTextValue();
        testC03(userId, key);
    }

    public static void testC03(String userId, String key) throws Exception
    {
        ObjectMapper om = new ObjectMapper();
        ReqC03Body reqC03Body = new ReqC03Body();
        reqC03Body.setAmount(200);

        om.setFilters(CmdContext.getInstance().getFilterProviderByCode("C030101"));
        String bodyStr = om.writeValueAsString(reqC03Body);
        log.info(bodyStr);
        String message = TestUtil.getCReqMessage(userId, Station.CODE, bodyStr, "C03", key);
        log.info(message);
        String content = HttpClientUtil.request(RemoteConfig.IP, RemoteConfig.PORT, RemoteConfig.PATH, message, HttpClientUtil.POST, null);
        log.info(content);
        McpGtMsg backMsg = om.readValue(content, McpGtMsg.class);
        log.info(DigestFactory.check(backMsg.getHead(), backMsg.getBody(), key));
    }
}
