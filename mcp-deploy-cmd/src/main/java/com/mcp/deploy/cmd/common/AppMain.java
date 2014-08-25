package com.mcp.deploy.cmd.common;

import com.mcp.core.util.MD5Util;
import com.mcp.order.inter.Head;
import com.mcp.order.util.DateTimeUtil;
import org.codehaus.jackson.map.ObjectMapper;

/**
 * Created by ming.li on 2014/4/17.
 */
public class AppMain {

    public static void main(String[] args) throws Exception {
        ObjectMapper om = new ObjectMapper();
        String head = "{\"id\":\"140417182538010000502\",\"cmd\":\"T03\",\"digestType\":\"md5\",\"digest\":\"a4b42a426eb502975840436829ede919\",\"ver\":\"s.1.01\",\"channelCode\":\"Q0008\",\"timestamp\":\"2014-04-17T18:25:38.464+0800\"}";
        Head h = om.readValue(head, Head.class);
        System.out.println(h.getTimestamp());

        String head2 = om.writeValueAsString(h);
        System.out.println(head2);

        Head h2 = om.readValue(head2, Head.class);

        String body = "{\"order\":{\"termCode\":\"2014042\",\"gameCode\":\"F01\",\"amount\":600,\"multiple\":1,\"outerId\":\"14041700075969\",\"tickets\":[{\"betTypeCode\":\"00\",\"playTypeCode\":\"00\",\"amount\":600,\"multiple\":1,\"numbers\":\"01,02,09,12,19,23|09;02,03,15,17,26,32|13;08,10,18,19,31,32|02\"}]}}";
        System.out.println(MD5Util.MD5(body + h2.getTimestamp() + "123456"));
    }
}
