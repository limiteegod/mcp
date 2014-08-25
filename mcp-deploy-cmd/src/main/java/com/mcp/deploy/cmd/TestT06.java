package com.mcp.deploy.cmd;

import com.mcp.core.util.CoreUtil;
import com.mcp.deploy.core.util.RemoteConfig;
import com.mcp.deploy.core.util.TestUtil;
import com.mcp.order.common.Constants;
import com.mcp.order.inter.McpGtMsg;
import com.mcp.order.inter.common.CmdContext;
import com.mcp.order.inter.trade.*;
import com.mcp.order.inter.util.HttpClientUtil;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;

import java.util.ArrayList;
import java.util.List;

public class TestT06 {
	
	private static Logger log = Logger.getLogger(TestT06.class);

	public static void main(String[] args) throws Exception {
        for(int i = 0; i < 1000; i++)
        {
            lot();
        }
	}

    public static void lot() throws Exception
    {
        ObjectMapper om = new ObjectMapper();
        ReqT06Body reqT06Body = new ReqT06Body();
        ReqOrder reqOrder = new ReqOrder();
        reqOrder.setGameCode("T51");
        reqOrder.setAmount(1200);
        reqOrder.setPlatform("ANDROID");
        reqOrder.setPlayType("06");
        reqOrder.setBetType("21");
        reqOrder.setMultiple(1);
        reqOrder.setOuterId(CoreUtil.getUUID());
        reqOrder.setNumber("01|201312172001|1@1.43,2@2.32&02|201312172001|1@1.44;01|201312172002|1@1.54,2@3.02");
        reqT06Body.setOrder(reqOrder);

        om.setFilters(CmdContext.getInstance().getFilterProviderByCode("T060101"));
        String bodyStr = om.writeValueAsString(reqT06Body);
        String message = TestUtil.getCReqMessage("", "Q0003", bodyStr, "T06", "CePEYAR/Snc=");
        log.info(message);
        String content = HttpClientUtil.request(RemoteConfig.IP, RemoteConfig.PORT, RemoteConfig.PATH, message, HttpClientUtil.POST, null);
        McpGtMsg rep = om.readValue(content, McpGtMsg.class);
        String repBodyStr = rep.getBody();
        log.info(repBodyStr);
    }
}
