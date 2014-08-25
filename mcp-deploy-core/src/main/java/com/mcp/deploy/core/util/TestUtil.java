package com.mcp.deploy.core.util;


import com.mcp.core.util.Base64;
import com.mcp.core.util.CoreUtil;
import com.mcp.core.util.DesUtil;
import com.mcp.core.util.MD5Util;
import com.mcp.core.util.cons.SystemUserType;
import com.mcp.order.common.Constants;
import com.mcp.order.inter.Head;
import com.mcp.order.inter.McpGtMsg;
import com.mcp.order.inter.util.DigestFactory;
import com.mcp.order.util.DateTimeUtil;
import org.codehaus.jackson.map.ObjectMapper;

import java.util.Date;

public class TestUtil {
	
	public static String getCReqMessage(String userId, String channelCode, String body, String cmd, String key) throws Exception
	{
		ObjectMapper om = new ObjectMapper();
		Date now = new Date();
		
		String digestType = "md5";
        String dateStr = DateTimeUtil.getDateString(now);
		String digest = MD5Util.MD5(body + dateStr + key);
		
		Head h = new Head();
		h.setId(CoreUtil.getUUID());
		h.setChannelCode(channelCode);
		h.setCmd(cmd);
		h.setTimestamp(dateStr);
        h.setUserId(userId);
		h.setVer(Constants.GATEWAY_VERSION);
		
		h.setDigest(digest);
		h.setDigestType(digestType);
		
		McpGtMsg req = new McpGtMsg();
		req.setHead(h);
		req.setBody(body);
		
		String message = om.writeValueAsString(req);
		return message;
	}

    /**
     * 老接口的加密方式
     */
    public static String getDesCReqMessage(String userId, String channelCode, String body, String cmd, String key) throws Exception
    {
        return getDesCReqMessage(userId, channelCode, body, cmd, key, "des", SystemUserType.GUEST.getCode());
    }

    public static String getDesCReqMessage(String userId, String channelCode, String body, String cmd, String key, String digestType, int userType) throws Exception
    {
        ObjectMapper om = new ObjectMapper();
        Date now = new Date();

        String dateStr = DateTimeUtil.getDateString(now);

        Head h = new Head();
        h.setId(CoreUtil.getUUID());
        h.setChannelCode(channelCode);
        h.setCmd(cmd);
        h.setTimestamp(dateStr);
        h.setUserId(userId);
        h.setUserType(userType);
        h.setVer(Constants.GATEWAY_VERSION);

        //h.setDigest(digest);
        h.setDigestType(digestType);

        McpGtMsg req = new McpGtMsg();
        req.setHead(h);
        String desBody = DigestFactory.generate(h, body, key);
        req.setBody(desBody);

        String message = om.writeValueAsString(req);
        return message;
    }
	
	public static String getReqMessage(String userId, String channelCode, String body, String cmd) throws Exception
	{
		ObjectMapper om = new ObjectMapper();
		
		String digestType = "md5";
		String digest = MD5Util.MD5(body);
		
		Head h = new Head();
		h.setId(CoreUtil.getUUID());
		h.setChannelCode(channelCode);
		h.setCmd(cmd);
        String dateStr = DateTimeUtil.getDateString(new Date());
		h.setTimestamp(dateStr);
		h.setVer(Constants.GATEWAY_VERSION);
		
		h.setDigest(digest);
		h.setDigestType(digestType);
		
		McpGtMsg req = new McpGtMsg();
		req.setHead(h);
		req.setBody(body);
		
		String message = om.writeValueAsString(req);
		return message;
	}
}
