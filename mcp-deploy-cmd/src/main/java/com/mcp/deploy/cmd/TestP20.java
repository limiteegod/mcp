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

import java.util.ArrayList;
import java.util.List;

/**
 * Created by CH on 2014/10/30.
 */
public class TestP20 {

    private static Logger log = Logger.getLogger(TestP20.class);

    public static void main(String[] args) throws Exception {
        while(true)
        {
            try {
                print();
                Thread.sleep(5000);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    private static boolean print() throws Exception{


        String stationCode = "C0002";
        String key = "123456";
        //查询队列   并取票
        ObjectMapper om = new ObjectMapper();
        ReqP20Body reqP20Body = new ReqP20Body();
        reqP20Body.setSize(2);
        om.setFilters(CmdContext.getInstance().getFilterProviderByCode("P200101"));
        String bodyStr = om.writeValueAsString(reqP20Body);
        log.info(bodyStr);
        String message = TestUtil.getCReqMessage("", stationCode, bodyStr, "P20", key);
        //String message = TestUtil.getDesCReqMessage("", "C0001", bodyStr, "P12", "GtUuw3yWiQI=");
        log.info(message);
        String content = HttpClientUtil.request(RemoteConfig.IP, RemoteConfig.PORT, RemoteConfig.PATH, message, HttpClientUtil.POST, null);
        log.info(content);
        McpGtMsg backMsg = om.readValue(content, McpGtMsg.class);
        String repBodyStr = DigestFactory.check(backMsg.getHead(), backMsg.getBody(), key);
        log.info(repBodyStr);
        RepP20Body repP20Body = om.readValue(repBodyStr, RepP20Body.class);
        List<TTicket> list = repP20Body.getRst();
        if(list.size() == 0)
        {
            return false;
        }
        ReqP21Body reqP21Body = new ReqP21Body();
        List<ReqP02Body> reqList = new ArrayList<ReqP02Body>();
        for(TTicket t:list)
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
                reqList.add(reqP02Body);
            }
        om.setFilters(CmdContext.getInstance().getFilterProviderByCode("P210101"));
        reqP21Body.setReqP02BodyList(reqList);
        bodyStr = om.writeValueAsString(reqP21Body);
        log.info(bodyStr);
        message = TestUtil.getCReqMessage("", stationCode, bodyStr, "P21", key);
        log.info(message);
        content = HttpClientUtil.request(RemoteConfig.IP, RemoteConfig.PORT, RemoteConfig.PATH, message, HttpClientUtil.POST, null);
        log.info("P21" + content);
        return true;
    }
}
