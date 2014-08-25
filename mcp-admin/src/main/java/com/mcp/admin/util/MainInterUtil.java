package com.mcp.admin.util;

import com.mcp.admin.cons.SiteConfig;
import com.mcp.core.util.cons.SystemUserType;
import com.mcp.order.common.Constants;
import com.mcp.order.exception.ErrCode;
import com.mcp.order.inter.Head;
import com.mcp.order.inter.McpGtMsg;
import com.mcp.order.inter.util.DigestFactory;
import com.mcp.order.inter.util.HttpClientUtil;
import com.mcp.order.inter.util.InterUtil;
import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created by limitee on 2014/8/7.
 */
public class MainInterUtil {

    private static Logger log = Logger.getLogger(MainInterUtil.class);

    public static String getData(HttpServletRequest request, String cmd, String body) throws Exception
    {
        HttpSession session = request.getSession();
        String userId = (String)session.getAttribute("userId");
        String st = (String)session.getAttribute("st");
        Object typeObject = session.getAttribute("type");
        int type = SystemUserType.GUEST.getCode();
        if(typeObject != null)
        {
            type = (Integer)typeObject;
        }
        log.info("角色:" + type);
        log.info("body:" + body);
        Head head = InterUtil.getHead(userId, type, "", cmd, Constants.GATEWAY_DIGEST_TYPE_3DES);
        String message = InterUtil.getMsg(head, body, st);
        String content = HttpClientUtil.request(SiteConfig.SITE_FILTER, SiteConfig.SITE_FILTER_PORT, SiteConfig.SITE_FILTER_PATH, message, HttpClientUtil.POST, null);
        ObjectMapper om = new ObjectMapper();
        McpGtMsg backMsg = om.readValue(content, McpGtMsg.class);
        String repBody = null;
        if(type != SystemUserType.GUEST.getCode())
        {
            repBody = DigestFactory.check(backMsg.getHead(), backMsg.getBody(), st);
        }
        else
        {
            repBody = backMsg.getBody();
        }
        log.info("repBody:" + repBody);
        if(cmd.equals("AD01"))
        {
            JsonNode bodyNode = om.readTree(repBody);
            String repCode = bodyNode.get("repCode").getTextValue();
            if(repCode.equals(ErrCode.E0000))
            {
                request.getSession().setAttribute("st", bodyNode.get("st").getTextValue());
                request.getSession().setAttribute("userId", bodyNode.get("user").get("name").getTextValue());
                request.getSession().setAttribute("type", bodyNode.get("user").get("type").getIntValue());
                request.getSession().setMaxInactiveInterval(30*60);//30分钟
            }
        }
        return repBody;
    }

}
