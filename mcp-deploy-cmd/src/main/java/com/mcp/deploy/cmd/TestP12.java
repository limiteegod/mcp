package com.mcp.deploy.cmd;

import com.mcp.deploy.core.util.RemoteConfig;
import com.mcp.deploy.core.util.TestUtil;
import com.mcp.order.common.ConstantValues;
import com.mcp.order.common.Constants;
import com.mcp.order.inter.McpGtMsg;
import com.mcp.order.inter.common.CmdContext;
import com.mcp.order.inter.print.*;
import com.mcp.order.inter.util.DigestFactory;
import com.mcp.order.inter.util.HttpClientUtil;
import com.mcp.order.model.common.LotteryContext;
import com.mcp.order.model.ts.Game;
import com.mcp.order.model.ts.TTicket;
import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ArrayNode;

import java.util.List;

public class TestP12 {
	
	private static Logger log = Logger.getLogger(TestP12.class);

	public static void main(String[] args) throws Exception {
        while(true)
        {
            try {
                /*if(!print())
                {
                    break;
                }*/
                print();
                Thread.sleep(2000);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
	}

    public static boolean print() throws Exception
    {
        String stationCode = "C0001";
        //查询队列
        ObjectMapper om = new ObjectMapper();
        ReqP12Body reqP12Body = new ReqP12Body();
        reqP12Body.setSize(20);
        om.setFilters(CmdContext.getInstance().getFilterProviderByCode("P120101"));
        String bodyStr = om.writeValueAsString(reqP12Body);
        log.info(bodyStr);
        String message = TestUtil.getCReqMessage("", stationCode, bodyStr, "P12", "GtUuw3yWiQI=");
        //String message = TestUtil.getDesCReqMessage("", "C0001", bodyStr, "P12", "GtUuw3yWiQI=");
        log.info(message);
        String content = HttpClientUtil.request(RemoteConfig.IP, RemoteConfig.PORT, RemoteConfig.PATH, message, HttpClientUtil.POST, null);
        log.info(content);
        McpGtMsg backMsg = om.readValue(content, McpGtMsg.class);
        String repBodyStr = DigestFactory.check(backMsg.getHead(), backMsg.getBody(), "GtUuw3yWiQI=");
        log.info(repBodyStr);
        JsonNode bodyNode = om.readTree(repBodyStr);
        ArrayNode rstNode = (ArrayNode)bodyNode.get("rst");
        if(rstNode.size() == 0)
        {
            return false;
        }
        for(JsonNode orderNode:rstNode)
        {
            //取票
            ReqP06Body reqP06Body = new ReqP06Body();
            String orderId = orderNode.get("orderId").getTextValue();
            reqP06Body.setOrderId(orderId);
            om.setFilters(CmdContext.getInstance().getFilterProviderByCode("P060101"));
            bodyStr = om.writeValueAsString(reqP06Body);
            log.info(bodyStr);
            message = TestUtil.getCReqMessage("", stationCode, bodyStr, "P06", "GtUuw3yWiQI=");
            //String message = TestUtil.getDesCReqMessage("", "C0001", bodyStr, "P06", "GtUuw3yWiQI=");
            log.info(message);
            content = HttpClientUtil.request(RemoteConfig.IP, RemoteConfig.PORT, RemoteConfig.PATH, message, HttpClientUtil.POST, null);
            log.info(content);
            backMsg = om.readValue(content, McpGtMsg.class);
            repBodyStr = DigestFactory.check(backMsg.getHead(), backMsg.getBody(), "GtUuw3yWiQI=");
            log.info(repBodyStr);
            RepP06Body repP06Body = om.readValue(repBodyStr, RepP06Body.class);

            List<TTicket> tList = repP06Body.getTickets();
            for(TTicket t:tList)
            {
                ReqP02Body reqP02Body = new ReqP02Body();
                reqP02Body.setTicketId(t.getId());
                reqP02Body.setPaper(true);
                reqP02Body.setCode(Constants.TICKET_PRINT_RECEIPT_SUCCESS);
                Game game = LotteryContext.getInstance().getGameByCode(t.getGameCode());
                if(game.getType() == ConstantValues.Game_Type_Jingcai.getCode())
                {
                    reqP02Body.setrNumber(t.getNumbers());
                }
                om.setFilters(CmdContext.getInstance().getFilterProviderByCode("P020101"));
                bodyStr = om.writeValueAsString(reqP02Body);
                message = TestUtil.getCReqMessage("", stationCode, bodyStr, "P02", "GtUuw3yWiQI=");
                //String message = TestUtil.getDesCReqMessage("", "C0001", bodyStr, "P02", "GtUuw3yWiQI=");
                log.info(message);
                content = HttpClientUtil.request(RemoteConfig.IP, RemoteConfig.PORT, RemoteConfig.PATH, message, HttpClientUtil.POST, null);
                log.info(content);
            }
        }
        return true;
    }
}
