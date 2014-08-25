package com.mcp.deploy.cmd;

import com.mcp.core.util.CoreUtil;
import com.mcp.deploy.cmd.common.Station;
import com.mcp.deploy.cmd.common.Term;
import com.mcp.deploy.cmd.common.User;
import com.mcp.deploy.core.util.RemoteConfig;
import com.mcp.deploy.core.util.TestUtil;
import com.mcp.order.common.ConstantValues;
import com.mcp.order.inter.McpGtMsg;
import com.mcp.order.inter.account.ReqA04Body;
import com.mcp.order.inter.cash.ReqC02Body;
import com.mcp.order.inter.common.CmdContext;
import com.mcp.order.inter.scheme.RepS01Body;
import com.mcp.order.inter.scheme.ReqS01Body;
import com.mcp.order.inter.trade.ReqT02Body;
import com.mcp.order.inter.util.CookieParam;
import com.mcp.order.inter.util.DigestFactory;
import com.mcp.order.inter.util.HttpClientUtil;
import com.mcp.scheme.model.SchemeZh;
import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;

public class TestS01 {
	
	private static Logger log = Logger.getLogger(TestS01.class);

	public static void main(String[] args) throws Exception {

        ObjectMapper om = new ObjectMapper();
        JsonNode bodyNode = TestA04.login("Q0003", "limitee2", "123456");
        String userId = bodyNode.get("customer").get("id").getTextValue();
        String key = bodyNode.get("st").getTextValue();

        ReqS01Body reqS01Body = new ReqS01Body();
        SchemeZh scheme = new SchemeZh();
        scheme.setWinStop(false);
        scheme.setPayType(ConstantValues.TOrder_PayType_Cash.getCode());
        scheme.setPlatform("IOS");
        scheme.setAmount(1200);
        scheme.setOrderCount(3);
        scheme.setGameCode("T01");
        scheme.setNumList("00~00~09,12,22,32,34|02,04~400~2");
        scheme.setStartTermCode("2014001");
        reqS01Body.setScheme(scheme);
        om.setFilters(CmdContext.getInstance().getFilterProviderByCode("S010101"));
        String bodyStr = om.writeValueAsString(reqS01Body);

        log.info(bodyStr);
        //String message = TestUtil.getCReqMessage(userId, "Q0003", bodyStr, "A02", key);
        String message = TestUtil.getDesCReqMessage(userId, "Q0003", bodyStr, "S01", key);
        log.info(message);

        String content = HttpClientUtil.request(RemoteConfig.IP, RemoteConfig.PORT, RemoteConfig.PATH, message, HttpClientUtil.POST, null);
        log.info(content);
        McpGtMsg backMsg = om.readValue(content, McpGtMsg.class);
        log.info(DigestFactory.check(backMsg.getHead(), backMsg.getBody(), key));
	}
}
